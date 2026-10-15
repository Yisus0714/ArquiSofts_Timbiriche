package Model;

import dto.JugadorLobbyDTO;
import java.util.List;

/**
 * Lo que la Vista del lobby puede consultar al Modelo.
 */
public interface IConsultaLobby {

    public List<JugadorLobbyDTO> getJugadores() throws Exception;
    public boolean isActivo();
    
}
