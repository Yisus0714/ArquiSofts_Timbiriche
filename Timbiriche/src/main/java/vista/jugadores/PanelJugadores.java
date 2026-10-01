/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.jugadores;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public class PanelJugadores extends JPanel {

    // Lista de las tarjetas de jugadores 
    private final List<TarjetaJugador> listaTarjetas;
    private int indiceJugadorActual;

    /**
     * Constructor por defecto para el panel.
     */
    public PanelJugadores() {
        this.listaTarjetas = new ArrayList<>();
        this.indiceJugadorActual = -1;

        setOpaque(false); 
        // 1 fila, número dinamico de columnas (0), con 16px de espacio horizontal y vertical
        setLayout(new GridLayout(1, 0, 16, 16));
    }

    /**
     * Inicializa o reemplaza las tarjetas del panel a partir de la lista.
     * 
     * @param tarjetas Lista de entre 2 y 4 tarjetas de jugador.
     */
    public void setJugadores(List<TarjetaJugador> tarjetas) {
        if (tarjetas == null || tarjetas.size() < 2 || tarjetas.size() > 4) {
            return;
        }

        // Limpiar estado previo de la lista
        removeAll();
        listaTarjetas.clear();

        // Mantener siempre 1 fila horizontal
        setLayout(new GridLayout(1, 0, 16, 16));

        // Agregar tarjetas a la fila
        for (TarjetaJugador tarjeta : tarjetas) {
            listaTarjetas.add(tarjeta);
            add(tarjeta);
        }

        // Activar el turno del primer jugador por defecto
        setTurnoJugador(0);

        revalidate();
        repaint();
    }

    // =========================================================================
    // CONTROL DE TURNOS Y ESTADO
    // =========================================================================

    /**
     * Activa el turno del jugador especificado por su indice (0 a N-1) y
     * desactiva el turno de todos los demas.
     *
     * @param indice Indice del jugador al que le toca el turno.
     */
    public void setTurnoJugador(int indice) {
        if (indice < 0 || indice >= listaTarjetas.size()) {
            return;
        }

        this.indiceJugadorActual = indice;

        for (int i = 0; i < listaTarjetas.size(); i++) {
            listaTarjetas.get(i).setEsSuTurno(i == indice);
        }
    }

    /**
     * Avanza automaticamente el turno al siguiente jugador en orden circular.
     */
    public void SiguienteTurno() {
        if (listaTarjetas.isEmpty()) return;
        
        int siguienteIndex = (indiceJugadorActual + 1) % listaTarjetas.size();
        setTurnoJugador(siguienteIndex);
    }

    /**
     * Actualiza la cantidad de cuadros conquistados para un jugador en especifico.
     *
     * @param indiceJugador Indice del jugador (0 a N-1).
     * @param cuadros        Nueva cantidad de cuadros.
     */
    public void actualizarCuadrosJugador(int indiceJugador, int cuadros) {
        if (indiceJugador >= 0 && indiceJugador < listaTarjetas.size()) {
            listaTarjetas.get(indiceJugador).setContadorCuadros(cuadros);
        }
    }

    /**
     * Obtiene la tarjeta del jugador que actualmente tiene el turno activo.
     */
    public TarjetaJugador getJugadorActual() {
        if (indiceJugadorActual >= 0 && indiceJugadorActual < listaTarjetas.size()) {
            return listaTarjetas.get(indiceJugadorActual);
        }
        return null;
    }

    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    public int getCantidadJugadores() {
        return listaTarjetas.size();
    }
}
    

