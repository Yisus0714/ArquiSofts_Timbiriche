package main;

import controlador.FuenteDeJugadas;
import eventos.EstadoPartida;
import eventos.Observador;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame implements Observador {

    private final int jugadorLocalId;
    private FuenteDeJugadas fuenteDeJugadas;

    private final JLabel lblEstado;

    public VentanaPrincipal(int jugadorLocalId) {

        this.jugadorLocalId = jugadorLocalId;

        setTitle("Timbiriche - Jugador " + jugadorLocalId);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        lblEstado = new JLabel(
                "Esperando cambios...",
                SwingConstants.CENTER
        );

        // Botón temporal para validar el flujo
        // Vista -> Controlador -> Modelo -> Observer -> Vistas
        JButton btnJugar = new JButton("Simular jugada");

        btnJugar.addActionListener(e -> {

            if (fuenteDeJugadas != null) {

                fuenteDeJugadas.alJugar(
                        jugadorLocalId,
                        true, // temporal: orientación horizontal
                        0,             // temporal: fila
                        0              // temporal: columna
                );
            }
        });

        setLayout(new BorderLayout());

        add(lblEstado, BorderLayout.CENTER);
        add(btnJugar, BorderLayout.SOUTH);
    }

    public void setFuenteDeJugadas(
            FuenteDeJugadas fuenteDeJugadas) {

        this.fuenteDeJugadas = fuenteDeJugadas;
    }

    @Override
    public void alCambiarPartida(EstadoPartida estado) {

        if (estado == null) {
            return;
        }

        lblEstado.setText(
                "Actualización recibida. Turno: "
                        + estado.getJugadorEnTurno()
        );
    }
}