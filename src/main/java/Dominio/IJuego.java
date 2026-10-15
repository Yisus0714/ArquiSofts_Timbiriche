/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dominio;

import java.util.List;

/**
 *
 * @author luisb
 */
public interface IJuego {
    
    public void seleccionarPunto(int x, int y, String nombre) throws Exception;
    public void abandonar(String nombre) throws Exception;
    public void votar(Voto voto, String nombre) throws Exception;
    public void proponerFin(String nombre) throws Exception;
    
    public List<Jugada> getJugadas()throws Exception;;
    public Jugador getJugadorEnTurno()throws Exception;;
    public List<Jugador> getJugadores()throws Exception;;
    public PuntoSeleccionado getPuntoSeleccionado() throws Exception;
    public Jugada getUltimaJugada() throws Exception;
    public Votacion getVotacion();
    public ResultadoVotacion getResultadoVotacion();
    public int getDimension();
    
}
