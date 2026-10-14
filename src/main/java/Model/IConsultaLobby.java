/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model;

import dto.JugadorLobbyDTO;
import java.util.List;

/**
 *
 * @author luisb
 */
public interface IConsultaLobby {
    
    public List<JugadorLobbyDTO> getJugadores() throws Exception;    
    
}
