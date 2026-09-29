package eventos;

import modelo.jugador.Jugador;
import modelo.tablero.TableroLectura;

import java.util.List;

/**
 * Representa una fotografía del estado lógico actual de la partida.
 *
 * Este objeto se utiliza para transportar información del juego
 * entre el modelo general, los Modelos MVC y los observadores.
 *
 * IMPORTANTE:
 * EstadoPartida NO es el Modelo MVC ni el dominio completo.
 * Solamente agrupa información del estado actual del juego.
 *
 * No contiene información de presentación como coordenadas de
 * pantalla, componentes gráficos o detalles de Swing.
 *
 * En UML:
 * - Se representará como una clase de datos/estado.
 * - IConsultaModeloPartida devuelve un EstadoPartida.
 * - IReceptorEstadoPartida recibe un EstadoPartida.
 * - Observador utiliza EstadoPartida en su notificación.
 */
public class EstadoPartida {

    private final TableroLectura tablero;
    private final List<Jugador> jugadores;
    private final int jugadorEnTurno;
    private final boolean partidaTerminada;

    /*
     * Integer permite representar la ausencia de ganador con null.
     * Por ejemplo, mientras la partida todavía no termina.
     */
    private final Integer idGanador;

    /**
     * Construye una representación del estado actual de la partida.
     *
     * En UML:
     * + EstadoPartida(tablero: TableroLectura,
     *                 jugadores: List<Jugador>,
     *                 jugadorEnTurno: int,
     *                 partidaTerminada: boolean,
     *                 idGanador: Integer)
     */
    public EstadoPartida(
            TableroLectura tablero,
            List<Jugador> jugadores,
            int jugadorEnTurno,
            boolean partidaTerminada,
            Integer idGanador) {

        this.tablero = tablero;
        this.jugadores = jugadores;
        this.jugadorEnTurno = jugadorEnTurno;
        this.partidaTerminada = partidaTerminada;
        this.idGanador = idGanador;
    }

    public TableroLectura getTablero() {
        return tablero;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public Integer getIdGanador() {
        return idGanador;
    }
}