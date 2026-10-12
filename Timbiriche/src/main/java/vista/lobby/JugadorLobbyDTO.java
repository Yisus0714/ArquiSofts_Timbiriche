
package vista.lobby;

import java.awt.Color;
import java.util.Objects;

/**
 * Datos de un jugador para las pruebas visuales del lobby.
 *
 * No representa la entidad Jugador del Dominio.
 */
public class JugadorLobbyDTO {

    private final int id;
    private final String nombre;
    private final String avatar;
    private final Color color;
    private final boolean jugadorLocal;

    private boolean listo;

    public JugadorLobbyDTO(
            int id,
            String nombre,
            String avatar,
            Color color,
            boolean jugadorLocal,
            boolean listo
    ) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El identificador debe ser positivo."
            );
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del jugador es obligatorio."
            );
        }

        this.id = id;
        this.nombre = nombre.trim();
        this.avatar = Objects.requireNonNull(avatar);
        this.color = Objects.requireNonNull(color);
        this.jugadorLocal = jugadorLocal;
        this.listo = listo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public Color getColor() {
        return color;
    }

    public boolean isJugadorLocal() {
        return jugadorLocal;
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }
}
