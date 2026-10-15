package eventos;

/**
 * Hechos que viajan por el observer push. El evento solo dice QUÉ pasó;
 * quien lo recibe consulta el detalle con un get.
 */
public enum Event {

    // ------------------------------------------------------------------
    // Dominio -> Modelo (los 9 notificar que emite Partida)
    // ------------------------------------------------------------------

    /** Un jugador se registró. */
    JUGADOR_REGISTRADO,

    /** La partida arrancó: tablero creado y primer turno fijado. */
    JUEGO_INICIADO,

    /** Se seleccionó un punto. */
    PUNTO_SELECCIONADO,

    /** Se deseleccionó el punto, o se limpió por un abandono. */
    PUNTO_DESELECCIONADO,

    /** Se trazó una línea. */
    LINEA_TRAZADA,

    /** Cambió el jugador en turno. */
    CAMBIO_TURNO,

    /** Un jugador abandonó, en el lobby o en la partida. */
    JUGADOR_ABANDONO,

    /** Se registró, quitó o resolvió un voto. */
    VOTO_REGISTRADO,

    /** La partida terminó: tablero completo, acuerdo de fin o un solo jugador. */
    JUEGO_TERMINADO,

    // ------------------------------------------------------------------
    // Modelo -> Modelo / Vista (no los emite el Dominio)
    // ------------------------------------------------------------------

    /**
     * Este jugador pasa a la pantalla de resultados. Lo emite ModeloJuego (solo para
     * los jugadores que siguen en la partida) y lo escuchan VistaJuego y ModeloFinal.
     */
    RESULTADOS_SOLICITADOS
}
