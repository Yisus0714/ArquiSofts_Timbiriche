/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacion;

import Control.ControlRegistro;
import Control.ControlFinal;
import Control.EjercerTurnoControl;
import Control.LobbyControl;
import Dominio.EstadoPartida;
import Dominio.IJuego;
import Dominio.ILobby;
import Dominio.Partida;
import Model.EsquemaColores;
import Model.ModeloFinal;
import Model.ModeloJuego;
import Model.ModeloLobby;
import Model.ModeloRegistro;
import Model.ObserverDominio;
import View.VistaFinal;
import View.VistaJuego;
import View.VistaLobby;
import View.VistaRegistro;
import javax.swing.JFrame;

/**
 *
 * @author luisb
 */
public class CoordinadorFase implements CoordinadorJuego, CoordinadorRegistro, CoordinadorLobby, CoordinadorFinal {
    
    private Partida partida;
    private JFrame pantallaActual;
    
    
    public CoordinadorFase(Partida partida) {
        this.partida = partida;
    }
    
    public void iniciarRegistro() {
        ModeloRegistro modelo = new ModeloRegistro(partida, this);
        partida.suscribir(modelo);                      // el modelo escucha al dominio

        ControlRegistro control = new ControlRegistro(modelo);
        VistaRegistro vista = new VistaRegistro(control, modelo);
        modelo.suscribir(vista);                        // la vista escucha al modelo

        cambiarPantalla(vista);

    }
    
    
    
    
    @Override
    public void iniciarFinalPartida(ModeloJuego modeloViejo, String jugadorLocal, EsquemaColores colores) {
        partida.desuscribir(modeloViejo);                 // el juego ya no escucha al dominio

        ModeloFinal modelo = new ModeloFinal(this, colores, jugadorLocal, partida);

        ControlFinal control = new ControlFinal(modelo);
        VistaFinal vista = new VistaFinal(control, modelo);

        cambiarPantalla(vista);
    }

    @Override
    public void iniciarLobby(ModeloRegistro modeloViejo, String jugadorLocal, EsquemaColores colores) {
        partida.desuscribir(modeloViejo);               // el registro ya no escucha al dominio

        ModeloLobby modelo = new ModeloLobby((CoordinadorLobby) this, colores, jugadorLocal, (ILobby)partida);
        partida.suscribir((ObserverDominio) modelo);                      // el lobby escucha al dominio

        LobbyControl control = new LobbyControl(modelo);
        VistaLobby vista = new VistaLobby(control, modelo);
        modelo.suscribir(vista);                        // la vista escucha al modelo

        cambiarPantalla(vista);
        
        if (partida.getEstado() == EstadoPartida.EN_CURSO) {
            iniciarJuego(modelo, jugadorLocal, colores);
        }
        
        
    }
    
    private void cambiarPantalla(JFrame nueva) {
        JFrame anterior = pantallaActual;
        pantallaActual = nueva;
        nueva.setVisible(true);
        if (anterior != null) {
            anterior.dispose();
        }
    }

    @Override
    public void iniciarJuego(ModeloLobby modeloViejo, String jugadorLocal, EsquemaColores colores) {
        partida.desuscribir(modeloViejo);                 // el lobby ya no escucha al dominio

        ModeloJuego modelo = new ModeloJuego(this, colores, jugadorLocal, partida);
        partida.suscribir(modelo);                        // el juego escucha al dominio

        EjercerTurnoControl control = new EjercerTurnoControl(modelo);

        // Dimensión del tablero: 2 jugadores = 10, 3 = 20, 4 = 30
        int dimension = (partida.getJugadores().size() - 1) * 10;

        VistaJuego vista = new VistaJuego(modelo, control, dimension);
        modelo.suscribir(vista);                          // la vista escucha al modelo

        cambiarPantalla(vista);
    }

    @Override
    public void volverARegistro(ModeloLobby modeloViejo) {
        partida.desuscribir(modeloViejo);   // el lobby ya no escucha al dominio
        iniciarRegistro();
    }

    @Override
    public void abandonarPartida(ModeloJuego modeloViejo) {
        partida.desuscribir(modeloViejo);   // el juego ya no escucha al dominio
        cerrarPantalla();
    }

    @Override
    public void salir(ModeloFinal modeloViejo) {
        cerrarPantalla();
    }
    
    private void cerrarPantalla() {
        if (pantallaActual != null) {
            pantallaActual.dispose();
            pantallaActual = null;
        }
    }

    
    
}
