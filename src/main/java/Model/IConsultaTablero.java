/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model;

import dto.JugadaDTO;
import dto.JugadorTableroDTO;
import dto.SeleccionDTO;
import java.util.List;

/**
 *
 * @author luisb
 */
public interface IConsultaTablero {
    
    public JugadorTableroDTO getJugadorEnTurnoTablero() throws Exception;
    public JugadaDTO getUltimaJugada() throws Exception;
    public List<JugadaDTO> getJugadas() throws Exception;
    public SeleccionDTO getOnSelect() throws Exception;

    /** Lado del tablero en puntos; 0 mientras la partida no ha iniciado. */
    public int getDimension();
    
    
    
}
