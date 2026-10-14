/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.util.List;

/**
 *
 * @author luisb
 */
public class Jugada {
    
    
    private Linea linea;
    private List<Recuadro> recuadrosCompletados;
    private Jugador jugador;

    public Jugada() {
    }

    public Jugada(Linea linea, List<Recuadro> recuadrosCompletados, Jugador jugador) {
        this.linea = linea;
        this.recuadrosCompletados = recuadrosCompletados;
        this.jugador = jugador;
    }

    public Linea getLinea() {
        return linea;
    }
    
    public Jugador getJugador() {
        return jugador;
    }

    public List<Recuadro> getRecuadrosCompletados() {
        return recuadrosCompletados;
    }
    
    
    
    
}
