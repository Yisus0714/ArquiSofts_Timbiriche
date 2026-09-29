package main;

import controlador.ControladorPartida;
import eventos.EstadoPartida;
import modelo.IOperacionesJuego;
import modelo.IReceptorEstadoPartida;
import modelo.ModeloPartida;
import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;

import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de crear los objetos principales de la aplicación
 * y establecer las relaciones entre ellos.
 *
 * Aquí no se implementan reglas del juego.
 * Su responsabilidad es "armar" los componentes.
 *
 * En esta versión se crean dos MVC independientes, uno por jugador,
 * y ambos se conectan al mismo modelo general del juego.
 *
 * En UML:
 * - Ensamblador tendrá dependencias de creación hacia los elementos
 *   que necesita construir y relacionar.
 */
public class Ensamblador {

    public void ensamblar() {

        // ==========================================
        // 1. Crear el modelo general del juego
        // ==========================================

        /*
         * Por ahora usamos JuegoTemporal porque la implementación
         * real de Partida todavía no está integrada.
         *
         * Los dos jugadores compartirán este mismo objeto.
         */
        JuegoTemporal juego =
                new JuegoTemporal();


        // ==========================================
        // 2. Crear los Modelos MVC de cada jugador
        // ==========================================

        /*
         * IMPORTANTE:
         * Cada jugador tiene su propio Modelo del MVC.
         *
         * Estos objetos NO son el modelo general del juego.
         */
        ModeloPartida modeloJugador1 =
                new ModeloPartida();

        ModeloPartida modeloJugador2 =
                new ModeloPartida();


        // ==========================================
        // 3. Crear los Controladores
        // ==========================================

        ControladorPartida controladorJugador1 =
                new ControladorPartida();

        ControladorPartida controladorJugador2 =
                new ControladorPartida();


        // ==========================================
        // 4. Crear las Vistas
        // ==========================================

        VentanaPrincipal vistaJugador1 =
                new VentanaPrincipal(1);

        VentanaPrincipal vistaJugador2 =
                new VentanaPrincipal(2);


        // ==========================================
        // 5. Modelo MVC -> Modelo general
        // ==========================================

        /*
         * Ambos Modelos MVC utilizan el mismo modelo general.
         *
         * Así, una jugada realizada por cualquiera de los jugadores
         * termina modificando el mismo estado lógico del juego.
         */
        modeloJugador1.setJuego(juego);
        modeloJugador2.setJuego(juego);


        // ==========================================
        // 6. Controlador -> Modelo MVC
        // ==========================================

        /*
         * Cada Controlador trabaja únicamente con el Modelo MVC
         * correspondiente a su jugador.
         */
        controladorJugador1.setModelo(modeloJugador1);
        controladorJugador2.setModelo(modeloJugador2);


        // ==========================================
        // 7. Asociaciones de la Vista
        // ==========================================

        /*
         * Vista -> Controlador:
         * la Vista envía acciones mediante FuenteDeJugadas.
         */
        vistaJugador1.setFuenteDeJugadas(
                controladorJugador1
        );

        vistaJugador2.setFuenteDeJugadas(
                controladorJugador2
        );

        /*
         * Vista -> Modelo MVC:
         * la Vista consulta información mediante IConsultaModeloPartida.
         */

        vistaJugador1.setModelo(modeloJugador1);
        vistaJugador2.setModelo(modeloJugador2);



        // ==========================================
        // 8. Observer: Modelo MVC -> Vista
        // ==========================================

        /*
         * Cada Vista observa solamente a su Modelo MVC.
         *
         * Cuando ese Modelo recibe un nuevo estado,
         * notifica a la Vista mediante Observer.
         */
        modeloJugador1.agregarObservador(
                vistaJugador1
        );

        modeloJugador2.agregarObservador(
                vistaJugador2
        );


        // ==========================================
        // 9. Modelo general -> Modelos MVC
        // ==========================================

        /*
         * El modelo general conoce solamente el contrato
         * IReceptorEstadoPartida.
         *
         * No necesita conocer directamente la clase ModeloPartida.
         *
         * Cuando cambia el estado del juego, ambos Modelos MVC
         * reciben la actualización.
         */
        juego.agregarReceptorEstado(
                modeloJugador1
        );

        juego.agregarReceptorEstado(
                modeloJugador2
        );


        // ==========================================
        // 10. Posicionar y mostrar las Vistas
        // ==========================================

        vistaJugador1.setLocation(100, 100);
        vistaJugador2.setLocation(550, 100);

        vistaJugador1.setVisible(true);
        vistaJugador2.setVisible(true);
    }


    /**
     * Simulación temporal del modelo general del juego.
     *
     * IMPORTANTE:
     * Esta clase NO es el Modelo del MVC.
     *
     * Representa provisionalmente al modelo general del juego
     * mientras no tengamos integrada la implementación real
     * de Partida y del resto del dominio.
     *
     * Su trabajo temporal es:
     * - recibir las jugadas de los Modelos MVC;
     * - generar un EstadoPartida;
     * - enviar ese estado a todos los Modelos MVC.
     *
     * Esta clase es únicamente de apoyo para las pruebas
     * y NO debe aparecer como elemento definitivo en UML.
     */
    private static class JuegoTemporal
            implements IOperacionesJuego {

        /*
         * Modelos MVC interesados en recibir los cambios
         * producidos por el modelo general.
         *
         * Se utiliza la interfaz para mantener bajo acoplamiento.
         */
        private final List<IReceptorEstadoPartida> receptores =
                new ArrayList<>();

        /*
         * Elementos temporales del dominio.
         *
         * Más adelante serán sustituidos por las implementaciones
         * reales de Partida, Tablero, Jugador, etc.
         */
        private final List<Jugador> jugadores =
                new ArrayList<>();

        private final TableroLectura tablero =
                new TableroTemporal();


        /**
         * Registra un Modelo MVC interesado en recibir
         * los cambios del estado general del juego.
         *
         * En UML del diseño definitivo, esta responsabilidad
         * probablemente pertenecerá al modelo general real.
         */
        public void agregarReceptorEstado(
                IReceptorEstadoPartida receptor) {

            receptores.add(receptor);
        }


        /**
         * Recibe una jugada proveniente de alguno de los Modelos MVC.
         *
         * Por ahora no existen reglas reales de Timbiriche.
         * Solamente simulamos que la jugada produjo un cambio
         * en el estado general.
         */
        @Override
        public void jugar(
                int jugadorId,
                boolean horizontal,
                int fila,
                int col) {

            EstadoPartida estado =
                    new EstadoPartida(
                            tablero,
                            jugadores,
                            jugadorId,
                            false,
                            null
                    );

            notificarReceptores(estado);
        }


        /**
         * Entrega el nuevo estado a todos los Modelos MVC.
         *
         * Aquí comienza el camino de regreso:
         *
         * Modelo general
         *      -> Modelo MVC
         *      -> Observer
         *      -> Vista
         */
        private void notificarReceptores(
                EstadoPartida estado) {

            for (IReceptorEstadoPartida receptor : receptores) {
                receptor.actualizarEstado(estado);
            }
        }
    }


    /**
     * Implementación temporal de TableroLectura.
     *
     * Existe solamente porque EstadoPartida necesita una referencia
     * a un tablero.
     *
     * No contiene todavía reglas reales de Timbiriche
     * y NO debe aparecer en los diagramas UML definitivos.
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