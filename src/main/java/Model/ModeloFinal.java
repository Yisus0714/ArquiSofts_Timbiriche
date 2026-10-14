/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Dominio.IFinal;
import aplicacion.CoordinadorFinal;
import dto.JugadorFinalDTO;
import java.util.List;

/**
 * Modelo de la pantalla de resultados. La partida ya terminó, por lo que no escucha al dominio.
 *
 * @author luisb
 */
public class ModeloFinal implements IConsultaFinal {
    
    private CoordinadorFinal coordinador;
    private EsquemaColores colores;
    private String jugadorLocal;
    private IFinal dominio;

    public ModeloFinal(CoordinadorFinal coordinador, EsquemaColores colores, String jugadorLocal, IFinal dominio) {
        this.coordinador = coordinador;
        this.colores = colores;
        this.jugadorLocal = jugadorLocal;
        this.dominio = dominio;
    }
    
    public void salir() {
        coordinador.salir(this);
    }

    @Override
    public List<JugadorFinalDTO> getResultados() throws Exception {
        return DTOMapper.toJugadoresFinalDTO(dominio.getClasificacion(), dominio.getGanadores(), jugadorLocal, colores);
    }
    
}
