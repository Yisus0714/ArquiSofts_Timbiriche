package modelo.tablero;


public interface TableroLectura {

    /**
     * @return cantidad de puntos por lado.
     */
    int puntosPorLado();

    /**
     * Indica si ya existe una línea trazada entre dos puntos.
     *
     * @param horizontal true si la línea es horizontal, false si es vertical
     * @param fila        fila del punto de inicio de la línea
     * @param col         columna del punto de inicio de la línea
     * @return true si la línea ya fue trazada
     */
    boolean lineaTrazada(boolean horizontal, int fila, int col);

    /**
     * @param fila fila del cuadro (0 a puntosPorLado - 2)
     * @param col  columna del cuadro (0 a puntosPorLado - 2)
     * @return el id del jugador dueño del cuadro, o 0 si está libre
     */
    int duenoDelCuadro(int fila, int col);

    /**
     * @return true si todavía hay cuadros sin dueño en el tablero
     */
    boolean quedanCuadrosLibres();

    /**
     * @return cantidad total de cuadros en el tablero (puntosPorLado - 1 al cuadrado)
     */
    int totalCuadros();
}