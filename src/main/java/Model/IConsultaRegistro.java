package Model;

import Dominio.Avatar;
import java.util.List;

/**
 * Lo que la Vista de registro puede consultar al Modelo.
 */
public interface IConsultaRegistro {

    public List<Avatar> getAvataresDisponibles();

    /** true si este jugador ya está dado de alta en la partida (la Vista de registro se oculta). */
    public boolean estoyRegistrado();
}
