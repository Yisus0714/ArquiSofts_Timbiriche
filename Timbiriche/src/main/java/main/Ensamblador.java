package main;

import controlador.ControladorPartida;
import eventos.EstadoPartida;
import eventos.Observador;
import modelo.IOperacionesPartida;
import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;

import java.util.ArrayList;
import java.util.List;

public class Ensamblador {

    public void ensamblar() {

        // ==========================================
        // 1. Crear los objetos
        // ==========================================

        ModeloPartidaTemporal modelo =
                new ModeloPartidaTemporal();

        ControladorPartida controladorJugador1 =
                new ControladorPartida();

        ControladorPartida controladorJugador2 =
                new ControladorPartida();

        VentanaPrincipal vistaJugador1 =
                new VentanaPrincipal(1);

        VentanaPrincipal vistaJugador2 =
                new VentanaPrincipal(2);


        // ==========================================
        // 2. Establecer las asociaciones
        // ==========================================

        controladorJugador1.setModelo(modelo);
        controladorJugador2.setModelo(modelo);

        vistaJugador1.setFuenteDeJugadas(
                controladorJugador1
        );

        vistaJugador2.setFuenteDeJugadas(
                controladorJugador2
        );

        modelo.agregarObservador(vistaJugador1);
        modelo.agregarObservador(vistaJugador2);


        // ==========================================
        // 3. Posicionar y mostrar las vistas
        // ==========================================

        vistaJugador1.setLocation(100, 100);
        vistaJugador2.setLocation(550, 100);

        vistaJugador1.setVisible(true);
        vistaJugador2.setVisible(true);
    }


    /*
     * MODELO TEMPORAL
     *
     * Se utiliza únicamente mientras la implementación
     * real de Partida no se encuentre disponible.
     */
    private static class ModeloPartidaTemporal
            implements IOperacionesPartida {

        private final List<Observador> observadores =
                new ArrayList<>();

        public void agregarObservador(
                Observador observador) {

            observadores.add(observador);
        }

        @Override
        public void jugar(
                int jugadorId,
                boolean horizontal,
                int fila,
                int col) {

            EstadoPartida estado =
                    new EstadoPartida(
                            new TableroTemporal(),
                            new ArrayList<Jugador>(),
                            jugadorId,
                            false,
                            null
                    );

            notificarObservadores(estado);
        }

        private void notificarObservadores(
                EstadoPartida estado) {

            for (Observador observador : observadores) {
                observador.alCambiarPartida(estado);
            }
        }
    }


    /*
     * TABLERO TEMPORAL
     *
     * Solo permite construir EstadoPartida.
     * No implementa todavía reglas de Timbiriche.
     */
    private static class TableroTemporal
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