package modelo.partida;

import modelo.tablero.TableroLectura;
import modelo.jugador.Jugador;

import java.util.List;

public class ReglasFin {

    /**
     * La partida termina si no quedan cuadros libres,
     * o si solo queda un jugador (los demás abandonaron).
     */
    public boolean terminada(TableroLectura tablero, List<Jugador> jugadores) {
        if (jugadores.size() <= 1) {
            return true;
        }
        return !tablero.quedanCuadrosLibres();
    }
}