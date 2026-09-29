package main;

import controlador.FuenteDeJugadas;
import eventos.EstadoPartida;
import eventos.Observador;
import modelo.IConsultaModeloPartida;

import javax.swing.*;
import java.awt.*;

/**
 * Vista principal correspondiente a un jugador.
 *
 * Su responsabilidad es:
 * - mostrar información al jugador;
 * - capturar acciones del usuario;
 * - enviar esas acciones al Controlador;
 * - actualizarse cuando el Modelo MVC le notifica un cambio.
 *
 * IMPORTANTE:
 * La Vista NO recibe información desde el Controlador.
 * La comunicación entre ambos va de Vista -> Controlador.
 *
 * Para consultar información, la Vista utiliza
 * IConsultaModeloPartida y obtiene los datos desde su Modelo MVC.
 *
 * En UML:
 * - Implementa Observador.
 * - Depende de FuenteDeJugadas para enviar acciones.
 * - Depende de IConsultaModeloPartida para consultar estado.
 */
public class VentanaPrincipal extends JFrame implements Observador {

    private final int jugadorLocalId;

    // Contrato usado por la Vista para enviar acciones al Controlador.
    private FuenteDeJugadas fuenteDeJugadas;

    // Contrato usado por la Vista para consultar su Modelo MVC.
    private IConsultaModeloPartida modelo;

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

        /*
         * Elemento temporal de prueba.
         *
         * Será sustituido por la interacción real con PanelTablero,
         * donde la posición seleccionada por el jugador se convertirá
         * en orientación, fila y columna.
         */
        JButton btnJugar = new JButton("Simular jugada");

        btnJugar.addActionListener(e -> {

            if (fuenteDeJugadas != null) {

                fuenteDeJugadas.alJugar(
                        jugadorLocalId,
                        true, // temporal: orientación horizontal
                        0,    // temporal: fila
                        0     // temporal: columna
                );
            }
        });

        setLayout(new BorderLayout());

        add(lblEstado, BorderLayout.CENTER);
        add(btnJugar, BorderLayout.SOUTH);
    }

    /**
     * Recibe la referencia al contrato mediante el cual
     * la Vista puede enviar acciones al Controlador.
     *
     * En UML:
     * + setFuenteDeJugadas(fuenteDeJugadas: FuenteDeJugadas): void
     */
    public void setFuenteDeJugadas(
            FuenteDeJugadas fuenteDeJugadas) {

        this.fuenteDeJugadas = fuenteDeJugadas;
    }

    /**
     * Recibe la referencia de lectura hacia el Modelo MVC.
     *
     * La Vista utiliza esta interfaz para consultar el estado
     * que debe mostrar.
     *
     * En UML:
     * + setModelo(modelo: IConsultaModeloPartida): void
     */
    public void setModelo(
            IConsultaModeloPartida modelo) {

        this.modelo = modelo;
    }

    /**
     * Método invocado mediante Observer cuando cambia el Modelo MVC.
     *
     * El contrato Observador actualmente recibe EstadoPartida como
     * parámetro porque así fue definido originalmente por el equipo.
     *
     * Sin embargo, en esta versión la Vista no utiliza directamente
     * ese parámetro. La notificación solamente indica que hubo un cambio
     * y la Vista consulta el estado actual mediante
     * IConsultaModeloPartida.
     *
     * Esto aproxima el flujo al esquema trabajado en clase:
     *
     * Modelo -> notifica -> Vista
     * Vista -> consulta -> Modelo
     */
    @Override
    public void alCambiarPartida(EstadoPartida estado) {

        if (modelo == null) {
            return;
        }

        EstadoPartida estadoActual =
                modelo.getEstado();

        if (estadoActual == null) {
            return;
        }

        lblEstado.setText(
                "Actualización recibida. Turno: "
                        + estadoActual.getJugadorEnTurno()
        );
    }
}