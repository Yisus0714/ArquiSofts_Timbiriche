package controlador;

/**
 * Contrato que utiliza la Vista para enviar una jugada
 * hacia el Controlador.
 *
 * La Vista no necesita conocer directamente a ControladorPartida;
 * solamente conoce esta interfaz.
 *
 * Esto ayuda a mantener bajo acoplamiento entre Vista y Controlador.
 *
 * En UML:
 * - Se representará como <<interface>>.
 * - VentanaPrincipal tendrá una dependencia hacia esta interfaz.
 * - ControladorPartida implementará este contrato.
 */
public interface FuenteDeJugadas {

    /**
     * Notifica al Controlador que el jugador intentó realizar
     * una jugada.
     *
     * Los datos ya representan una jugada lógica:
     * jugador, orientación, fila y columna.
     *
     * En UML:
     * + alJugar(jugadorId: int, horizontal: boolean,
     *           fila: int, col: int): void
     */
    void alJugar(
            int jugadorId,
            boolean horizontal,
            int fila,
            int col
    );
}