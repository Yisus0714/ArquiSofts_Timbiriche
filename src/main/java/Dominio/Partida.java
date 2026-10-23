/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import Model.ObserverDominio;
import eventos.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author luisb
 */
public class Partida implements IRegistro, IJuego, ILobby, IFinal {
    
    //sout
    
    private List<ObserverDominio> observadores;
    private Tablero tablero;
    private List<Jugador> jugadores;
    private Jugador turnoActual;
    private Votacion votacion;
    private EstadoPartida estado;
    private PuntoSeleccionado onSelect;
    private List<Jugada> jugadas;
    private ResultadoVotacion resultadoFin;
    
    
    public Partida(){
        this.observadores = new CopyOnWriteArrayList<>();
        this.jugadores = new ArrayList<>();
        this.jugadas = new ArrayList<>();
        this.votacion = new Votacion();
        this.estado = EstadoPartida.PREPARANDO;
        this.resultadoFin = ResultadoVotacion.SIN_VOTACION;
    }
    
    private Jugador buscarJugador(String nombre) {
        
        for(Jugador jugador: jugadores) {
            if(jugador.getNombre().equalsIgnoreCase(nombre.trim())) {
                return jugador;
            }
        }
        return null;
    }
    
    public int getDimension(){
        return tablero.getDimension();
    }
    
    
    private boolean avatarDisponible(Avatar avatar) {
        for (Jugador jugador : jugadores) {
            if (jugador.getAvatar() == avatar) {
                return false;
            }
        }
        return true;
    }
    
    private ColorJugador buscarColorDisponible() {
        for (ColorJugador color : ColorJugador.values()) {
            boolean disponible = true;

            for (Jugador jugador : jugadores) {
                if (jugador.getColor() == color) {
                    disponible = false;
                    break;
                }
            }

            if (disponible) {
                return color;
            }
        }
        return null;
    }
    
    private boolean estaEnTurno(Jugador jugador){
        if(jugador == turnoActual) {
            return true;
        }
        return false;
    }
    
    
    
    private boolean verificarComienzo() {
        
        if (estado != EstadoPartida.PREPARANDO) {
            return false;
        }
        
        int cantJugadores = jugadores.size();
        
        
        if(cantJugadores == 4) {
           return true;
        } 
    
        if(cantJugadores >= 2 && votacion.analizarVotosAFavor(cantJugadores) == ResultadoVotacion.GANO_A_FAVOR) {
            return true;
        }
        return false;
    }
   
    

    /** Avisa a cada observador suscrito del hecho ocurrido; ellos consultan el detalle. */
    private void notificar(Event evento) {
        for (ObserverDominio obs : observadores) {
            obs.actualizar(evento);
        }
    }


    /**
     * Resuelve la votación para terminar la partida con los votos actuales.
     * Si todos están de acuerdo la partida termina; si alguien se opone se desestima.
     */
    private void evaluarVotacionFin() {
        resultadoFin = votacion.analizarVotosParaTerminar(jugadores.size());

        if (resultadoFin == ResultadoVotacion.GANO_A_FAVOR) {
            estado = EstadoPartida.TERMINADA;
        }

        notificar(Event.VOTO_REGISTRADO);

        if (estado == EstadoPartida.TERMINADA) {
            notificar(Event.JUEGO_TERMINADO);
        }
    }

    public void suscribir(ObserverDominio observador) {
        observadores.add(observador);
    }
    
    
    public void desuscribir(ObserverDominio observador) {
            observadores.remove(observador);
        }    
    
    
    public void empezarJuego() {
        
        if (estado != EstadoPartida.PREPARANDO) {
            throw new IllegalStateException("La partida ya comenzó");
        }

        if (jugadores.size() < 2 || jugadores.size() > 4) {
            throw new IllegalStateException(
                "Se necesitan entre 2 y 4 jugadores"
            );
        }
        
        votacion.limpiar();
        
        tablero = new Tablero(calcularDimension());
        estado = EstadoPartida.EN_CURSO;
        Collections.shuffle(jugadores);
        turnoActual = jugadores.get(0);
        
        notificar(Event.JUEGO_INICIADO);
        
    
    }
    
    public boolean estaDeseleccionado(int x, int y) {
        if (onSelect == null) return false;

        Punto punto = this.onSelect.getPutnoJugado();
                
        if(punto.getFila() == x && punto.getColumna() == y) {
            return true;
        }
        return false;
    }
    
    private boolean estaIntentandoJugada(int x, int y) {
        if (onSelect == null) return false;
        for(Punto puntoDispo: onSelect.getJugadasDsiponibles()) {
            if(puntoDispo.getFila() == x && puntoDispo.getColumna() == y) {
                return true;
            }
        }
        return false;
    }
    
    public EstadoPartida getEstado() {
        return estado;
    }
    
    public int calcularDimension() {
        return ((jugadores.size() - 1)*10);
    }
    
    private void cambiarTurno() {
        int indiceActual = jugadores.indexOf(turnoActual);

        if (indiceActual == -1) {
            throw new IllegalStateException(
                "El jugador actual no está registrado en la partida"
            );
        }

        int siguienteIndice = (indiceActual + 1) % jugadores.size();
        turnoActual = jugadores.get(siguienteIndice);
    }
    
    private void deshacerJugadas(Jugador jugador) {
        ListIterator<Jugada> iterador =
                jugadas.listIterator(jugadas.size());

        while (iterador.hasPrevious()) {
            Jugada jugada = iterador.previous();

            if (jugada.getJugador() == jugador) {
                Linea linea = jugada.getLinea();

                linea.quitarJugador();

                iterador.remove();
            }
        }
    }
     
    
    @Override
    public void registrarJugador(String nombre, Avatar avatar) throws Exception{
        
        Objects.requireNonNull(nombre, "El nombre es obligatorio");
        Objects.requireNonNull(avatar, "El avatar es obligatorio");

        
        if(estado != EstadoPartida.PREPARANDO) {
            throw new Exception("La partida ya está en curso");
        }
        
        if(jugadores.size() >= 4) {
            throw new Exception("La partida ya está completa");
        }
        
        if(nombre.isBlank()) {
            throw new Exception("El nombre está vacío");
        }
        
        if (nombre.length() > 15) {
            throw new Exception("El nombre no puede superar los 15 caracteres");
        }
        
        if(buscarJugador(nombre) != null) {
            throw new Exception("Este nombre ya está registrado");
        }
        
        if(!avatarDisponible(avatar)) {
            throw new Exception("Este avatar ya está ocupado");
        }
        
        nombre = nombre.trim();

        nombre = nombre.substring(0, 1).toUpperCase()
                + nombre.substring(1);
        
        
        ColorJugador colorJugador = buscarColorDisponible();
        
        Jugador jugadorNuevo = new Jugador(nombre, colorJugador, avatar);
        
        jugadores.add(jugadorNuevo);
        
        notificar(Event.JUGADOR_REGISTRADO);
        
        if(verificarComienzo()){
            empezarJuego();
        }
        
        
    }
    
    
    @Override
    public void abandonar(String nombre) throws Exception {
        Objects.requireNonNull(nombre, "El nombre es obligatorio");

        if(nombre.isBlank()) {
            throw new Exception("El nombre está vacío");
        }
        if(estado == EstadoPartida.TERMINADA) {
            throw new Exception("La partida ya terminó");
        }

        Jugador jugador = buscarJugador(nombre.trim());
        if(jugador == null) {
            throw new Exception("Este jugador no existe en partida");
        }

        // Sala de espera: solo se le quita de la lista
        if(estado == EstadoPartida.PREPARANDO) {
            votacion.quitarVoto(jugador);
            jugadores.remove(jugador);
            notificar(Event.JUGADOR_ABANDONO);
            if (verificarComienzo()) {
                empezarJuego();
            }
            return;
        }

        boolean teniaElTurno = (jugador == turnoActual);
        boolean teniaSeleccion = teniaElTurno && onSelect != null;
        Jugador siguiente = jugadores.get((jugadores.indexOf(jugador) + 1) % jugadores.size());

        deshacerJugadas(jugador);
        votacion.quitarVoto(jugador);
        jugadores.remove(jugador);

        if(teniaElTurno) {
            turnoActual = siguiente;
            onSelect = null;
        } else if(onSelect != null) {
            // Al liberarse sus líneas pueden aparecer destinos nuevos: se refresca la selección
            Punto sel = onSelect.getPutnoJugado();
            onSelect = tablero.seleccionarPunto(sel.getFila(), sel.getColumna());
        }

        notificar(Event.JUGADOR_ABANDONO);
        if(teniaSeleccion) {
            notificar(Event.PUNTO_DESELECCIONADO);
        }

        if(jugadores.size() == 1) {
            estado = EstadoPartida.TERMINADA;
            notificar(Event.JUEGO_TERMINADO);
        } else {
            if(teniaElTurno) {
                notificar(Event.CAMBIO_TURNO);
            }
            // Si había una votación abierta, el voto del que se fue deja de contar
            if(resultadoFin == ResultadoVotacion.EN_PROCESO) {
                evaluarVotacionFin();
            }
        }
    }
        
        
    @Override
    public void votar(Voto voto, String nombre) throws Exception {
        Objects.requireNonNull(voto, "El voto es obligatorio");
        Objects.requireNonNull(nombre, "El nombre es obligatorio");
        
        if (estado == EstadoPartida.TERMINADA) {
            throw new IllegalStateException(
                "No se puede votar porque la partida ya terminó"
            );
        }
        
        nombre = nombre.trim();
        
        if(nombre.isBlank()) {
            throw new Exception("El nommbre no puede estar vacío");
        }
        
        Jugador jugador = buscarJugador(nombre);
        
        if(jugador == null) {
            throw new Exception("No se encontró ningun jugador con ese nombre");
        }
        
        if(estado == EstadoPartida.EN_CURSO && resultadoFin != ResultadoVotacion.EN_PROCESO) {
            throw new Exception("No hay una votación en curso");
        }
        
        if(votacion.obtenerVoto(jugador) != null) {
            throw new Exception("Usted ya ha votado");
        } 
        
        if(estado == EstadoPartida.PREPARANDO && voto == Voto.EN_CONTRA) {
                throw new Exception("En el lobby no se permiten votos en contra");       
        } 
        
        votacion.Votar(jugador, voto);
        
        if(estado == EstadoPartida.EN_CURSO) {
            evaluarVotacionFin();
        } else {
            notificar(Event.VOTO_REGISTRADO);
        }
        
        if (estado == EstadoPartida.PREPARANDO && verificarComienzo()) {
            empezarJuego();
        }

    }
    
    
    /**
     * Abre una votación para terminar la partida antes de tiempo.
     * Quien la propone vota a favor automáticamente.
     */
    @Override
    public void proponerFin(String nombre) throws Exception {
        Objects.requireNonNull(nombre, "El nombre es obligatorio");

        if(estado == EstadoPartida.PREPARANDO) {
            throw new Exception("La partida aún no ha comenzado");
        }
        if(estado == EstadoPartida.TERMINADA) {
            throw new Exception("La partida ya terminó");
        }

        Jugador jugador = buscarJugador(nombre);

        if(jugador == null) {
            throw new Exception("No existe un jugador con este nombre en partida");
        }

        if(resultadoFin == ResultadoVotacion.EN_PROCESO) {
            throw new Exception("Ya hay una votación en curso");
        }

        votacion.limpiar();
        resultadoFin = ResultadoVotacion.EN_PROCESO;
        votacion.Votar(jugador, Voto.FAVOR);

        evaluarVotacionFin();
    }
  
    
    
    
    
    @Override
    public void seleccionarPunto(int x, int y, String nombre) throws Exception{
        Objects.requireNonNull(nombre, "El nombre es obligatorio");

        if(estado == EstadoPartida.PREPARANDO) {
            throw new Exception("La partida aún no ha comenzado");
        }
        if(estado == EstadoPartida.TERMINADA) {
            throw new Exception("La partida ya terminó");
        }
        
        Jugador jugador = buscarJugador(nombre);
        
        if(jugador == null) {
            throw new Exception("No existe un jugador con este nombre en partida");
        }
        
        if(!estaEnTurno(jugador)) {
            throw new Exception("No estás en turno actualmente");
        }
        
        if(estaDeseleccionado(x, y)) {
            
            onSelect = null;
            
            notificar(Event.PUNTO_DESELECCIONADO);
        } else if(estaIntentandoJugada(x, y)) {
            
            Punto punto = onSelect.getPutnoJugado();
            Jugada jugada = tablero.jugarLinea(x, y, punto.getFila(), punto.getColumna(), jugador);
            jugadas.add(jugada);
            onSelect = null;
            
            notificar(Event.LINEA_TRAZADA);
            
            if(tablero.estaCompleto()) {
                estado = EstadoPartida.TERMINADA;
                notificar(Event.JUEGO_TERMINADO);
            }
            
            if(jugada.getRecuadrosCompletados().isEmpty()) {
                cambiarTurno();
                notificar(Event.CAMBIO_TURNO);
                
            }
            
            
        } else if(onSelect == null) {
            
            onSelect = tablero.seleccionarPunto(x, y);
            
            notificar(Event.PUNTO_SELECCIONADO);
            
        } else {
            throw new Exception("Tu vista está desactualizada");
        }
        
        
        
    }
    
    @Override
    public Jugador getJugadorEnTurno() {
        return turnoActual;
    }
    
    @Override
    public void quitarVoto(String nombre) throws Exception {
        Objects.requireNonNull(nombre, "El nombre es obligatorio");

        if (estado != EstadoPartida.PREPARANDO) {
            throw new Exception("Solo se puede quitar el voto en la sala de espera");
        }

        Jugador jugador = buscarJugador(nombre);
        if (jugador == null) {
            throw new Exception("No se encontró ningun jugador con ese nombre");
        }
        if (votacion.obtenerVoto(jugador) == null) {
            throw new Exception("Usted no ha votado");
        }

        votacion.quitarVoto(jugador);

        notificar(Event.VOTO_REGISTRADO);
    }


    @Override
    public List<Avatar> getAvataresDisponibles() {
        List<Avatar> avataresDisponibles = new ArrayList<>();

        for (Avatar avatar : Avatar.values()) {
            if (avatarDisponible(avatar)) {
                avataresDisponibles.add(avatar);
            }
        }

        return avataresDisponibles;
    }


    @Override
    public PuntoSeleccionado getPuntoSeleccionado() {
        return onSelect;     
    }

    @Override
    public Jugada getUltimaJugada() {
        return jugadas.isEmpty() ? null : jugadas.get(jugadas.size() - 1);
    }

    @Override
    public List<Jugador> getJugadores() {
        return List.copyOf(jugadores);
    }

    @Override
    public List<Jugada> getJugadas() {
        return List.copyOf(jugadas);
    }

    @Override
    public Votacion getVotacion() {
        return votacion;
    }

    @Override
    public ResultadoVotacion getResultadoVotacion() {
        return resultadoFin;
    }

    /**
     * Jugadores ordenados de mayor a menor puntaje. En caso de empate conservan su orden.
     */
    @Override
    public List<Jugador> getClasificacion() throws Exception {
        if(estado != EstadoPartida.TERMINADA) {
            throw new Exception("La partida aún no ha terminado");
        }

        List<Jugador> clasificacion = new ArrayList<>(jugadores);
        clasificacion.sort((a, b) -> Integer.compare(b.getPuntaje(), a.getPuntaje()));

        return clasificacion;
    }

    /**
     * Jugadores con el puntaje más alto; hay más de uno cuando empatan.
     */
    @Override
    public List<Jugador> getGanadores() throws Exception {
        List<Jugador> clasificacion = getClasificacion();
        List<Jugador> ganadores = new ArrayList<>();

        int puntajeMaximo = clasificacion.get(0).getPuntaje();

        for(Jugador jugador: clasificacion) {
            if(jugador.getPuntaje() == puntajeMaximo) {
                ganadores.add(jugador);
            }
        }

        return ganadores;
    }

    

}