package Model;

import Dominio.ColorJugador;
import Dominio.IJuego;
import Dominio.Jugada;
import Dominio.Jugador;
import Dominio.PuntoSeleccionado;
import Dominio.ResultadoVotacion;
import Dominio.Voto;
import aplicacion.CoordinadorJuego;
import dto.JugadaDTO;
import dto.JugadorPanelDTO;
import dto.JugadorTableroDTO;
import dto.SeleccionDTO;
import dto.VotacionDTO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author luisb
 */
public class ModeloJuego implements ObserverDominio, IConsultaJugadores, IConsultaTablero, IConsultaVotacion {
    
    private CoordinadorJuego coordinador;
    private EsquemaColores colores;
    private String jugadorLocal;
    private IJuego dominio;
    private List<ObserverModeloJuego> observadores;
    private boolean finalIniciado;

    public ModeloJuego(CoordinadorJuego coordinador, EsquemaColores colores, String jugadorLocal, IJuego dominio) {
        this.coordinador = coordinador;
        this.colores = colores;
        this.jugadorLocal = jugadorLocal;
        this.dominio = dominio;
        this.observadores = new CopyOnWriteArrayList<>();
    }
    
    public void suscribir(ObserverModeloJuego obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }
    
    public void seleccionarPunto(int x, int y) throws Exception {
        dominio.seleccionarPunto(x, y, jugadorLocal);
    }
    
    public void abandonarJuego() throws Exception {
        dominio.abandonar(jugadorLocal);
        coordinador.abandonarPartida(this);
    }
    
    public void proponerFin() throws Exception {
        dominio.proponerFin(jugadorLocal);
    }
    
    public void votarFin(Voto voto) throws Exception {
        dominio.votar(voto, jugadorLocal);
    }
    
    /**
     * Pasa a la pantalla de resultados. Solo se hace una vez por jugador.
     */
    public void verResultados() {
        if (!finalIniciado) {
            finalIniciado = true;
            coordinador.iniciarFinalPartida(this, jugadorLocal, colores);
        }
    }
    
    private boolean sigoEnLaPartida() {
        try {
            for (Jugador jugador : dominio.getJugadores()) {
                if (jugador.getNombre().equalsIgnoreCase(jugadorLocal)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public JugadorPanelDTO getJugadorEnTurno() throws Exception {
        Jugador jugador = dominio.getJugadorEnTurno();
        boolean esTu = jugador.getNombre().equalsIgnoreCase(jugadorLocal);
        return DTOMapper.toPanelDTO(jugador, esTu, true, colores);
    }
    
    @Override
    public JugadorTableroDTO getJugadorEnTurnoTablero() throws Exception {
        Jugador jugador = dominio.getJugadorEnTurno();
        boolean esTu = jugador.getNombre().equalsIgnoreCase(jugadorLocal);
        return new JugadorTableroDTO(colores.resolver(jugador.getColor()), esTu);
    }

    @Override
    public List<JugadorPanelDTO> getJugadores() throws Exception {
        List<Jugador> jugadores = dominio.getJugadores();
        Jugador jugadorEnTurno = dominio.getJugadorEnTurno();
        return DTOMapper.toPanelDTO(jugadores, jugadorLocal, jugadorEnTurno, colores);
    }

    @Override
    public JugadaDTO getUltimaJugada() throws Exception {
        Jugada jugada = dominio.getUltimaJugada();
        return DTOMapper.toJugadaDTO(jugada, colores);
    }

    @Override
    public List<JugadaDTO> getJugadas() throws Exception {
        List<Jugada> jugadas = dominio.getJugadas();
        return DTOMapper.toJugadasDTO(jugadas, colores);
    }

    @Override
    public SeleccionDTO getOnSelect() throws Exception {
        ColorJugador color = dominio.getJugadorEnTurno().getColor();
        PuntoSeleccionado seleccion = dominio.getPuntoSeleccionado();
        return DTOMapper.toSeleccionDTO(seleccion, colores.resolver(color));
    }
    
    @Override
    public VotacionDTO getVotacion() throws Exception {
        Jugador local = null;

        for (Jugador jugador : dominio.getJugadores()) {
            if (jugador.getNombre().equalsIgnoreCase(jugadorLocal)) {
                local = jugador;
            }
        }

        return DTOMapper.toVotacionDTO(dominio.getResultadoVotacion(), dominio.getVotacion(), local);
    }
    
    // ------------------------------------------------------------------
    // IMPLEMENTACIÓN DE NOTIFICACIONES (ObserverDominio -> ObserverModeloJuego)
    // ------------------------------------------------------------------

    @Override
    public void notificarSeleccion() {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModeloJuego obs : observadores) {
                obs.notificarSeleccion();
            }
        });
    }

    @Override
    public void notificarDeseleccion() {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModeloJuego obs : observadores) {
                obs.notificarDeseleccion();
            }
        });
    }

    @Override
    public void notificarJugada() {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModeloJuego obs : observadores) {
                obs.notificarJugada();
            }
        });
    }

    @Override
    public void notificarCambioTurno() {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModeloJuego obs : observadores) {
                obs.notificarCambioTurno();
            }
        });
    }

    @Override
    public void notificarAbandono() {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModeloJuego obs : observadores) {
                obs.notificarAbandono();
            }
        });
    }

    @Override
    public void notificarRegistro() {
        // No aplica directamente en esta pantalla, se deja vacío
    }

    @Override
    public void notificarComienzo() {
        // Opcional
    }

    @Override
    public void notificarFin() {
        // Si terminó por votación, el jugador ve primero la pantalla del acuerdo.
        // Quien ya abandonó no tiene resultados que ver.
        if (dominio.getResultadoVotacion() == ResultadoVotacion.GANO_A_FAVOR || !sigoEnLaPartida()) {
            return;
        }
        SwingUtilities.invokeLater(this::verResultados);
    }

    @Override
    public void notificarVoto() {
        SwingUtilities.invokeLater(() -> {
            for (ObserverModeloJuego obs : observadores) {
                obs.notificarVoto();
            }
        });
    }
}