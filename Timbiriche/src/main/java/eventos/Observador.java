package eventos;

/**
 * Contrato del patrón Observer utilizado para notificar
 * que el estado de la partida cambió.
 *
 * Actualmente la notificación incluye EstadoPartida porque
 * este fue el contrato definido originalmente por el equipo.
 *
 * Sin embargo, una Vista puede utilizar la notificación solamente
 * como aviso de que hubo un cambio y consultar después su Modelo MVC.
 *
 * En UML:
 * - Se representará como <<interface>>.
 * - VentanaPrincipal implementará este contrato.
 * - ModeloPartida mantendrá referencias a Observador
 *   para notificar cambios.
 */
public interface Observador {

    /**
     * Se invoca cada vez que el estado de la partida cambia:
     * una jugada, un cuadro completado, un cambio de turno,
     * un abandono o el fin de la partida.
     *
     * En la implementación actual, la Vista puede ignorar
     * el parámetro y consultar el estado desde su Modelo MVC.
     *
     * En UML:
     * + alCambiarPartida(estado: EstadoPartida): void
     *
     * @param estado estado generado después del cambio de la partida
     */
    void alCambiarPartida(EstadoPartida estado);
}