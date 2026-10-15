package Model;

/**
 * Contexto de UN jugador que el dominio no conoce: quién es y qué esquema de colores eligió.
 * Lo escribe ModeloRegistro (alta) y los modelos que sacan al jugador de la partida (baja);
 * el resto de los modelos solo lo consulta por SessionConsulta.
 *
 * @author luisb
 */
public class Session implements SessionConsulta {

    private String jugadorLocal;
    private EsquemaColores colores;

    public Session(String jugadorLocal, EsquemaColores colores) {
        this.jugadorLocal = jugadorLocal;
        this.colores = colores;
    }

    public void setJugadorLocal(String jugadorLocal) {
        this.jugadorLocal = jugadorLocal;
    }

    public void setColores(EsquemaColores colores) {
        this.colores = colores;
    }

    @Override
    public String getJugadorLocal() {
        return jugadorLocal;
    }

    @Override
    public EsquemaColores getColores() {
        return colores;
    }
}
