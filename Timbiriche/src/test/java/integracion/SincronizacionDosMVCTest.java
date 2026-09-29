package integracion;

import eventos.EstadoPartida;
import modelo.IOperacionesJuego;
import modelo.IReceptorEstadoPartida;
import modelo.ModeloPartida;
import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Prueba de integración de la sincronización entre dos MVC.
 *
 * Se valida que dos Modelos MVC independientes, conectados al mismo
 * modelo general del juego, reciban el mismo EstadoPartida después
 * de una jugada.
 *
 * Este test representa la arquitectura:
 *
 * ModeloPartida1 -> modelo general
 * ModeloPartida2 -> modelo general
 *
 * modelo general -> ModeloPartida1
 * modelo general -> ModeloPartida2
 *
 * No se prueban aquí reglas reales de Timbiriche.
 */
class SincronizacionDosMVCTest {

    /**
     * Valida que dos Modelos MVC distintos reciban el mismo
     * EstadoPartida cuando comparten el mismo modelo general.
     *
     * Esta prueba representa la sincronización entre jugadores
     * explicada por el maestro.
     */
    @Test
    void unaJugadaActualizaAmbosModelosMVC() {

        JuegoFalso juego = new JuegoFalso();

        ModeloPartida modeloJugador1 =
                new ModeloPartida();

        ModeloPartida modeloJugador2 =
                new ModeloPartida();

        // Ambos Modelos MVC usan el mismo modelo general.
        modeloJugador1.setJuego(juego);
        modeloJugador2.setJuego(juego);

        // El modelo general conoce a ambos solamente
        // mediante IReceptorEstadoPartida.
        juego.agregarReceptorEstado(modeloJugador1);
        juego.agregarReceptorEstado(modeloJugador2);

        // Simulamos una jugada realizada por el jugador 1.
        modeloJugador1.jugar(
                1,
                true,
                0,
                0
        );

        EstadoPartida estadoGenerado =
                juego.getUltimoEstado();

        // Ambos Modelos MVC deben haber recibido
        // exactamente el mismo estado.
        assertSame(
                estadoGenerado,
                modeloJugador1.getEstado()
        );

        assertSame(
                estadoGenerado,
                modeloJugador2.getEstado()
        );
    }


    /**
     * Sustituto temporal del modelo general del juego
     * utilizado únicamente para esta prueba.
     *
     * No contiene reglas reales de Timbiriche.
     */
    private static class JuegoFalso
            implements IOperacionesJuego {

        private final List<IReceptorEstadoPartida> receptores =
                new ArrayList<>();

        private final List<Jugador> jugadores =
                new ArrayList<>();

        private final TableroLectura tablero =
                new TableroFalso();

        private EstadoPartida ultimoEstado;

        public void agregarReceptorEstado(
                IReceptorEstadoPartida receptor) {

            receptores.add(receptor);
        }

        @Override
        public void jugar(
                int jugadorId,
                boolean horizontal,
                int fila,
                int col) {

            ultimoEstado =
                    new EstadoPartida(
                            tablero,
                            jugadores,
                            jugadorId,
                            false,
                            null
                    );

            for (IReceptorEstadoPartida receptor : receptores) {
                receptor.actualizarEstado(ultimoEstado);
            }
        }

        public EstadoPartida getUltimoEstado() {
            return ultimoEstado;
        }
    }


    /**
     * Tablero mínimo utilizado solamente para construir
     * el EstadoPartida de la prueba.
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