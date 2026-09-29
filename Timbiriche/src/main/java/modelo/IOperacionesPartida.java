package modelo;
/**
 * Contrato de operaciones que el Controlador puede solicitar
 * al Modelo MVC de una partida.
 *
 * IMPORTANTE:
 * Esta interfaz NO representa directamente al modelo general
 * del juego o Dominio.
 *
 * El Controlador utiliza este contrato para comunicarse con
 * ModeloPartida, y ModeloPartida será quien delegue después
 * la operación al modelo general mediante IOperacionesJuego.
 *
 * En UML:
 * - Se representará como <<interface>>.
 * - ControladorPartida tendrá una dependencia hacia esta interfaz.
 * - ModeloPartida implementará este contrato.
 */
public interface IOperacionesPartida {

    /**
     * Solicita al Modelo MVC procesar la intención de realizar
     * una jugada.
     *
     * El Modelo MVC no decide aquí las reglas del Timbiriche;
     * posteriormente delega la operación al modelo general.
     *
     * En UML:
     * + jugar(jugadorId: int, horizontal: boolean,
     *         fila: int, col: int): void
     */
    void jugar(int jugadorId,
               boolean horizontal,
               int fila,
               int col);

}
