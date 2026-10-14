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
public interface ILobby {   
    public void votar(Voto voto, String nombre) throws Exception;
    public void quitarVoto(String nombre) throws Exception;
    public void abandonar(String nombre) throws Exception;
    public Votacion getVotacion();
    public List<Jugador> getJugadores()throws Exception;;

    
}
