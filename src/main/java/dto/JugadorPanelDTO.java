/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.Avatar;
import java.awt.Color;

/**
 *
 * @author luisb
 */
public class JugadorPanelDTO {
    
    private String nombre;
    private int puntaje;
    private boolean esTu;
    private boolean enTurno;
    private Color color;
    private Avatar avatar;

    public JugadorPanelDTO(String nombre, int puntaje, boolean esTu, boolean enTurno, Color color, Avatar avatar) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.esTu = esTu;
        this.enTurno = enTurno;
        this.color = color;
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean isEsTu() {
        return esTu;
    }

    public boolean isEnTurno() {
        return enTurno;
    }
    
    public Color getColor() {
        return color;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    
}
