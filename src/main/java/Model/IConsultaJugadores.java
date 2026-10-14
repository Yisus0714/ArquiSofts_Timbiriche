/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model;

import dto.JugadorPanelDTO;
import java.util.List;

/**
 *
 * @author luisb
 */
public interface IConsultaJugadores {
    
    public JugadorPanelDTO getJugadorEnTurno()throws Exception;;
    public List<JugadorPanelDTO> getJugadores()throws Exception;;
    
}
