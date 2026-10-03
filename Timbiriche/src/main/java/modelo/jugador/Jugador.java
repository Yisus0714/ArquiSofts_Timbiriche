package modelo.jugador;

import java.util.Objects;

public class Jugador {

    private final int id;
    private final String nombre;
    private final Avatar avatar;
    private final ColorJugador color;
    private int puntaje;

    public Jugador(int id, String nombre, Avatar avatar, ColorJugador color) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.puntaje = 0;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public ColorJugador getColor() {
        return color;
    }

    public int getPuntaje() {
        return puntaje;
    }

    /** Suma cuadros completados al puntaje del jugador. */
    public void sumarPuntos(int cuadrosCompletados) {
        if (cuadrosCompletados < 0) {
            throw new IllegalArgumentException("No se pueden sumar puntos negativos");
        }
        this.puntaje += cuadrosCompletados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador otro = (Jugador) o;
        return id == otro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", nombre='" + nombre + "', puntaje=" + puntaje + "}";
    }
}