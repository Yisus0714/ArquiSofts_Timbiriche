package modelo.jugador;


public class ColorJugador {

    private final int r;
    private final int g;
    private final int b;

    public ColorJugador(int r, int g, int b) {
        validar(r, "r");
        validar(g, "g");
        validar(b, "b");
        this.r = r;
        this.g = g;
        this.b = b;
    }

    private void validar(int valor, String nombre) {
        if (valor < 0 || valor > 255) {
            throw new IllegalArgumentException("Componente " + nombre + " fuera de rango: " + valor);
        }
    }

    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }

    public java.awt.Color aColorAwt() {
        return new java.awt.Color(r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorJugador)) return false;
        ColorJugador otro = (ColorJugador) o;
        return r == otro.r && g == otro.g && b == otro.b;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(r, g, b);
    }
}