package dto;

import java.awt.Color;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisb
 */
public class SeleccionDTO {
    
    private PuntoDTO puntoSelec;
    private List<PuntoDTO> puntosDisponibles;
    private Color color;

    public SeleccionDTO(PuntoDTO puntoSelec, List<PuntoDTO> puntosDisponibles, Color color) {
        this.puntoSelec = puntoSelec;
        this.puntosDisponibles = puntosDisponibles;
        this.color = color;
    }

    public PuntoDTO getPuntoSelec() {
        return puntoSelec;
    }

    public List<PuntoDTO> getPuntosDisponibles() {
        return puntosDisponibles;
    }

    public Color getColor() {
        return color;
    }
}
