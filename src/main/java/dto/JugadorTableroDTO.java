/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.awt.Color;

/**
 *
 * @author luisb
 */
public class JugadorTableroDTO {
    
    private Color color;
    private boolean esTu;

    public JugadorTableroDTO(Color color, boolean esTu) {
        this.color = color;
        this.esTu = esTu;
    }

    

    public Color getColor() {
        return color;
    }

    public boolean isEsTu() {
        return esTu;
    }
    
    
    
    
    
    
}
