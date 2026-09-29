package integracion;

import controlador.ControladorPartida;
import controlador.FuenteDeJugadas;
import eventos.EstadoPartida;
import eventos.Observador;
import modelo.IOperacionesJuego;
import modelo.IReceptorEstadoPartida;
import modelo.ModeloPartida;
import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de integración del flujo completo de una jugada
 * usando la arquitectura MVC actual.
 *
 * Se valida el recorrido:
 *
 * Vista
 * -> Controlador
 * -> Modelo MVC
 * -> Modelo general del juego
 * -> Modelos MVC
 * -> Observer
 * -> Vistas
 *
 * IMPORTANTE:
 * Se utilizan las clases reales ControladorPartida y ModeloPartida.
 * La Vista y el modelo general son falsos porque todavía no tenemos
 * integradas sus implementaciones definitivas.
 *
 * Este flujo puede servir después como base para el
 * diagrama de secuencia de diseño.
 */
public class FlujoCompletoMVCTest {

    /**
     * Comprueba que una jugada iniciada desde la Vista del jugador 1
     * recorra toda la estructura y termine actualizando ambas Vistas.
     */
    @Test
    void unaJugadaDebeActualizarAmbasVistas() {

        // ==========================================
        // 1. Crear modelo general falso
        // ==========================================

        JuegoFalso juego =
                new JuegoFalso();


        // ==========================================
        // 2. Crear Modelos MVC
        // ==========================================

        ModeloPartida modeloJugador1 =
                new ModeloPartida();

        ModeloPartida modeloJugador2 =
                new ModeloPartida();

        modeloJugador1.setJuego(juego);
        modeloJugador2.setJuego(juego);


        // ==========================================
        // 3. Crear Controlador del jugador 1
        // ==========================================

        ControladorPartida controladorJugador1 =
                new ControladorPartida();

        controladorJugador1.setModelo(
                modeloJugador1
        );


        // ==========================================
        // 4. Crear Vistas falsas
        // ==========================================

        VistaFalsa vistaJugador1 =
                new VistaFalsa();

        VistaFalsa vistaJugador2 =
                new VistaFalsa();

        vistaJugador1.setFuenteDeJugadas(
                controladorJugador1
        );


        // ==========================================
        // 5. Observer: Modelo MVC -> Vista
        // ==========================================

        modeloJugador1.agregarObservador(
                vistaJugador1
        );

        modeloJugador2.agregarObservador(
                vistaJugador2
        );


        // ==========================================
        // 6. Modelo general -> Modelos MVC
        // ==========================================

        juego.agregarReceptorEstado(
                modeloJugador1
        );

        juego.agregarReceptorEstado(
                modeloJugador2
        );


        // ==========================================
        // 7. Simular jugada desde Vista 1
        // ==========================================

        vistaJugador1.simularJugada(
                1,
                true,
                2,
                3
        );


        // ==========================================
        // 8. Validar resultado
        // ==========================================

        assertTrue(vistaJugador1.fueNotificada);
        assertTrue(vistaJugador2.fueNotificada);

        assertNotNull(vistaJugador1.estadoRecibido);
        assertNotNull(vistaJugador2.estadoRecibido);

        assertSame(
                juego.getUltimoEstado(),
                modeloJugador1.getEstado()
        );

        assertSame(
                juego.getUltimoEstado(),
                modeloJugador2.getEstado()
        );

        assertEquals(
                1,
                vistaJugador1.estadoRecibido.getJugadorEnTurno()
        );

        assertEquals(
                vistaJugador1.estadoRecibido.getJugadorEnTurno(),
                vistaJugador2.estadoRecibido.getJugadorEnTurno()
        );
    }


    /**
     * Vista falsa utilizada para iniciar la jugada
     * y comprobar que recibió la notificación final.
     */
    private static class VistaFalsa
            implements Observador {

        private FuenteDeJugadas fuenteDeJugadas;

        private boolean fueNotificada;
        private EstadoPartida estadoRecibido;

        public void setFuenteDeJugadas(
                FuenteDeJugadas fuenteDeJugadas) {

            this.fuenteDeJugadas =
                    fuenteDeJugadas;
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

        @Override
        public void alCambiarPartida(
                EstadoPartida estado) {

            fueNotificada = true;
            estadoRecibido = estado;
        }
    }


    /**
     * Sustituto temporal del modelo general del juego.
     *
     * Recibe las jugadas desde los Modelos MVC y entrega
     * el nuevo EstadoPartida a todos los receptores registrados.
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
                receptor.actualizarEstado(
                        ultimoEstado
                );
            }
        }

        public EstadoPartida getUltimoEstado() {
            return ultimoEstado;
        }
    }


    /**
     * Tablero mínimo utilizado únicamente para construir
     * EstadoPartida durante esta prueba.
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