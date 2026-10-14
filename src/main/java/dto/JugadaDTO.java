/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.awt.Color;
import java.util.List;

/**
 *
 * @author luisb
 */
public class JugadaDTO {
    
    private LineaDTO linea;
    private List<RecuadroDTO> recuadrosCompletados;
    private Color color;
    private char inicialJugador;

    public JugadaDTO(LineaDTO linea, List<RecuadroDTO> recuadrosCompletados, Color color, char inicialJugador) {
        this.linea = linea;
        this.recuadrosCompletados = recuadrosCompletados;
        this.color = color;
        this.inicialJugador = inicialJugador;
    }

    public LineaDTO getLinea() {
        return linea;
    }

    public List<RecuadroDTO> getRecuadrosCompletados() {
        return recuadrosCompletados;
    }

    public Color getColor() {
        return color;
    }

    public char getInicialJugador() {
        return inicialJugador;
    }
}
