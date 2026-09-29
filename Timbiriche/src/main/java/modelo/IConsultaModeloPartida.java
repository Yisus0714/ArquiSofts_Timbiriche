package modelo;

import eventos.EstadoPartida;

/**
 * Contrato que permite consultar información del Modelo MVC.
 *
 * La Vista utiliza esta interfaz para obtener el estado que necesita
 * mostrar al jugador. De esta forma, la Vista no depende directamente
 * de una clase concreta como ModeloPartida.
 *
 * IMPORTANTE:
 * Este Modelo MVC no es el modelo general del juego (Dominio).
 * El Modelo MVC representa la información que necesita la interfaz
 * de un jugador.
 *
 * En UML:
 * - Se representará como una <<interface>>.
 * - VentanaPrincipal tendrá una dependencia hacia esta interfaz.
 * - ModeloPartida será la clase que implemente este contrato.
 */
public interface IConsultaModeloPartida {

    /**
     * Obtiene el estado que actualmente conoce el Modelo MVC.
     *
     * Por ahora usamos EstadoPartida para concentrar la información
     * que necesita la Vista. Más adelante podremos ajustar este
     * contrato si el diseño definitivo lo requiere.
     *
     * En UML:
     * + getEstado(): EstadoPartida
     *
     * @return estado actual disponible para la Vista
     */
    EstadoPartida getEstado();

}
