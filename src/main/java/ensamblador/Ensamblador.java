package ensamblador;

import Control.ControlFinal;
import Control.ControlRegistro;
import Control.ControlEjercerTurno;
import Control.ControlLobby;
import Dominio.Partida;
import Model.ModeloFinal;
import Model.ModeloJuego;
import Model.ModeloLobby;
import Model.ModeloRegistro;
import Model.Session;
import View.VistaFinal;
import View.VistaJuego;
import View.VistaLobby;
import View.VistaRegistro;

/**
 * Raíz de composición: crea y conecta todo desde el principio (Session, Modelos, Controles y
 * Vistas de cada jugador). Después no interviene: las Vistas se muestran y se ocultan solas
 * según los eventos que reciben.
 *
 * Cada modelo recibe del dominio SOLO la interfaz de su rol (IRegistro, ILobby, IJuego, IFinal).
 *
 * @author luisb
 */
public class Ensamblador {

    private final Partida partida;

    public Ensamblador(Partida partida) {
        this.partida = partida;
    }

    /** Arma el conjunto MVC completo de UN jugador y deja visible solo su pantalla de registro. */
    public VistaRegistro crearJugador() {
        Session session = new Session(null, null);

        // Modelos
        ModeloRegistro modeloRegistro = new ModeloRegistro(partida, session);
        ModeloLobby modeloLobby = new ModeloLobby(partida, session);
        ModeloJuego modeloJuego = new ModeloJuego(partida, session);
        ModeloFinal modeloFinal = new ModeloFinal(partida, session);

        // Controles
        ControlRegistro controlRegistro = new ControlRegistro(modeloRegistro);
        ControlLobby controlLobby = new ControlLobby(modeloLobby);
        ControlEjercerTurno controlJuego = new ControlEjercerTurno(modeloJuego);
        ControlFinal controlFinal = new ControlFinal(modeloFinal);

        // Vistas
        VistaRegistro vistaRegistro = new VistaRegistro(controlRegistro, modeloRegistro);
        VistaLobby vistaLobby = new VistaLobby(controlLobby, modeloLobby);
        VistaJuego vistaJuego = new VistaJuego(modeloJuego, controlJuego);
        VistaFinal vistaFinal = new VistaFinal(controlFinal, modeloFinal);

        // Dominio -> Modelos
        partida.suscribir(modeloRegistro);
        partida.suscribir(modeloLobby);
        partida.suscribir(modeloJuego);

        // Modelos -> Vistas (y ModeloJuego -> ModeloFinal)
        modeloRegistro.suscribir(vistaRegistro);
        modeloLobby.suscribir(vistaLobby);
        modeloJuego.suscribir(vistaJuego);
        modeloJuego.suscribir(modeloFinal);
        modeloFinal.suscribir(vistaFinal);

        vistaRegistro.setVisible(true);
        return vistaRegistro;
    }
}
