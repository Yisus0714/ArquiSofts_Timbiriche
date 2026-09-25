package controlador;

import modelo.IOperacionesPartida;

public class ControladorPartida implements FuenteDeJugadas {

    private IOperacionesPartida modelo;

    public void setModelo(IOperacionesPartida modelo) {
        this.modelo = modelo;
    }

    @Override
    public void alJugar(int jugadorId,
                        boolean horizontal,
                        int fila,
                        int col) {

        modelo.jugar(jugadorId, horizontal, fila, col);
    }
}
