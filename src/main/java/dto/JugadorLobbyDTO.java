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
public class JugadorLobbyDTO {
   
    private String nombre;
    private Color color;
    private boolean isReady;
    private boolean esTu;
    private Avatar avatar;

    public JugadorLobbyDTO(String nombre, Color color, boolean isReady, boolean esTu, Avatar avatar) {
        this.nombre = nombre;
        this.color = color;
        this.isReady = isReady;
        this.esTu = esTu;
        this.avatar = avatar;
    }
    
    public Avatar getAvatar() {
        return avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public boolean isIsReady() {
        return isReady;
    }

    public boolean isEsTu() {
        return esTu;
    }
        
}
