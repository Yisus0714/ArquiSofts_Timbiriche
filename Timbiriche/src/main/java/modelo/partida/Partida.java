package modelo.partida;

import modelo.tablero.Tablero;
import modelo.jugador.Jugador;
import eventos.Observador;
import eventos.EstadoPartida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partida {

    private final Tablero tablero;
    private final List<Jugador> jugadores;
    private final List<Observador> observadores = new ArrayList<>();
    private final ReglasFin reglasFin = new ReglasFin();
    private final Ganador ganador = new Ganador();

    private int indiceTurno;
    private boolean iniciada = false;

    public Partida(List<Jugador> jugadores) {
        if (jugadores == null || jugadores.size() < 2) {
            throw new IllegalArgumentException("Se requieren al menos 2 jugadores");
        }
        this.jugadores = new ArrayList<>(jugadores);
        int puntosPorLado = Tablero.tamanoPara(jugadores.size());
        this.tablero = new Tablero(puntosPorLado);
    }

    public void iniciar() {
        Collections.shuffle(jugadores);
        indiceTurno = 0;
        iniciada = true;
        notificarCambio();
    }

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void quitarObservador(Observador o) {
        observadores.remove(o);
    }

    public int getJugadorEnTurno() {
        return jugadores.get(indiceTurno).getId();
    }

    /**
     * Aplica una jugada. Devuelve true si la jugada fue válida.
     */
    public boolean jugarLinea(int jugadorId, boolean horizontal, int fila, int col) {
        if (!iniciada || reglasFin.terminada(tablero, jugadores)) {
            return false;
        }
        if (jugadorId != getJugadorEnTurno()) {
            return false; // no es su turno
        }

        int completados = horizontal
                ? tablero.trazarLineaH(fila, col, jugadorId)
                : tablero.trazarLineaV(fila, col, jugadorId);

        if (completados < 0) {
            return false; // línea ya trazada
        }

        Jugador actual = jugadorPorId(jugadorId);
        if (completados > 0) {
            actual.sumarPuntos(completados);
            // el mismo jugador sigue: no se avanza el turno
        } else {
            avanzarTurno();
        }

        notificarCambio();
        return true;
    }

    /** Quita al jugador y sus puntos. Los turnos no se modifican. */
    public void abandonar(int jugadorId) {
        int indiceAbandona = jugadores.indexOf(jugadorPorId(jugadorId));
        if (indiceAbandona == -1) return;

        boolean eraElTurno = (indiceAbandona == indiceTurno);
        jugadores.remove(indiceAbandona);

        if (jugadores.isEmpty()) {
            notificarCambio();
            return;
        }

        // Ajuste del índice para que el turno "no se modifique" en términos
        // de orden relativo entre los que quedan.
        if (indiceAbandona < indiceTurno) {
            indiceTurno--;
        } else if (eraElTurno) {
            indiceTurno = indiceTurno % jugadores.size();
        }

        notificarCambio();
    }

    private void avanzarTurno() {
        indiceTurno = (indiceTurno + 1) % jugadores.size();
    }

    private Jugador jugadorPorId(int id) {
        return jugadores.stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + id));
    }

    private void notificarCambio() {
        EstadoPartida estado = construirEstadoActual();
        for (Observador o : observadores) {
            o.alCambiarPartida(estado);
        }
    }

    private EstadoPartida construirEstadoActual() {
        boolean terminada = reglasFin.terminada(tablero, jugadores);
        Integer idGanador = terminada ? ganador.calcular(jugadores) : null;
        return new EstadoPartida(
                tablero,
                jugadores,
                jugadores.isEmpty() ? -1 : getJugadorEnTurno(),
                terminada,
                idGanador
        );
    }
}