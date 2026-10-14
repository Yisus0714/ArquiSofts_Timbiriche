/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Avatar;
import java.awt.Color;

/**
 * Jugador en la pantalla de resultados. Los jugadores empatados comparten puesto.
 *
 * @author luisb
 */
public class JugadorFinalDTO {
    
    private String nombre;
    private int puntaje;
    private int puesto;
    private boolean ganador;
    private boolean esTu;
    private Color color;
    private Avatar avatar;

    public JugadorFinalDTO(String nombre, int puntaje, int puesto, boolean ganador, boolean esTu, Color color, Avatar avatar) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.puesto = puesto;
        this.ganador = ganador;
        this.esTu = esTu;
        this.color = color;
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getPuesto() {
        return puesto;
    }

    public boolean isGanador() {
        return ganador;
    }

    public boolean isEsTu() {
        return esTu;
    }

    public Color getColor() {
        return color;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    
}
