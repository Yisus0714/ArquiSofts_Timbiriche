package controlador;

import modelo.IOperacionesPartida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de ControladorPartida.
 *
 * El objetivo es comprobar que el Controlador solamente
 * recibe la acción proveniente de la Vista y la delega
 * al Modelo MVC mediante IOperacionesPartida.
 *
 * No se prueban aquí reglas del juego ni comportamiento
 * del dominio.
 */
public class ControladorPartidaTest {

    /**
     * Comprueba que alJugar() delegue la jugada al Modelo MVC
     * conservando exactamente los datos recibidos.
     *
     * Este flujo puede aparecer después en el UML de secuencia:
     *
     * Vista -> ControladorPartida : alJugar(...)
     * ControladorPartida -> IOperacionesPartida : jugar(...)
     */
    @Test
    void debeDelegarLaJugadaAlModelo() {

        ModeloPartidaFalso modelo =
                new ModeloPartidaFalso();

        ControladorPartida controlador =
                new ControladorPartida();

        controlador.setModelo(modelo);

        controlador.alJugar(
                1,
                true,
                2,
                3
        );

        assertTrue(modelo.jugarFueInvocado);
        assertEquals(1, modelo.jugadorId);
        assertTrue(modelo.horizontal);
        assertEquals(2, modelo.fila);
        assertEquals(3, modelo.col);
    }

    /**
     * Implementación falsa de IOperacionesPartida.
     *
     * Se utiliza solamente para comprobar qué datos
     * recibió desde el Controlador.
     */
    private static class ModeloPartidaFalso
            implements IOperacionesPartida {

        boolean jugarFueInvocado;
        int jugadorId;
        boolean horizontal;
        int fila;
        int col;

        @Override
        public void jugar(int jugadorId,
                          boolean horizontal,
                          int fila,
                          int col) {

            this.jugarFueInvocado = true;
            this.jugadorId = jugadorId;
            this.horizontal = horizontal;
            this.fila = fila;
            this.col = col;
        }
    }
}