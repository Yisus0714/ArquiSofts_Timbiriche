/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Dominio.ILobby;
import Dominio.Jugador;
import Dominio.Votacion;
import Dominio.Voto;
import aplicacion.CoordinadorLobby;
import dto.JugadorLobbyDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisb
 */
public class ModeloLobby implements IConsultaLobby, ObserverDominio {
    
    private CoordinadorLobby coordinador;
    private EsquemaColores colores;
    private String jugadorLocal;
    private ILobby dominio;
    private List<ObserverModeloLobby> observadores;

    public ModeloLobby(CoordinadorLobby coordinador, EsquemaColores colores, String jugadorLocal, ILobby dominio) {
        this.coordinador = coordinador;
        this.colores = colores;
        this.jugadorLocal = jugadorLocal;
        this.dominio = dominio;
        this.observadores = new ArrayList<>();
    }
    
    
   public void suscribir(ObserverModeloLobby observer) {
        observadores.add(observer);
   }
   
   public void votar(Voto voto) throws Exception {
        dominio.votar(voto, jugadorLocal);
   }
   
   public void abandonar() throws Exception {
        dominio.abandonar(jugadorLocal);
        coordinador.volverARegistro(this);
   }
   
   public void quitarVoto() throws Exception {
       dominio.quitarVoto(jugadorLocal);
   }
   

    @Override
    public List<JugadorLobbyDTO> getJugadores() throws Exception {
        List<Jugador> jugadores = dominio.getJugadores();
        Votacion votacion = dominio.getVotacion();
        return DTOMapper.toJugadoresLobbyDTO(jugadores, jugadorLocal, colores, votacion);
    }

    @Override
    public void notificarRegistro() {
        for(ObserverModeloLobby obs : new ArrayList<>(observadores)) {
            obs.actualizarRegistro();
        }
    }

    @Override
    public void notificarComienzo() {
        coordinador.iniciarJuego(this, jugadorLocal, colores);
    }

    @Override
    public void notificarSeleccion() { }

    @Override
    public void notificarDeseleccion() { }

    @Override
    public void notificarJugada() { }

    @Override
    public void notificarCambioTurno() { }

    @Override
    public void notificarAbandono() {
        for(ObserverModeloLobby obs : new ArrayList<>(observadores)) {
            obs.actualizarAbandono();
        }
    }

    @Override
    public void notificarFin() { }

    @Override
    public void notificarVoto() {
        for(ObserverModeloLobby obs : new ArrayList<>(observadores)) {
            obs.actualizarVoto();
        }
    }

    
    
}
