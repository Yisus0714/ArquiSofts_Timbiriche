package integracion;
import controlador.ControladorPartida;
import controlador.FuenteDeJugadas;
import eventos.EstadoPartida;
import eventos.Observador;
import modelo.IOperacionesPartida;
import modelo.tablero.TableroLectura;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlujoCompletoMVCTest {

    @Test
    void unaJugadaDebeActualizarAmbasVistas() {

        ModeloObservableFalso modelo = new ModeloObservableFalso();

        ControladorPartida controladorJugador1 = new ControladorPartida();
        controladorJugador1.setModelo(modelo);

        VistaFalsa vistaJugador1 = new VistaFalsa();
        VistaFalsa vistaJugador2 = new VistaFalsa();

        vistaJugador1.setFuenteDeJugadas(controladorJugador1);

        modelo.agregarObservador(vistaJugador1);
        modelo.agregarObservador(vistaJugador2);

        vistaJugador1.simularJugada(
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

        private FuenteDeJugadas fuenteDeJugadas;

        boolean fueNotificada;
        EstadoPartida estadoRecibido;

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

            EstadoPartida estado = new EstadoPartida(
                    new TableroFalso(),
                    new ArrayList<>(),
                    jugadorId,
                    false,
                    null
            );

            for (Observador observador : observadores) {
                observador.alCambiarPartida(estado);
            }
        }
    }

    private static class TableroFalso
            implements TableroLectura {

        @Override
        public int puntosPorLado() {
            return 10;
        }

        @Override
        public boolean lineaTrazada(boolean horizontal,
                                    int fila,
                                    int col) {
            return false;
        }

        @Override
        public int duenoDelCuadro(int fila, int col) {
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