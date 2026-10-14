/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author luisb
 */
public class LineaDTO {
    
    private PuntoDTO inicio;
    private PuntoDTO fin;

    public LineaDTO(PuntoDTO inicio, PuntoDTO fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public PuntoDTO getInicio() {
        return inicio;
    }

    public PuntoDTO getFin() {
        return fin;
    }
    
    
    
}
