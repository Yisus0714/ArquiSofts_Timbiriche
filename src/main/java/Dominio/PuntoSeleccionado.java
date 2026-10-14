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
public class PuntoSeleccionado {
    private Punto putnoJugado;
    private List<Punto> jugadasDsiponibles;

    public PuntoSeleccionado() {
    }

    public PuntoSeleccionado(Punto putnoJugado, List<Punto> jugadasDsiponibles) {
        this.putnoJugado = putnoJugado;
        this.jugadasDsiponibles = jugadasDsiponibles;
    }

    public Punto getPutnoJugado() {
        return putnoJugado;
    }

    public List<Punto> getJugadasDsiponibles() {
        return jugadasDsiponibles;
    }
    
    
}
