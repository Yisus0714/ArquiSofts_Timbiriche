/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.timbiriche_arquisofts;

import Dominio.Avatar;
import Dominio.Partida;
import View.VistaLobby;
import aplicacion.CoordinadorFase;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author luisb
 */
public class Timbiriche_Arquisofts {

    public static void main(String[] args) {
    
        Partida partida = new Partida();
        
        CoordinadorFase coordinador = new CoordinadorFase(partida);
        coordinador.iniciarRegistro();
        
        
        CoordinadorFase coordinador2 = new CoordinadorFase(partida);
        coordinador2.iniciarRegistro();
    }
}

