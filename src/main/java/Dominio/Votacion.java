/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luisb
 */
public class Votacion {
    
    private Map<Jugador, Voto> votos;

    public Votacion() {
        this.votos = new HashMap<>();
    }
    
    public void Votar(Jugador jugador, Voto voto) {
        votos.put(jugador, voto);
    }
    
    public Voto obtenerVoto(Jugador jugador) {
        return votos.get(jugador);
    }
    
        /**
     * Determina si los votos a favor alcanzan la mayoría absoluta.
     * Ignora los votos en contra para determinar el resultado.
     */
    public ResultadoVotacion analizarVotosAFavor(int cantidadJugadores) {
        if (cantidadJugadores <= 0) {
            return ResultadoVotacion.SIN_JUGADORES;
        }

        int aFavor = 0;

        for (Voto voto : votos.values()) {
            if (voto == Voto.FAVOR) {
                aFavor++;
            }
        }

        int votosParaGanar = cantidadJugadores;

        if (aFavor >= votosParaGanar) {
            return ResultadoVotacion.GANO_A_FAVOR;
        }

        return ResultadoVotacion.EN_PROCESO;
    }

    /**
     * Determina si los votos a favor o en contra alcanzan
     * la mayoría absoluta, sin esperar a que voten todos.
     */
    public ResultadoVotacion analizarVotosAFavorYEnContra(
            int cantidadJugadores) {

        if (cantidadJugadores <= 0) {
            return ResultadoVotacion.SIN_JUGADORES;
        }

        int aFavor = 0;
        int enContra = 0;

        for (Voto voto : votos.values()) {
            if (voto == Voto.FAVOR) {
                aFavor++;
            } else if (voto == Voto.EN_CONTRA) {
                enContra++;
            }
        }

        int votosParaGanar = (cantidadJugadores / 2) + 1;

        if (aFavor >= votosParaGanar) {
            return ResultadoVotacion.GANO_A_FAVOR;
        }

        if (enContra >= votosParaGanar) {
            return ResultadoVotacion.GANO_EN_CONTRA;
        }

        return ResultadoVotacion.EN_PROCESO;
    }
    
    /**
     * Cuenta cuántos jugadores han emitido el voto indicado.
     */
    public int contarVotos(Voto voto) {
        int total = 0;

        for (Voto v : votos.values()) {
            if (v == voto) {
                total++;
            }
        }

        return total;
    }

    /**
     * Votación para terminar la partida: se necesita la unanimidad.
     * Un solo voto en contra la desestima sin esperar a que voten todos.
     */
    public ResultadoVotacion analizarVotosParaTerminar(int cantidadJugadores) {
        if (cantidadJugadores <= 0) {
            return ResultadoVotacion.SIN_JUGADORES;
        }

        if (contarVotos(Voto.EN_CONTRA) > 0) {
            return ResultadoVotacion.GANO_EN_CONTRA;
        }

        if (contarVotos(Voto.FAVOR) >= cantidadJugadores) {
            return ResultadoVotacion.GANO_A_FAVOR;
        }

        return ResultadoVotacion.EN_PROCESO;
    }
    
    public void limpiar() {
        votos.clear();
    }
    
    public void quitarVoto(Jugador jugador) {
        votos.remove(jugador);
    }

    
    
    
    
}
