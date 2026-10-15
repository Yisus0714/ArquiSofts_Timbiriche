package Model;

import Dominio.IJuego;
import Dominio.Jugador;
import Dominio.ResultadoVotacion;
import Dominio.Voto;
import dto.JugadaDTO;
import dto.JugadorPanelDTO;
import dto.JugadorTableroDTO;
import dto.SeleccionDTO;
import dto.VotacionDTO;
import eventos.Event;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;

/**
 * Modelo de la pantalla de partida de UN jugador.
 *
 * Flujo: Partida -> actualizar(Event) -> este Modelo relee el dominio y reemplaza los DTOs
 * afectados -> avisa a sus Vistas con el mismo evento -> la Vista consulta con los get (que solo
 * devuelven lo ya guardado, no vuelven a tocar el dominio).
 *
 * @author luisb
 */
public class ModeloJuego implements ObserverDominio, IConsultaJugadores, IConsultaTablero, IConsultaVotacion {

    private final Session session;
    private final IJuego dominio;
    private final List<ObserverModelo> observadores;

    // Datos visuales: DTOs ya armados, nunca entidades del dominio.
    private List<JugadorPanelDTO> jugadoresPanel = List.of();
    private List<JugadaDTO> jugadas = List.of();
    private SeleccionDTO seleccion;
    private VotacionDTO votacion = new VotacionDTO(ResultadoVotacion.SIN_VOTACION, 0, 0, false);
    private int dimension;

    /** true desde que arranca la partida hasta que este jugador sale de ella o pasa a resultados. */
    private boolean activo;
    /** true mientras corre MI propio abandono (sus eventos llegan antes de que regrese la llamada). */
    private boolean abandonando;
    private boolean finalSolicitado;

    public ModeloJuego(IJuego dominio, Session session) {
        this.session = session;
        this.dominio = dominio;
        this.observadores = new CopyOnWriteArrayList<>();
    }

    public void suscribir(ObserverModelo obs) {
        if (obs != null && !observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    // ------------------------------------------------------------------
    // Control -> Modelo (comandos). Las excepciones del dominio suben hasta la Vista.
    // ------------------------------------------------------------------

    public void seleccionarPunto(int x, int y) throws Exception {
        dominio.seleccionarPunto(x, y, session.getJugadorLocal());
    }

    /**
     * La salida se reclama antes de avisar: así mis otros modelos (el de registro) ya me ven
     * fuera cuando llega el evento de mi abandono. Si el dominio rechaza, se devuelve.
     */
    public void abandonarJuego() throws Exception {
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

    public void proponerFin() throws Exception {
        dominio.proponerFin(session.getJugadorLocal());
    }

    public void votarFin(Voto voto) throws Exception {
        dominio.votar(voto, session.getJugadorLocal());
    }

    /**
     * Pasa este jugador a la pantalla de resultados. Solo se hace una vez, y solo si sigue en la
     * partida. La Vista de la partida se oculta y ModeloFinal arma los resultados.
     */
    public void verResultados() {
        if (!activo || finalSolicitado) {
            return;
        }
        finalSolicitado = true;
        activo = false;
        notificarVistas(Event.RESULTADOS_SOLICITADOS);
    }

    // ------------------------------------------------------------------
    // Vista -> Modelo (consultas): solo devuelven lo ya guardado
    // ------------------------------------------------------------------

    public boolean isActivo() {
        return activo;
    }

    /** Se deriva de la lista del panel: no se guarda aparte. */
    @Override
    public JugadorPanelDTO getJugadorEnTurno() {
        for (JugadorPanelDTO jugador : jugadoresPanel) {
            if (jugador.isEnTurno()) {
                return jugador;
            }
        }
        return null;
    }

    @Override
    public JugadorTableroDTO getJugadorEnTurnoTablero() {
        JugadorPanelDTO enTurno = getJugadorEnTurno();
        return enTurno == null ? null : new JugadorTableroDTO(enTurno.getColor(), enTurno.isEsTu());
    }

    @Override
    public List<JugadorPanelDTO> getJugadores() {
        return jugadoresPanel;
    }

    @Override
    public List<JugadaDTO> getJugadas() {
        return jugadas;
    }

    /** Se deriva de la lista de jugadas. */
    @Override
    public JugadaDTO getUltimaJugada() {
        return jugadas.isEmpty() ? null : jugadas.get(jugadas.size() - 1);
    }

    @Override
    public SeleccionDTO getOnSelect() {
        return seleccion;
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    @Override
    public VotacionDTO getVotacion() {
        return votacion;
    }

    // ------------------------------------------------------------------
    // Dominio -> Modelo
    //
    // Eventos que le interesan a la pantalla de partida:
    //   JUEGO_INICIADO, PUNTO_SELECCIONADO, PUNTO_DESELECCIONADO, LINEA_TRAZADA,
    //   CAMBIO_TURNO, JUGADOR_ABANDONO, VOTO_REGISTRADO, JUEGO_TERMINADO.
    // No le interesa JUGADOR_REGISTRADO (es del lobby).
    // ------------------------------------------------------------------

    @Override
    public void actualizar(Event evento) {
        switch (evento) {
            case JUEGO_INICIADO -> {
                if (!sigoEnLaPartida()) {
                    return;                      // no entré a esta partida (p. ej. abandoné en el lobby)
                }
                activo = true;
                finalSolicitado = false;
                dimension = dominio.getDimension();
                refrescarPanel();
                refrescarJugadas();
                refrescarSeleccion();
                refrescarVotacion();
            }
            case PUNTO_SELECCIONADO, PUNTO_DESELECCIONADO -> {
                if (!activo) {
                    return;
                }
                refrescarSeleccion();
            }
            case LINEA_TRAZADA -> {
                if (!activo) {
                    return;
                }
                refrescarJugadas();
                refrescarPanel();                // cambian los puntajes
                refrescarSeleccion();            // el dominio limpia la selección al jugar
            }
            case CAMBIO_TURNO -> {
                if (!activo) {
                    return;
                }
                refrescarPanel();
                refrescarSeleccion();
            }
            case JUGADOR_ABANDONO -> {
                if (abandonando) {
                    activo = false;              // el que salió fui yo: aviso y dejo de reaccionar
                } else if (!activo) {
                    return;                      // abandono del lobby: no es de esta pantalla
                } else {
                    refrescarPanel();
                    refrescarJugadas();          // abandonar deshace las líneas de ese jugador
                    refrescarSeleccion();
                    refrescarVotacion();
                }
            }
            case VOTO_REGISTRADO -> {
                if (!activo) {
                    return;                      // voto del lobby: no es de esta pantalla
                }
                refrescarVotacion();
            }
            case JUEGO_TERMINADO -> {
                if (!activo) {
                    return;
                }
                refrescarPanel();
                refrescarVotacion();
                notificarVistas(evento);
                // Si terminó por acuerdo, primero se ve el aviso y el jugador pasa a resultados
                // desde la Vista; en cualquier otro caso se pasa de inmediato.
                if (votacion.getResultado() != ResultadoVotacion.GANO_A_FAVOR) {
                    verResultados();
                }
                return;
            }
            default -> {
                return;                          // no es de esta pantalla
            }
        }
        notificarVistas(evento);
    }

    // ------------------------------------------------------------------
    // Refrescos: cada uno relee el dominio y REEMPLAZA su DTO completo
    // ------------------------------------------------------------------

    private void refrescarPanel() {
        try {
            jugadoresPanel = DTOMapper.toPanelDTO(dominio.getJugadores(), session.getJugadorLocal(),
                    dominio.getJugadorEnTurno(), session.getColores());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refrescarJugadas() {
        try {
            jugadas = DTOMapper.toJugadasDTO(dominio.getJugadas(), session.getColores());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refrescarSeleccion() {
        try {
            Jugador turno = dominio.getJugadorEnTurno();
            seleccion = (turno == null) ? null
                    : DTOMapper.toSeleccionDTO(dominio.getPuntoSeleccionado(),
                            session.getColores().resolver(turno.getColor()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refrescarVotacion() {
        try {
            votacion = DTOMapper.toVotacionDTO(dominio.getResultadoVotacion(), dominio.getVotacion(), buscarLocal());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------------
    // Utilidades internas
    // ------------------------------------------------------------------

    private Jugador buscarLocal() throws Exception {
        String local = session.getJugadorLocal();
        if (local == null) {
            return null;
        }
        for (Jugador jugador : dominio.getJugadores()) {
            if (jugador.getNombre().equalsIgnoreCase(local)) {
                return jugador;
            }
        }
        return null;
    }

    private boolean sigoEnLaPartida() {
        try {
            return buscarLocal() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
