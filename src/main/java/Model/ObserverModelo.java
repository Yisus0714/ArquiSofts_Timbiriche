package Model;

import eventos.Event;

/**
 * Lo que un Modelo le avisa a quien lo observa (sus Vistas y, en un caso, otro Modelo).
 * El evento solo dice qué pasó; el receptor consulta el detalle con los get del Modelo.
 */
public interface ObserverModelo {

    void actualizar(Event evento);
}
