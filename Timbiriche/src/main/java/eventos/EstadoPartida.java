package eventos;

import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;

import java.util.List;

public class EstadoPartida {
    private final TableroLectura tablero;
    private final List<Jugador> jugadores;
    private final int jugadorEnTurno;
    private final boolean partidaTerminada;
    private final Integer idGanador;

    public EstadoPartida(TableroLectura tablero, List<Jugador> jugadores, int jugadorEnTurno, boolean partidaTerminada, Integer idGanador) {
        this.tablero = tablero;
        this.jugadores = jugadores;
        this.jugadorEnTurno = jugadorEnTurno;
        this.partidaTerminada = partidaTerminada;
        this.idGanador = idGanador;
    }

    public TableroLectura getTablero() {
        return tablero;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public Integer getIdGanador() {
        return idGanador;
    }
}
