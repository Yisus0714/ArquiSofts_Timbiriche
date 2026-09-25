package eventos;
import modelo.IOperacionesPartida;
import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlujoObserverTest {

    @Test
    void debeNotificarALaVistaCuandoCambiaLaPartida() {

        ModeloObservableFalso modelo = new ModeloObservableFalso();
        VistaFalsa vista = new VistaFalsa();

        modelo.agregarObservador(vista);

        modelo.jugar(
                1,
                true,
                2,
                3
        );

        assertTrue(vista.fueNotificada);
        assertNotNull(vista.estadoRecibido);
        assertEquals(1, vista.estadoRecibido.getJugadorEnTurno());
    }

    @Test
    void debeNotificarADosVistasCuandoCambiaLaPartida() {

        ModeloObservableFalso modelo = new ModeloObservableFalso();

        VistaFalsa vistaJugador1 = new VistaFalsa();
        VistaFalsa vistaJugador2 = new VistaFalsa();

        modelo.agregarObservador(vistaJugador1);
        modelo.agregarObservador(vistaJugador2);

        modelo.jugar(
                1,
                true,
                2,
                3
        );

        assertTrue(vistaJugador1.fueNotificada);
        assertTrue(vistaJugador2.fueNotificada);

        assertNotNull(vistaJugador1.estadoRecibido);
        assertNotNull(vistaJugador2.estadoRecibido);

        assertEquals(
                vistaJugador1.estadoRecibido.getJugadorEnTurno(),
                vistaJugador2.estadoRecibido.getJugadorEnTurno()
        );
    }


    private static class VistaFalsa implements Observador {

        boolean fueNotificada;
        EstadoPartida estadoRecibido;

        @Override
        public void alCambiarPartida(EstadoPartida estado) {
            this.fueNotificada = true;
            this.estadoRecibido = estado;
        }
    }


    private static class ModeloObservableFalso
            implements IOperacionesPartida {

        private final List<Observador> observadores =
                new ArrayList<>();

        public void agregarObservador(Observador observador) {
            observadores.add(observador);
        }

        @Override
        public void jugar(int jugadorId,
                          boolean horizontal,
                          int fila,
                          int col) {

            EstadoPartida estado =
                    crearEstado(jugadorId);

            notificarObservadores(estado);
        }

        private void notificarObservadores(
                EstadoPartida estado) {

            for (Observador observador : observadores) {
                observador.alCambiarPartida(estado);
            }
        }

        private EstadoPartida crearEstado(
                int jugadorEnTurno) {

            return new EstadoPartida(
                    new TableroFalso(),
                    new ArrayList<>(),
                    jugadorEnTurno,
                    false,
                    null
            );
        }
    }


    private static class TableroFalso
            implements TableroLectura {

        @Override
        public int puntosPorLado() {
            return 10;
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
            return 81;
        }
    }
}