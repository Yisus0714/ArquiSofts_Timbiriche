package modelo.partida;

import modelo.jugador.Jugador;

import java.util.List;

public class Ganador {

    /**
     * Devuelve el id del jugador con mayor puntaje.
     * Si hay empate, devuelve el primero encontrado con el puntaje máximo
     * (el equipo puede decidir si prefiere manejar empate distinto).
     */
    public Integer calcular(List<Jugador> jugadores) {
        if (jugadores.isEmpty()) {
            return null;
        }
        Jugador mejor = jugadores.get(0);
        for (Jugador j : jugadores) {
            if (j.getPuntaje() > mejor.getPuntaje()) {
                mejor = j;
            }
        }
        return mejor.getId();
    }

    /** Útil para mostrar empates en la tabla de posiciones. */
    public boolean hayEmpate(List<Jugador> jugadores) {
        if (jugadores.isEmpty()) return false;
        int maximo = jugadores.stream().mapToInt(Jugador::getPuntaje).max().orElse(0);
        long cantidadConMaximo = jugadores.stream()
                .filter(j -> j.getPuntaje() == maximo)
                .count();
        return cantidadConMaximo > 1;
    }
}