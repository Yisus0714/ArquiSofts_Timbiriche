/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion;

import Model.EsquemaColores;
import Model.ModeloRegistro;

/**
 *
 * @author luisb
 */
public interface CoordinadorRegistro {
    public void iniciarLobby(ModeloRegistro modeloViejo, String jugadorLocal, EsquemaColores colores);    
}
