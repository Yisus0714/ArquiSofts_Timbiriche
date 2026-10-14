/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model;

/**
 *
 * @author luisb
 */
public interface ObserverModeloJuego {
    void notificarSeleccion();
    void notificarDeseleccion();
    void notificarJugada();
    void notificarCambioTurno();
    void notificarAbandono();
    void notificarVoto();
}