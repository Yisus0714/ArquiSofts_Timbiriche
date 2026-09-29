package modelo;

import eventos.EstadoPartida;
import eventos.Observador;
import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

class ModeloPartidaTest {

    /**
     * Prueba que ModeloPartida conserve el estado recibido
     * y notifique al observador registrado.
     */
    @Test
    void actualizarEstadoGuardaYNotifica() {

        ModeloPartida modelo = new ModeloPartida();

        EstadoPartida estadoEsperado =
                new EstadoPartida(
                        new TableroFalso(),
                        new ArrayList<Jugador>(),
                        1,
                        false,
                        null
                );

        ObservadorFalso observador =
                new ObservadorFalso();

        modelo.agregarObservador(observador);

        modelo.actualizarEstado(estadoEsperado);

        // El Modelo MVC debe conservar el estado recibido.
        assertSame(
                estadoEsperado,
                modelo.getEstado()
        );

        // El observador debe recibir exactamente ese mismo estado.
        assertSame(
                estadoEsperado,
                observador.estadoRecibido
        );
    }

    /**
     * Observador mínimo utilizado solamente para esta prueba.
     */
    private static class ObservadorFalso
            implements Observador {

        private EstadoPartida estadoRecibido;

        @Override
        public void alCambiarPartida(EstadoPartida estado) {
            estadoRecibido = estado;
        }
    }

    /**
     * Tablero mínimo para poder construir EstadoPartida.
     * No representa la implementación real del dominio.
     */
    private static class TableroFalso
            implements TableroLectura {

        @Override
        public int puntosPorLado() {
            return 3;
        }

        @Override
        public boolean lineaTrazada(
                boolean horizontal,
                int fila,
                int col) {
            return false;
        }

        @Override
        public int duenoDelCuadro(
                int fila,
                int col) {
            return 0;
        }

        @Override
        public boolean quedanCuadrosLibres() {
            return true;
        }

        @Override
        public int totalCuadros() {
            return 4;
        }
    }
}