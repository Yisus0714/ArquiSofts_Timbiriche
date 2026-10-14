/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion;

import Model.EsquemaColores;
import Model.ModeloLobby;

/**
 *
 * @author luisb
 */
public interface CoordinadorLobby {
    public void iniciarJuego(ModeloLobby modeloViejo, String jugadorLocal, EsquemaColores colores);
    public void volverARegistro(ModeloLobby modeloViejo);
}
