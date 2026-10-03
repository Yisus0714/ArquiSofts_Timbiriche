package modelo.tablero;

public class Tablero implements TableroLectura {

    private final int puntosPorLado;
    private final boolean[][] lineasH; // [fila][col], col va de 0 a puntosPorLado-2
    private final boolean[][] lineasV; // [fila][col], fila va de 0 a puntosPorLado-2
    private final int[][] cuadros;     // [fila][col], 0 = libre

    public Tablero(int puntosPorLado) {
        if (puntosPorLado < 2) {
            throw new IllegalArgumentException("El tablero debe tener al menos 2 puntos por lado");
        }
        this.puntosPorLado = puntosPorLado;
        this.lineasH = new boolean[puntosPorLado][puntosPorLado - 1];
        this.lineasV = new boolean[puntosPorLado - 1][puntosPorLado];
        this.cuadros = new int[puntosPorLado - 1][puntosPorLado - 1];
    }


    public static int tamanoPara(int numJugadores) {
        return switch (numJugadores) {
            case 2 -> 10;
            case 3 -> 20;
            case 4 -> 30;
            default -> throw new IllegalArgumentException(
                    "Número de jugadores no soportado: " + numJugadores);
        };
    }



    /**
     * Traza una línea horizontal entre el punto (fila, col) y (fila, col+1).
     *
     * @return cantidad de cuadros completados (0, 1 o 2), o -1 si la línea ya existía
     */
    public int trazarLineaH(int fila, int col, int jugadorId) {
        validarRangoH(fila, col);
        if (lineasH[fila][col]) {
            return -1;
        }
        lineasH[fila][col] = true;

        int completados = 0;
        // Cuadro arriba de la línea (fila-1, col)
        if (fila > 0 && cuadroListoParaCerrar(fila - 1, col)) {
            cuadros[fila - 1][col] = jugadorId;
            completados++;
        }
        // Cuadro debajo de la línea (fila, col)
        if (fila < puntosPorLado - 1 && cuadroListoParaCerrar(fila, col)) {
            cuadros[fila][col] = jugadorId;
            completados++;
        }
        return completados;
    }

    /**
     * Traza una línea vertical entre el punto (fila, col) y (fila+1, col).
     *
     * @return cantidad de cuadros completados (0, 1 o 2), o -1 si la línea ya existía
     */
    public int trazarLineaV(int fila, int col, int jugadorId) {
        validarRangoV(fila, col);
        if (lineasV[fila][col]) {
            return -1;
        }
        lineasV[fila][col] = true;

        int completados = 0;
        // Cuadro a la izquierda (fila, col-1)
        if (col > 0 && cuadroListoParaCerrar(fila, col - 1)) {
            cuadros[fila][col - 1] = jugadorId;
            completados++;
        }
        // Cuadro a la derecha (fila, col)
        if (col < puntosPorLado - 1 && cuadroListoParaCerrar(fila, col)) {
            cuadros[fila][col] = jugadorId;
            completados++;
        }
        return completados;
    }

    /** Un cuadro está listo para cerrar si sus 4 lados están trazados y aún no tiene dueño. */
    private boolean cuadroListoParaCerrar(int fila, int col) {
        return cuadros[fila][col] == 0
                && lineasH[fila][col]       // lado de arriba
                && lineasH[fila + 1][col]   // lado de abajo
                && lineasV[fila][col]       // lado izquierdo
                && lineasV[fila][col + 1];  // lado derecho
    }


    @Override
    public int puntosPorLado() {
        return puntosPorLado;
    }

    @Override
    public boolean lineaTrazada(boolean horizontal, int fila, int col) {
        if (horizontal) {
            validarRangoH(fila, col);
            return lineasH[fila][col];
        } else {
            validarRangoV(fila, col);
            return lineasV[fila][col];
        }
    }

    @Override
    public int duenoDelCuadro(int fila, int col) {
        validarRangoCuadro(fila, col);
        return cuadros[fila][col];
    }

    @Override
    public boolean quedanCuadrosLibres() {
        for (int[] fila : cuadros) {
            for (int dueno : fila) {
                if (dueno == 0) return true;
            }
        }
        return false;
    }

    @Override
    public int totalCuadros() {
        return (puntosPorLado - 1) * (puntosPorLado - 1);
    }


    private void validarRangoH(int fila, int col) {
        if (fila < 0 || fila >= puntosPorLado || col < 0 || col >= puntosPorLado - 1) {
            throw new IndexOutOfBoundsException("Línea horizontal fuera de rango: " + fila + "," + col);
        }
    }

    private void validarRangoV(int fila, int col) {
        if (fila < 0 || fila >= puntosPorLado - 1 || col < 0 || col >= puntosPorLado) {
            throw new IndexOutOfBoundsException("Línea vertical fuera de rango: " + fila + "," + col);
        }
    }

    private void validarRangoCuadro(int fila, int col) {
        if (fila < 0 || fila >= puntosPorLado - 1 || col < 0 || col >= puntosPorLado - 1) {
            throw new IndexOutOfBoundsException("Cuadro fuera de rango: " + fila + "," + col);
        }
    }
}