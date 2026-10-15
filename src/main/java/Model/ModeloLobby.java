package Model;

import Dominio.ILobby;
import Dominio.Voto;
import dto.JugadorLobbyDTO;
import eventos.Event;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;

/**
 * Modelo de la sala de espera de UN jugador.
 *
 * Guarda la lista de jugadores del lobby ya convertida a DTO. Eventos que le interesan:
 * JUGADOR_REGISTRADO, JUGADOR_ABANDONO, VOTO_REGISTRADO y JUEGO_INICIADO. Solo reenvía a su
 * Vista los que le tocan a este jugador (los de quien aún no se registra o ya salió se ignoran).
 *
 * @author luisb
 */
public class ModeloLobby implements IConsultaLobby, ObserverDominio {

    private final ILobby dominio;
    private final Session session;
    private final List<ObserverModelo> observadores;

    // Datos visuales
    private List<JugadorLobbyDTO> jugadores = List.of();

    /** true mientras este jugador está en la sala de espera. */
    private boolean activo;
    /** true mientras corre MI propio abandono (sus eventos llegan antes de que regrese la llamada). */
    private boolean abandonando;

    public ModeloLobby(ILobby dominio, Session session) {
        this.dominio = dominio;
        this.session = session;
        this.observadores = new CopyOnWriteArrayList<>();
    }

    public void suscribir(ObserverModelo observer) {
        if (observer != null && !observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    // ------------------------------------------------------------------
    // Control -> Modelo (comandos). Las excepciones del dominio suben hasta la Vista.
    // ------------------------------------------------------------------

    public void votar(Voto voto) throws Exception {
        dominio.votar(voto, session.getJugadorLocal());
    }

    public void quitarVoto() throws Exception {
        dominio.quitarVoto(session.getJugadorLocal());
    }

    /**
     * La salida se reclama antes de avisar: así mis otros modelos (el de registro) ya me ven
     * fuera cuando llega el evento de mi abandono. Si el dominio rechaza, se devuelve.
     */
    public void abandonar() throws Exception {
        String nombre = session.getJugadorLocal();
        session.setJugadorLocal(null);
        abandonando = true;
        try {
            dominio.abandonar(nombre);
        } catch (Exception e) {
            session.setJugadorLocal(nombre);
            throw e;
        } finally {
            abandonando = false;
        }
    }

    // ------------------------------------------------------------------
    // Vista -> Modelo (consultas): solo devuelven lo ya guardado
    // ------------------------------------------------------------------

    @Override
    public List<JugadorLobbyDTO> getJugadores() {
        return jugadores;
    }

    @Override
    public boolean isActivo() {
        return activo;
    }

    // ------------------------------------------------------------------
    // Dominio -> Modelo
    // ------------------------------------------------------------------

    @Override
    public void actualizar(Event evento) {
        switch (evento) {
            case JUGADOR_REGISTRADO -> {
                if (session.getJugadorLocal() == null) {
                    return;                      // yo aún no me registro
                }
                activo = true;
                refrescarJugadores();
            }
            case JUGADOR_ABANDONO -> {
                if (abandonando) {
                    activo = false;              // el que salió fui yo: aviso y dejo de reaccionar
                } else if (!activo) {
                    return;
                } else {
                    refrescarJugadores();
                }
            }
            case VOTO_REGISTRADO -> {
                if (!activo) {
                    return;
                }
                refrescarJugadores();
            }
            case JUEGO_INICIADO -> {
                if (!activo) {
                    return;
                }
                activo = false;                  // el lobby termina; sigue la partida
            }
            default -> {
                return;                          // no es de esta pantalla
            }
        }
        notificarVistas(evento);
    }

    private void refrescarJugadores() {
        try {
            jugadores = DTOMapper.toJugadoresLobbyDTO(dominio.getJugadores(), session.getJugadorLocal(),
                    session.getColores(), dominio.getVotacion());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
