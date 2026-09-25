package controlador;

import modelo.IOperacionesPartida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorPartidaTest {

    @Test
    void debeDelegarLaJugadaAlModelo() {

        ModeloPartidaFalso modelo = new ModeloPartidaFalso();

        ControladorPartida controlador = new ControladorPartida();
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
