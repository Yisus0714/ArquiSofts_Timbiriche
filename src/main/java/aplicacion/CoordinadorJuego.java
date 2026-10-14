/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package aplicacion;

import Model.EsquemaColores;
import Model.ModeloJuego;

/**
 *
 * @author luisb
 */
public interface CoordinadorJuego {
    public void iniciarFinalPartida(ModeloJuego modeloViejo, String jugadorLocal, EsquemaColores colores);

    public void abandonarPartida(ModeloJuego modeloViejo);

}
