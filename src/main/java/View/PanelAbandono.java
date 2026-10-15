/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Control.ControlEjercerTurno;
import javax.swing.*;

/**
 * Pantalla de confirmación para abandonar la partida en curso.
 *
 * @author luisb
 */
public class PanelAbandono extends PanelMensaje {

    private final ControlEjercerTurno control;
    private final Runnable alVolver;

    public PanelAbandono(ControlEjercerTurno control, Runnable alVolver) {
        this.control = control;
        this.alVolver = alVolver;
        initUI();
    }

    private void initUI() {
        JButton btnAbandonar = crearBotonPrimario("Sí, abandonar partida", ROJO);
        btnAbandonar.addActionListener(e -> {
            try {
                control.solicitarAbandono();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al abandonar", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnSeguir = crearBotonSecundario("No, seguir jugando");
        btnSeguir.addActionListener(e -> alVolver.run());

        pnlContenido.add(crearIcono("➔", ROJO));
        pnlContenido.add(Box.createVerticalStrut(20));
        pnlContenido.add(crearTitulo("¿Estás seguro de abandonar la partida?"));
        pnlContenido.add(Box.createVerticalStrut(12));
        pnlContenido.add(crearDescripcion("Si abandonas, tus puntos serán eliminados del tablero y la partida continuará con el resto de jugadores."));
        pnlContenido.add(Box.createVerticalStrut(28));
        pnlContenido.add(crearFilaBotones(btnAbandonar, btnSeguir));
        pnlContenido.add(Box.createVerticalStrut(20));
        pnlContenido.add(crearNota("Esta acción no se puede deshacer"));
    }

}
