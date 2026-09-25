package controlador;
import modelo.IOperacionesPartida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlujoVistaControladorTest {

    @Test
    void laVistaDebeEnviarLaJugadaHastaElModelo() {

        ModeloPartidaFalso modelo = new ModeloPartidaFalso();

        ControladorPartida controlador = new ControladorPartida();
        controlador.setModelo(modelo);

        VistaFalsa vista = new VistaFalsa();
        vista.setFuenteDeJugadas(controlador);

        vista.simularJugada(2, false, 4, 5);

        assertTrue(modelo.jugarFueInvocado);
        assertEquals(2, modelo.jugadorId);
        assertFalse(modelo.horizontal);
        assertEquals(4, modelo.fila);
        assertEquals(5, modelo.col);
    }

    private static class VistaFalsa {

        private FuenteDeJugadas fuenteDeJugadas;

        public void setFuenteDeJugadas(FuenteDeJugadas fuenteDeJugadas) {
            this.fuenteDeJugadas = fuenteDeJugadas;
        }

        public void simularJugada(int jugadorId,
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