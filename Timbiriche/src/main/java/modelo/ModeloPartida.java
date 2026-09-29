package modelo;

import eventos.EstadoPartida;
import eventos.Observador;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo del MVC correspondiente a un jugador.
 *
 * IMPORTANTE:
 * Esta clase NO representa el dominio completo del juego.
 * Su responsabilidad es mantener el estado que necesita la Vista
 * y servir como punto de comunicación entre el MVC local
 * y el modelo general del juego.
 *
 * En UML:
 * - Se representará como una clase del componente MVC.
 * - Implementa IOperacionesPartida.
 * - Implementa IConsultaModeloPartida.
 * - Implementa IReceptorEstadoPartida.
 * - Depende de IOperacionesJuego para solicitar operaciones
 *   al modelo general del juego.
 */
public class ModeloPartida
        implements IOperacionesPartida, IConsultaModeloPartida, IReceptorEstadoPartida {

    private final List<Observador> observadores = new ArrayList<>();
    private EstadoPartida estado;

    // Referencia al contrato del modelo general del juego.
    // ModeloPartida no necesita conocer una implementación concreta
    // como Partida; solamente sabe qué operaciones puede solicitar.
    private IOperacionesJuego juego;

    /**
     * Recibe la dependencia hacia el modelo general del juego.
     *
     * Esta asociación será realizada por Ensamblador.
     *
     * En UML:
     * + setJuego(juego: IOperacionesJuego): void
     */
    public void setJuego(IOperacionesJuego juego) {
        this.juego = juego;
    }

    /**
     * Registra una Vista interesada en los cambios de este Modelo MVC.
     *
     * En UML:
     * + agregarObservador(observador: Observador): void
     */
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    /**
     * Recibe un nuevo estado proveniente del modelo general del juego.
     *
     * ModeloPartida no calcula reglas aquí; solamente guarda el estado
     * recibido y avisa a su Vista mediante Observer.
     *
     * En UML:
     * + actualizarEstado(estado: EstadoPartida): void
     */
    @Override
    public void actualizarEstado(EstadoPartida estado) {
        this.estado = estado;
        notificarObservadores();
    }

    /**
     * Notifica a los observadores registrados que el estado cambió.
     *
     * Normalmente el observador será la Vista correspondiente
     * a este Modelo MVC.
     */
    private void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.alCambiarPartida(estado);
        }
    }

    /**
     * Recibe desde el Controlador la intención de realizar una jugada.
     *
     * ModeloPartida no decide si la jugada es válida ni calcula
     * puntos o turnos. Esas responsabilidades pertenecen al dominio.
     * Aquí solamente se delega la operación al modelo general.
     */
    @Override
    public void jugar(int jugadorId,
                      boolean horizontal,
                      int fila,
                      int col) {

        if (juego != null) {
            juego.jugar(
                    jugadorId,
                    horizontal,
                    fila,
                    col
            );
        }
    }

    /**
     * Devuelve el estado que actualmente conoce este Modelo MVC.
     *
     * La Vista consulta esta información mediante
     * IConsultaModeloPartida.
     */
    @Override
    public EstadoPartida getEstado() {
        return estado;
    }
}