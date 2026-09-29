package controlador;

import modelo.IOperacionesPartida;

/**
 * Controlador del MVC correspondiente a una partida.
 *
 * Su responsabilidad es recibir las acciones enviadas por la Vista
 * y delegarlas al Modelo MVC mediante IOperacionesPartida.
 *
 * IMPORTANTE:
 * - No contiene reglas del juego.
 * - No conoce directamente la Vista.
 * - No conoce el modelo general del juego ni clases como Partida o Tablero.
 *
 * En UML:
 * - Implementa FuenteDeJugadas.
 * - Depende de IOperacionesPartida.
 */
public class ControladorPartida implements FuenteDeJugadas {

    /*
     * Contrato utilizado para enviar operaciones al Modelo MVC.
     *
     * El Controlador depende de la abstracción IOperacionesPartida
     * y no de una implementación concreta como ModeloPartida.
     */
    private IOperacionesPartida modelo;

    /**
     * Recibe la referencia al Modelo MVC.
     *
     * Esta asociación será realizada por Ensamblador.
     *
     * En UML:
     * + setModelo(modelo: IOperacionesPartida): void
     */
    public void setModelo(IOperacionesPartida modelo) {
        this.modelo = modelo;
    }

    /**
     * Recibe desde la Vista la intención del jugador de realizar
     * una jugada y la delega al Modelo MVC.
     *
     * El Controlador no valida la jugada ni calcula reglas.
     *
     * En UML:
     * + alJugar(jugadorId: int, horizontal: boolean,
     *           fila: int, col: int): void
     */
    @Override
    public void alJugar(int jugadorId,
                        boolean horizontal,
                        int fila,
                        int col) {

        modelo.jugar(
                jugadorId,
                horizontal,
                fila,
                col
        );
    }
}