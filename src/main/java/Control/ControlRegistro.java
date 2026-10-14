/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Dominio.Avatar;
import Model.EsquemaColores;
import Model.IConsultaRegistro;
import Model.ModeloRegistro;
import View.VistaLobby;

/**
 *
 * @author luisb
 */
public class ControlRegistro {
    
    private ModeloRegistro modelo;

    public ControlRegistro(ModeloRegistro modelo) {
        this.modelo = modelo;
    }
    
    
    
    
    public void manejarRegistro(String nombre, Avatar avatar, EsquemaColores colores) throws Exception{
        
        modelo.registrarJugador(nombre, avatar, colores);
    
    }  
}
