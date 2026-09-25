package eventos;


public interface Observador {

    /**
     * Se invoca cada vez que el estado de la partida cambia:
     * una jugada, un cuadro completado, un cambio de turno,
     * un abandono o el fin de la partida.
     *
     * @param estado snapshot del estado actual de la partida
     */
    void alCambiarPartida(EstadoPartida estado);
}