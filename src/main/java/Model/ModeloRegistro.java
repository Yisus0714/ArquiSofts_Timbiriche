package Model;

import Dominio.Avatar;
import Dominio.IRegistro;
import aplicacion.CoordinadorRegistro;
import dto.JugadorLobbyDTO;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisb
 */
public class ModeloRegistro implements IConsultaRegistro, ObserverDominio{
    
    private CoordinadorRegistro coordinador;
    private IRegistro dominio;
    private List<ObserverModeloRegistro> observadores;
    
    public ModeloRegistro(IRegistro dominio, CoordinadorRegistro coordinador) {
        this.dominio = dominio;
        this.observadores = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.coordinador = coordinador;
        
    }
    
    public void registrarJugador(String nombre, Avatar avatar, EsquemaColores colores) throws Exception{
        dominio.registrarJugador(nombre, avatar);
        coordinador.iniciarLobby(this, nombre.trim(), colores);
        
    }

    public void suscribir(ObserverModeloRegistro o) { if (o != null && !observadores.contains(o)) observadores.add(o); }
    public void desuscribir(ObserverModeloRegistro o) { observadores.remove(o); }

    @Override
    public List<Avatar> getAvataresDisponibles() {
        return dominio.getAvataresDisponibles();
    }

    @Override
    public void notificarRegistro() {
        for(ObserverModeloRegistro obs: observadores) {
            obs.actualizarRegistro();
        }
    }

    @Override
    public void notificarComienzo() { }

    @Override
    public void notificarSeleccion() { }

    @Override
    public void notificarDeseleccion() { }

    @Override
    public void notificarJugada() { }

    @Override
    public void notificarCambioTurno() { }

    @Override
    public void notificarAbandono() {
        for (ObserverModeloRegistro obs : observadores) { obs.actualizarAbandono(); }
    }

    @Override
    public void notificarFin() { }

    @Override
    public void notificarVoto() { }

}
