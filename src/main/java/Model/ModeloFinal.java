package Model;

import Dominio.IFinal;
import dto.JugadorFinalDTO;
import eventos.Event;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;

/**
 * Modelo de la pantalla de resultados de UN jugador.
 *
 * No escucha al dominio: escucha a ModeloJuego, que es quien sabe si este jugador sigue en la
 * partida y si ya le toca ver los resultados (RESULTADOS_SOLICITADOS). Entonces arma los
 * DTOs de resultados y avisa a su Vista.
 *
 * @author luisb
 */
public class ModeloFinal implements IConsultaFinal, ObserverModelo {

    private final IFinal dominio;
    private final SessionConsulta session;
    private final List<ObserverModelo> observadores;

    // Datos visuales
    private List<JugadorFinalDTO> resultados = List.of();

    public ModeloFinal(IFinal dominio, SessionConsulta session) {
        this.dominio = dominio;
        this.session = session;
        this.observadores = new CopyOnWriteArrayList<>();
    }

    public void suscribir(ObserverModelo observer) {
        if (observer != null && !observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    public void salir() {
        // Salir es cosa de la Vista (cerrar su ventana); el Modelo no guarda nada más.
    }

    @Override
    public List<JugadorFinalDTO> getResultados() {
        return resultados;
    }

    // ------------------------------------------------------------------
    // ModeloJuego -> ModeloFinal
    // ------------------------------------------------------------------

    @Override
    public void actualizar(Event evento) {
        if (evento != Event.RESULTADOS_SOLICITADOS) {
            return;
        }
        try {
            resultados = DTOMapper.toJugadoresFinalDTO(dominio.getClasificacion(), dominio.getGanadores(),
                    session.getJugadorLocal(), session.getColores());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        notificarVistas(evento);
    }

    /** Las Vistas se pintan en el hilo de Swing. */
    private void notificarVistas(Event evento) {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModelo obs : observadores) {
                obs.actualizar(evento);
            }
        });
    }
}
