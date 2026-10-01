/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.finall;

import java.awt.Color;

/**
 *
 * @author RAMSES
 */
public class ResultadosJugador implements Comparable<ResultadosJugador>{
    private String nombre;
    private Color color;
    private int puntos;
    private boolean esUsuarioActual; // Para activar el badge "TÚ"

    public ResultadosJugador(String nombre, Color color, int puntos, boolean esUsuarioActual) {
        this.nombre = nombre;
        this.color = color;
        this.puntos = puntos;
        this.esUsuarioActual = esUsuarioActual;
    }

    public ResultadosJugador(String nombre, Color color, int puntos) {
        this(nombre, color, puntos, false);
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean isEsUsuarioActual() {
        return esUsuarioActual;
    }

    // Permite ordenar a los jugadores de mayor a menor puntaje
    @Override
    public int compareTo(ResultadosJugador otro) {
        return Integer.compare(otro.puntos, this.puntos);
    }
}
