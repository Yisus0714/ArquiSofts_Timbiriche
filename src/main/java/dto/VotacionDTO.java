/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import Dominio.ResultadoVotacion;

/**
 * Estado de la votación para terminar la partida, tal como lo ve el jugador local.
 * Los votos son anónimos: solo se informa cuántos hay de cada tipo.
 *
 * @author luisb
 */
public class VotacionDTO {
    
    private ResultadoVotacion resultado;
    private int aFavor;
    private int enContra;
    private boolean yaVoto;

    public VotacionDTO(ResultadoVotacion resultado, int aFavor, int enContra, boolean yaVoto) {
        this.resultado = resultado;
        this.aFavor = aFavor;
        this.enContra = enContra;
        this.yaVoto = yaVoto;
    }

    public ResultadoVotacion getResultado() {
        return resultado;
    }

    public int getAFavor() {
        return aFavor;
    }

    public int getEnContra() {
        return enContra;
    }

    public boolean isYaVoto() {
        return yaVoto;
    }
    
}
