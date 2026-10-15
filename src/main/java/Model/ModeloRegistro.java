package Model;

import Dominio.Avatar;
import Dominio.IRegistro;
import eventos.Event;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;

/**
 * Modelo de la pantalla de registro de UN jugador.
 *
 * Guarda la lista de avatares libres y es el único que da de alta al jugador en la Session.
 * Eventos que le interesan: JUGADOR_REGISTRADO y JUGADOR_ABANDONO (cambian los avatares libres
 * y la Vista decide si se muestra según estoyRegistrado()).
 *
 * @author luisb
 */
public class ModeloRegistro implements IConsultaRegistro, ObserverDominio {

    private final IRegistro dominio;
    private final Session session;
    private final List<ObserverModelo> observadores;

    // Datos visuales
    private List<Avatar> avataresDisponibles = List.of();

    public ModeloRegistro(IRegistro dominio, Session session) {
        this.dominio = dominio;
        this.session = session;
        this.observadores = new CopyOnWriteArrayList<>();
        refrescarAvatares();
    }

    public void suscribir(ObserverModelo o) {
        if (o != null && !observadores.contains(o)) {
            observadores.add(o);
        }
    }

    public void desuscribir(ObserverModelo o) {
        observadores.remove(o);
    }

    /**
     * Los eventos de este mismo registro llegan ANTES de que regrese la llamada al dominio, y los
     * demás modelos ya necesitan saber quién soy para procesarlos. Por eso la identidad se
     * reclama antes y se devuelve si el dominio rechaza el registro (la excepción sigue su camino
     * hasta la Vista).
     */
    public void registrarJugador(String nombre, Avatar avatar, EsquemaColores colores) throws Exception {
        String nombreAnterior = session.getJugadorLocal();
        EsquemaColores coloresAnteriores = session.getColores();

        session.setJugadorLocal(nombre == null ? null : nombre.trim());
        session.setColores(colores);
        try {
            dominio.registrarJugador(nombre, avatar);
        } catch (Exception e) {
            session.setJugadorLocal(nombreAnterior);
            session.setColores(coloresAnteriores);
            throw e;
        }
    }

    @Override
    public List<Avatar> getAvataresDisponibles() {
        return avataresDisponibles;
    }

    @Override
    public boolean estoyRegistrado() {
        return session.getJugadorLocal() != null;
    }

    // ------------------------------------------------------------------
    // Dominio -> Modelo
    // ------------------------------------------------------------------

    @Override
    public void actualizar(Event evento) {
        switch (evento) {
            case JUGADOR_REGISTRADO, JUGADOR_ABANDONO -> {
                refrescarAvatares();
                notificarVistas(evento);
            }
            default -> { /* no es de esta pantalla */ }
        }
    }

    private void refrescarAvatares() {
        avataresDisponibles = dominio.getAvataresDisponibles();
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
