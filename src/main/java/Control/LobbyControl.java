/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Dominio.Voto;
import Model.ModeloLobby;

/**
 *
 * @author luisb
 */
public class LobbyControl {
    
    private ModeloLobby modelo;

    public LobbyControl(ModeloLobby modelo) {
        this.modelo = modelo;
    }
    
    public void solicitarAbandono() throws Exception {
        modelo.abandonar();
    }
    
    public void votar(Voto voto) throws Exception {
        modelo.votar(voto);
    }
    
    public void quitarVoto() throws Exception {
        modelo.quitarVoto();
    }
    
}
