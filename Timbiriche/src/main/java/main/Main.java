package main;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Ensamblador ensamblador =
                    new Ensamblador();

            ensamblador.ensamblar();
        });
    }
}