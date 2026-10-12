package view;

import vista.abandono.DialogoConfirmarAbandono;
import vista.votacion.PanelVotacion;

import javax.swing.*;
import java.awt.*;

/**
 * Prueba visual de la pantalla de votación y del diálogo de abandono.
 */
public class PruebaVistaVotacion {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame ventana = new JFrame("Prueba Votación / Abandono");
            PanelVotacion panel = new PanelVotacion();
            panel.mostrarVotacion(1, 0);

            JButton btnV01 = new JButton("V01 Votación");
            btnV01.addActionListener(e -> panel.mostrarVotacion(1, 0));

            JButton btnV02 = new JButton("V02 Terminada");
            btnV02.addActionListener(e -> panel.mostrarTerminada(2, 0));

            JButton btnV03 = new JButton("V03 Continúa");
            btnV03.addActionListener(e -> panel.mostrarContinua(1, 1));

            JButton btnAbandono = new JButton("Diálogo de abandono");
            btnAbandono.addActionListener(e -> {
                DialogoConfirmarAbandono dialogo = new DialogoConfirmarAbandono(ventana);

                // Solo para la prueba: ambos botones cierran el diálogo..
                dialogo.getBtnCancelar().addActionListener(x -> dialogo.cerrar());
                dialogo.getBtnConfirmar().addActionListener(x -> dialogo.cerrar());

                dialogo.setVisible(true);
            });

            JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
            barra.add(btnV01);
            barra.add(btnV02);
            barra.add(btnV03);
            barra.add(btnAbandono);

            ventana.setLayout(new BorderLayout());
            ventana.add(panel, BorderLayout.CENTER);
            ventana.add(barra, BorderLayout.SOUTH);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(760, 520);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}