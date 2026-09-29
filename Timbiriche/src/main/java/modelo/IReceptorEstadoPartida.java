package modelo;

import eventos.EstadoPartida;

/**
 * Contrato que permite al modelo general del juego
 * entregar un nuevo estado a un Modelo MVC.
 *
 * Sirve para evitar que el dominio dependa directamente
 * de la clase concreta ModeloPartida.
 *
 * En UML:
 * - Se representará como <<interface>>.
 * - ModeloPartida implementará este contrato.
 * - El modelo general del juego dependerá de esta abstracción.
 */
public interface IReceptorEstadoPartida {

    /**
     * Recibe el nuevo estado generado por el modelo general.
     *
     * En UML:
     * + actualizarEstado(estado: EstadoPartida): void
     */
    void actualizarEstado(EstadoPartida estado);
}
