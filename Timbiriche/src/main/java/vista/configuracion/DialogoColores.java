/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.configuracion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana emergente para que un jugador elija su color.
 *
 * @author RAMSES
 */
public class DialogoColores extends JDialog {

    /** Lista de colores disponibles en el sistema. */
    public static final Color[] PALETA_COLORES = {
            new Color(0, 122, 255),   // Azul
            new Color(255, 59, 48),   // Rojo
            new Color(52, 199, 89),   // Verde
            new Color(255, 149, 0),   // Naranja
            new Color(175, 82, 222),  // Morado
            new Color(255, 204, 0),   // Amarillo
            new Color(0, 199, 190),   // Turquesa
            new Color(255, 45, 85)    // Rosa
    };

    private final String nombreJugador;
    private final List<Color> coloresEnUso;
    private JComboBox<ColorItem> comboColor;
    private JPanel pnlMuestra;

    private Color colorSeleccionado;
    private boolean confirmado;

    /**
     * Crea e inicializa la ventana de seleccion de color.
     *
     * @param padre Ventana principal que llama a esta ventana.
     * @param nombreJugador Nombre del jugador actual.
     * @param coloresEnUso Lista de colores ya ocupados por otros jugadores.
     */
    public DialogoColores(Frame padre, String nombreJugador, List<Color> coloresEnUso) {
        super(padre, "Seleccion de Color", true);
        this.nombreJugador = nombreJugador;
        this.coloresEnUso = (coloresEnUso != null) ? coloresEnUso : new ArrayList<>();
        this.confirmado = false;

        setLayout(new BorderLayout(0, 20));
        getContentPane().setBackground(new Color(245, 247, 250));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 24, 20, 24));

        // ---------------------------------------------------------------------
        // ENCABEZADO
        // ---------------------------------------------------------------------
        JLabel lblTitulo = new JLabel("Elige tu color para la partida");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(30, 32, 38));
        add(lblTitulo, BorderLayout.NORTH);

        // ---------------------------------------------------------------------
        // PANEL CENTRAL: CONTROLES DEL JUGADOR
        // ---------------------------------------------------------------------
        JPanel pnlJugador = new JPanel(new BorderLayout(12, 0));
        pnlJugador.setOpaque(false);

        JLabel lblNombre = new JLabel("Jugador: " + nombreJugador);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setPreferredSize(new Dimension(160, 30));

        comboColor = new JComboBox<>();
        comboColor.setRenderer(new ColorRenderer());
        comboColor.setPreferredSize(new Dimension(140, 32));

        // Cargar paleta de colores
        comboColor.addItem(new ColorItem(PALETA_COLORES[0], "Azul"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[1], "Rojo"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[2], "Verde"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[3], "Naranja"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[4], "Morado"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[5], "Amarillo"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[6], "Turquesa"));
        comboColor.addItem(new ColorItem(PALETA_COLORES[7], "Rosa"));

        // Muestra visual del color
        pnlMuestra = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
            }
        };
        pnlMuestra.setPreferredSize(new Dimension(32, 32));
        pnlMuestra.setOpaque(false);

        // Escuchar cambios para actualizar la muestra previa
        comboColor.addActionListener(e -> actualizarVistaPrevia());

        pnlJugador.add(lblNombre, BorderLayout.WEST);
        pnlJugador.add(comboColor, BorderLayout.CENTER);
        pnlJugador.add(pnlMuestra, BorderLayout.EAST);

        add(pnlJugador, BorderLayout.CENTER);

        // Seleccionar por defecto el primer color disponible que no este ocupado
        seleccionarPrimerColorDisponible();

        // ---------------------------------------------------------------------
        // PANEL INFERIOR: BOTONES
        // ---------------------------------------------------------------------
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBotones.setOpaque(false);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());

        JButton btnConfirmar = new JButton("Confirmar Color");
        btnConfirmar.setBackground(Color.WHITE);
        btnConfirmar.setForeground(Color.BLUE);
        btnConfirmar.addActionListener(e -> validarYConfirmar());

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnConfirmar);
        add(pnlBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(padre);
        setResizable(false);
    }

    /**
     * Comprueba si un color esta libre para usarse en la partida.
     *
     * @param color El color a validar.
     * @return true si esta libre, false si ya esta ocupado o es nulo.
     */
    public boolean esColorValido(Color color) {
        if (color == null) {
            return false;
        }
        for (Color c : coloresEnUso) {
            if (c.equals(color)) {
                return false; // El color ya esta en uso
            }
        }
        return true; // Color libre
    }

    /**
     * Selecciona de forma automatica el primer color disponible de la lista.
     */
    private void seleccionarPrimerColorDisponible() {
        for (int i = 0; i < comboColor.getItemCount(); i++) {
            ColorItem item = comboColor.getItemAt(i);
            if (esColorValido(item.getColor())) {
                comboColor.setSelectedIndex(i);
                actualizarVistaPrevia();
                return;
            }
        }
        actualizarVistaPrevia();
    }

    /**
     * Cambia el color del recuadro de previsualizacion segun la opcion elegida.
     */
    private void actualizarVistaPrevia() {
        ColorItem seleccionado = (ColorItem) comboColor.getSelectedItem();
        if (seleccionado != null) {
            pnlMuestra.setBackground(seleccionado.getColor());
            pnlMuestra.repaint();
        }
    }

    /**
     * Valida la seleccion y cierra la ventana si el color es valido.
     */
    private void validarYConfirmar() {
        ColorItem item = (ColorItem) comboColor.getSelectedItem();

        if (item == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar un color.",
                    "Error de Seleccion",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Utiliza la funcion de validacion de color duplicado
        if (!esColorValido(item.getColor())) {
            JOptionPane.showMessageDialog(
                    this,
                    "El color " + item.getNombre() + " ya esta en uso por otro jugador en la partida.\nPor favor, elige un color diferente.",
                    "Color Duplicado",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        this.colorSeleccionado = item.getColor();
        this.confirmado = true;
        dispose();
    }

    /**
     * Indica si la seleccion fue confirmada.
     *
     * @return true si se presiono el boton de confirmar, false en caso contrario.
     */
    public boolean isConfirmado() {
        return confirmado;
    }

    /**
     * Retorna el color elegido por el jugador.
     *
     * @return El objeto Color seleccionado o null si cancelo.
     */
    public Color getColorSeleccionado() {
        return colorSeleccionado;
    }

    // =========================================================================
    // CLASES AUXILIARES Y RENDERER
    // =========================================================================

    /**
     * Estructura simple para guardar un color y su nombre.
     */
    private static class ColorItem {
        final Color color;
        final String nombre;

        /**
         * Asigna un color y su nombre.
         *
         * @param color El color.
         * @param nombre El nombre del color.
         */
        ColorItem(Color color, String nombre) {
            this.color = color;
            this.nombre = nombre;
        }

        /**
         * Obtiene el color.
         *
         * @return El objeto Color.
         */
        public Color getColor() {
            return color;
        }

        /**
         * Obtiene el nombre del color.
         *
         * @return El nombre textual.
         */
        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    /**
     * Renderizador para mostrar un icono con el color junto al nombre en la lista desplegable.
     */
    private static class ColorRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof ColorItem) {
                ColorItem item = (ColorItem) value;
                label.setText(item.getNombre());
                label.setIcon(crearIconoColor(item.getColor()));
            }
            return label;
        }

        /**
         * Crea un pequeño cuadro con el color seleccionado.
         *
         * @param color El color a mostrar en el icono.
         * @return Un objeto Icon para la etiqueta.
         */
        private Icon crearIconoColor(Color color) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(color);
                    g2.fillRoundRect(x, y + 2, 14, 14, 4, 4);
                    g2.dispose();
                }

                @Override
                public int getIconWidth() { return 18; }

                @Override
                public int getIconHeight() { return 18; }
            };
        }
    }
}
