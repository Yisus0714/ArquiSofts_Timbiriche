package controlador;

import modelo.IOperacionesPartida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de integración sencilla entre Vista, Controlador
 * y el contrato de operaciones del Modelo MVC.
 *
 * El objetivo es comprobar el flujo:
 *
 * Vista -> FuenteDeJugadas -> ControladorPartida
 *       -> IOperacionesPartida
 *
 * No se prueban aquí reglas del juego ni el dominio.
 */
public class FlujoVistaControladorTest {

    /**
     * Comprueba que una jugada originada en la Vista
     * llegue hasta el Modelo MVC pasando por el Controlador.
     *
     * Este flujo puede aparecer en UML de secuencia como:
     *
     * Vista -> FuenteDeJugadas : alJugar(...)
     * FuenteDeJugadas / ControladorPartida
     *     -> IOperacionesPartida : jugar(...)
     */
    @Test
    void laVistaDebeEnviarLaJugadaHastaElModelo() {

        ModeloPartidaFalso modelo =
                new ModeloPartidaFalso();

        ControladorPartida controlador =
                new ControladorPartida();

        controlador.setModelo(modelo);

        VistaFalsa vista =
                new VistaFalsa();

        vista.setFuenteDeJugadas(controlador);

        vista.simularJugada(
                2,
                false,
                4,
                5
        );

        assertTrue(modelo.jugarFueInvocado);
        assertEquals(2, modelo.jugadorId);
        assertFalse(modelo.horizontal);
        assertEquals(4, modelo.fila);
        assertEquals(5, modelo.col);
    }

    /**
     * Vista falsa utilizada únicamente para probar
     * la comunicación Vista -> Controlador.
     */
    private static class VistaFalsa {

        private FuenteDeJugadas fuenteDeJugadas;

        public void setFuenteDeJugadas(
                FuenteDeJugadas fuenteDeJugadas) {

            this.fuenteDeJugadas = fuenteDeJugadas;
        }

        public void simularJugada(
                int jugadorId,
                boolean horizontal,
                int fila,
                int col) {

            fuenteDeJugadas.alJugar(
                    jugadorId,
                    horizontal,
                    fila,
                    col
            );
        }
    }

    /**
     * Modelo MVC falso utilizado para comprobar que
     * el Controlador delegó correctamente la jugada.
     */
    private static class ModeloPartidaFalso
            implements IOperacionesPartida {

        boolean jugarFueInvocado;
        int jugadorId;
        boolean horizontal;
        int fila;
        int col;

        @Override
        public void jugar(
                int jugadorId,
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