/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Dominio.Voto;
import Model.ModeloJuego;

/**
 *
 * @author luisb
 */
public class ControlEjercerTurno {
    
    private ModeloJuego modelo;

    public ControlEjercerTurno(ModeloJuego modelo) {
        this.modelo = modelo;
    }
    
    public void seleccionarPunto(int x, int y) throws Exception {
        modelo.seleccionarPunto(x, y);
    }
    
    public void solicitarVotacion() throws Exception {
        modelo.proponerFin();
    }
    
    public void votarFin(Voto voto) throws Exception {
        modelo.votarFin(voto);
    }
    
    public void verResultados() {
        modelo.verResultados();
    }
    
    public void solicitarAbandono() throws Exception {
        modelo.abandonarJuego();
    }
    
}
