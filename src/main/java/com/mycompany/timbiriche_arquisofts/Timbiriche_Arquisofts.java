package com.mycompany.timbiriche_arquisofts;

import Dominio.Partida;
import ensamblador.Ensamblador;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada: una sola Partida compartida y un conjunto MVC por jugador.
 * Uso: Timbiriche_Arquisofts [numeroDeJugadores]   (por omisión 3)
 *
 * @author luisb
 * 
 */
public class Timbiriche_Arquisofts {

    public static void main(String[] args) {
        int jugadores = 2;
        if (args.length > 0) {
            try {
                jugadores = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Número de jugadores inválido, se usarán 2.");
            }
        }
        final int n = jugadores;
        SwingUtilities.invokeLater(() -> {
            Partida partida = new Partida();
            Ensamblador ensamblador = new Ensamblador(partida);
            for (int i = 0; i < n; i++) {
                ensamblador.crearJugador();
            }
        });
    }
}
