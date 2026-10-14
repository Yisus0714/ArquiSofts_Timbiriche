/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model;

/**
 *
 * @author luisb
 */
public interface ObserverDominio {
    
    public void notificarRegistro(); 
    public void notificarComienzo();
    public void notificarSeleccion();
    public void notificarDeseleccion();
    public void notificarJugada();
    public void notificarCambioTurno();
    public void notificarAbandono();
    public void notificarFin();
    public void notificarVoto();
        
}
