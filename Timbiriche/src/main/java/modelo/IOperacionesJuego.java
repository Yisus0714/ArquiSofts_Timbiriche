package modelo;

/**
 * Contrato de operaciones que el Modelo MVC puede solicitar
 * al modelo general del juego.
 *
 * Esta interfaz marca la frontera entre el MVC local de un jugador
 * y el dominio del juego.
 *
 * IMPORTANTE:
 * El Controlador NO usa esta interfaz directamente.
 * El Controlador habla con ModeloPartida mediante IOperacionesPartida.
 * ModeloPartida es quien utiliza este contrato para comunicarse
 * con el dominio.
 *
 * En UML:
 * - Se representará como <<interface>>.
 * - ModeloPartida tendrá una dependencia hacia esta interfaz.
 * - La implementación real del dominio, por ejemplo Partida,
 *   deberá implementar este contrato.
 */
public interface IOperacionesJuego {

    /**
     * Solicita al dominio realizar una jugada lógica.
     *
     * Aquí ya no existen coordenadas de pantalla.
     * La jugada se expresa usando fila, columna y orientación.
     *
     * En UML:
     * + jugar(jugadorId: int, horizontal: boolean, fila: int, col: int): void
     */
    void jugar(
            int jugadorId,
            boolean horizontal,
            int fila,
            int col
    );
}
