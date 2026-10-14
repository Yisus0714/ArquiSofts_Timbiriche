/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.ModeloFinal;

/**
 *
 * @author luisb
 */
public class ControlFinal {
    
    private ModeloFinal modelo;

    public ControlFinal(ModeloFinal modelo) {
        this.modelo = modelo;
    }
    
    public void salir() {
        modelo.salir();
    }
    
}
