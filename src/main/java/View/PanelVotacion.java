/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Control.EjercerTurnoControl;
import Dominio.ResultadoVotacion;
import Dominio.Voto;
import Model.IConsultaVotacion;
import dto.VotacionDTO;

import javax.swing.*;
import java.awt.*;

/**
 * Pantallas de la votación para terminar la partida: votación en curso,
 * partida terminada por acuerdo y partida que continúa.
 *
 * @author luisb
 */
public class PanelVotacion extends PanelMensaje {

    private static final int SEGUNDOS_ESPERA = 3;

    private final IConsultaVotacion consultaVotacion;
    private final EjercerTurnoControl control;
    private final Runnable alVolver;

    private Timer temporizador;
    private ResultadoVotacion resultadoMostrado;
    private int segundosRestantes;
    private JLabel lblCuentaRegresiva;

    public PanelVotacion(IConsultaVotacion consultaVotacion, EjercerTurnoControl control, Runnable alVolver) {
        this.consultaVotacion = consultaVotacion;
        this.control = control;
        this.alVolver = alVolver;
    }

    /**
     * Dibuja la pantalla que corresponde al estado de la votación.
     *
     * @return true si hay una votación que mostrar, false si se debe volver al tablero
     */
    public boolean actualizarDatos() {
        try {
            VotacionDTO votacion = consultaVotacion.getVotacion();
            ResultadoVotacion resultado = votacion.getResultado();

            if (resultado == ResultadoVotacion.EN_PROCESO) {
                detenerTemporizador();
                mostrarVotacion(votacion);
            } else if (resultado == ResultadoVotacion.GANO_A_FAVOR) {
                mostrarResultado(votacion, true);
            } else if (resultado == ResultadoVotacion.GANO_EN_CONTRA) {
                // Si el aviso ya se descartó, la partida sigue y no se vuelve a mostrar
                if (resultadoMostrado == resultado && temporizador == null) {
                    return false;
                }
                mostrarResultado(votacion, false);
            } else {
                detenerTemporizador();
                resultadoMostrado = null;
                return false;
            }

            revalidate();
            repaint();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void mostrarVotacion(VotacionDTO votacion) {
        resultadoMostrado = ResultadoVotacion.EN_PROCESO;
        pnlContenido.removeAll();

        JButton btnTerminar = crearBotonPrimario("Sí, terminar", AZUL);
        btnTerminar.addActionListener(e -> votar(Voto.FAVOR));

        JButton btnContinuar = crearBotonSecundario("No, continuar");
        btnContinuar.addActionListener(e -> votar(Voto.EN_CONTRA));

        // Quien ya votó (incluido quien propuso terminar) espera a los demás
        btnTerminar.setEnabled(!votacion.isYaVoto());
        btnContinuar.setEnabled(!votacion.isYaVoto());

        pnlContenido.add(crearIcono("?", AZUL));
        pnlContenido.add(Box.createVerticalStrut(20));
        pnlContenido.add(crearTitulo("¿Terminar la partida?"));
        pnlContenido.add(Box.createVerticalStrut(12));
        pnlContenido.add(crearDescripcion("Todos los jugadores deben estar de acuerdo para finalizar y calcular la puntuación final."));
        pnlContenido.add(Box.createVerticalStrut(24));
        pnlContenido.add(crearFilaConteo(votacion));
        pnlContenido.add(Box.createVerticalStrut(24));
        pnlContenido.add(crearFilaBotones(btnTerminar, btnContinuar));
        pnlContenido.add(Box.createVerticalStrut(20));
        pnlContenido.add(crearNota(votacion.isYaVoto() ? "Esperando a los demás jugadores..." : "Votación anónima en tiempo real"));
    }

    private void mostrarResultado(VotacionDTO votacion, boolean terminada) {
        ResultadoVotacion resultado = terminada ? ResultadoVotacion.GANO_A_FAVOR : ResultadoVotacion.GANO_EN_CONTRA;

        // Si ya se está mostrando este resultado no se reinicia la cuenta regresiva
        if (resultadoMostrado == resultado) {
            return;
        }
        detenerTemporizador();
        resultadoMostrado = resultado;
        pnlContenido.removeAll();

        JButton btnContinuar;
        Runnable accion;

        if (terminada) {
            btnContinuar = crearBotonPrimario("Ver resultados finales  →", AZUL);
            accion = control::verResultados;
            pnlContenido.add(crearIcono("✓", AZUL));
            pnlContenido.add(Box.createVerticalStrut(20));
            pnlContenido.add(crearTitulo("Partida terminada por acuerdo"));
            pnlContenido.add(Box.createVerticalStrut(12));
            pnlContenido.add(crearDescripcion("Todos los jugadores han votado a favor de terminar la partida anticipadamente. Calculando resultados oficiales..."));
        } else {
            btnContinuar = crearBotonPrimario("Regresar al tablero", AZUL);
            accion = alVolver;
            pnlContenido.add(crearIcono("!", TEXTO));
            pnlContenido.add(Box.createVerticalStrut(20));
            pnlContenido.add(crearTitulo("La partida continúa"));
            pnlContenido.add(Box.createVerticalStrut(12));
            pnlContenido.add(crearDescripcion("Al menos un jugador votó por continuar el juego. La votación se ha desestimado."));
        }

        btnContinuar.addActionListener(e -> {
            detenerTemporizador();
            accion.run();
        });

        lblCuentaRegresiva = crearNota("");

        pnlContenido.add(Box.createVerticalStrut(24));
        pnlContenido.add(crearFilaConteo(votacion));
        pnlContenido.add(Box.createVerticalStrut(24));
        pnlContenido.add(crearFilaBotones(btnContinuar));
        pnlContenido.add(Box.createVerticalStrut(20));
        pnlContenido.add(lblCuentaRegresiva);

        iniciarCuentaRegresiva(terminada ? "Redirigiendo a resultados en " : "Reanudando partida en ", accion);
    }

    private void iniciarCuentaRegresiva(String texto, Runnable alTerminar) {
        segundosRestantes = SEGUNDOS_ESPERA;
        lblCuentaRegresiva.setText(texto + segundosRestantes + "s...");

        temporizador = new Timer(1000, e -> {
            segundosRestantes--;
            if (segundosRestantes <= 0) {
                detenerTemporizador();
                alTerminar.run();
            } else {
                lblCuentaRegresiva.setText(texto + segundosRestantes + "s...");
            }
        });
        temporizador.start();
    }

    private void detenerTemporizador() {
        if (temporizador != null) {
            temporizador.stop();
            temporizador = null;
        }
    }

    private void votar(Voto voto) {
        try {
            control.votarFin(voto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel crearFilaConteo(VotacionDTO votacion) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        fila.setOpaque(false);
        fila.setAlignmentX(CENTER_ALIGNMENT);
        fila.add(crearChip("Sí:", votacion.getAFavor(), AZUL));
        fila.add(crearChip("No:", votacion.getEnContra(), new Color(148, 163, 184)));
        return fila;
    }

    private JPanel crearChip(String etiqueta, int cantidad, Color colorPunto) {
        JPanel chip = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        chip.setBackground(Color.WHITE);
        chip.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true));

        JLabel lblPunto = new JLabel("●");
        lblPunto.setFont(new Font("SansSerif", Font.PLAIN, 9));
        lblPunto.setForeground(colorPunto);

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblEtiqueta.setForeground(TEXTO_SUAVE);

        JLabel lblCantidad = new JLabel(String.valueOf(cantidad));
        lblCantidad.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblCantidad.setForeground(TEXTO);

        chip.add(lblPunto);
        chip.add(lblEtiqueta);
        chip.add(lblCantidad);
        return chip;
    }

    @Override
    public void removeNotify() {
        detenerTemporizador();
        super.removeNotify();
    }

}
