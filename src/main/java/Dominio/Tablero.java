/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisb
 */
public class Tablero {
    
    private final int dimension;
    
    private final Punto[][] puntos;
    
    private final Linea[][] lineasHorizontales;
    
    private final Linea[][] lineasVerticales;
    
    private final Recuadro[][] recuadros;
    
  
    private boolean coordenadasValidas(int x, int y) {
        
        return x >= 0 && x < dimension
        && y >= 0 && y < dimension;
    
    }
    
    private boolean esLineaJugable(int x1, int y1, int x2, int y2) {
        if (!coordenadasValidas(x1, y1) || !coordenadasValidas(x2, y2)) {
            return false;
        }

        // Puntos adyacentes horizontalmente
        if (x1 == x2 && Math.abs(y1 - y2) == 1) {
            int fila = x1;
            int columna = Math.min(y1, y2);

            return !lineasHorizontales[fila][columna].estaJugada();
        }

        // Puntos adyacentes verticalmente
        if (y1 == y2 && Math.abs(x1 - x2) == 1) {
            int fila = Math.min(x1, x2);
            int columna = y1;

            return !lineasVerticales[fila][columna].estaJugada();
        }
        return false;
    }
            
    public Jugada jugarLinea(int x1, int y1, int x2, int y2, Jugador jugador) throws Exception {
        
        if(!coordenadasValidas(x1, y1) || !coordenadasValidas(x2, y2)) {
            throw new Exception("Coordenadas no válidas");
        }
        
        if(!esLineaJugable(x1, y1, x2, y2)) {
            throw new Exception("Esos dos puntos no son jugables");
        }
           
        Linea linea = buscarLinea(x1, y1, x2, y2);
        
        
        List<Recuadro> recuadrosCompletados = linea.asignarJugador(jugador);
        
        return new Jugada(linea, recuadrosCompletados, jugador);
        
    }
    
    public PuntoSeleccionado seleccionarPunto(int x, int y) throws Exception {
        
        if(!coordenadasValidas(x, y)) {
            throw new Exception("Coordenadas no validas");
        }
        
        Punto punto = buscarPunto(x, y);
        
        List<Punto> jugadasPosibles = buscarJugadasPosibles(punto);
        
        PuntoSeleccionado puntoSelec = new PuntoSeleccionado(punto, jugadasPosibles);
        
        return puntoSelec;
    }
    
    
    public Tablero(int dimension) {
        if (dimension != 10 && dimension != 20 && dimension != 30) {
            throw new IllegalArgumentException("Dimensión inválida: " + dimension);
        }
        this.dimension = dimension;
        this.puntos = generarPuntos();
        this.lineasHorizontales = generarLineasHorizontales();
        this.lineasVerticales = generarLineasVerticales();
        this.recuadros = generarRecuadros(); // depende de las dos anteriores
    }
    
    private Punto[][] generarPuntos() {
        Punto[][] p = new Punto[dimension][dimension];
        for (int fila = 0; fila < dimension; fila++) {
            for (int col = 0; col < dimension; col++) {
                p[fila][col] = new Punto(fila, col);
            }
        }
        return p;
    }

    private Linea[][] generarLineasHorizontales() {
        // Conectan (fila, col) con (fila, col+1)
        Linea[][] h = new Linea[dimension][dimension - 1];
        for (int fila = 0; fila < dimension; fila++) {
            for (int col = 0; col < dimension - 1; col++) {
                Punto inicio = puntos[fila][col];
                Punto fin = puntos[fila][col + 1];
                h[fila][col] = new Linea(inicio, fin, Orientacion.HORIZONTAL);
            }
        }
        return h;
    }

    private Linea[][] generarLineasVerticales() {
        // Conectan (fila, col) con (fila+1, col)
        Linea[][] v = new Linea[dimension - 1][dimension];
        for (int fila = 0; fila < dimension - 1; fila++) {
            for (int col = 0; col < dimension; col++) {
                Punto inicio = puntos[fila][col];
                Punto fin = puntos[fila + 1][col];
                v[fila][col] = new Linea(inicio, fin, Orientacion.VERTICAL);
            }
        }
        return v;
    }

    private Recuadro[][] generarRecuadros() {
        Recuadro[][] r = new Recuadro[dimension - 1][dimension - 1];
        for (int fila = 0; fila < dimension - 1; fila++) {
            for (int col = 0; col < dimension - 1; col++) {
                Linea arriba    = lineasHorizontales[fila][col];
                Linea abajo     = lineasHorizontales[fila + 1][col];
                Linea izquierda = lineasVerticales[fila][col];
                Linea derecha   = lineasVerticales[fila][col + 1];

                Recuadro recuadro = new Recuadro(arriba, abajo, izquierda, derecha, fila, col);
                r[fila][col] = recuadro;

                // La otra mitad de la asociación: cada línea aprende a qué recuadro(s) pertenece
                arriba.agregarRecuadro(recuadro);
                abajo.agregarRecuadro(recuadro);
                izquierda.agregarRecuadro(recuadro);
                derecha.agregarRecuadro(recuadro);
            }
        }
        return r;
    }

    private Punto buscarPunto(int fila, int columna) {
        if (fila < 0 || fila >= dimension || columna < 0 || columna >= dimension) {
            throw new IllegalArgumentException("Coordenadas fuera del tablero: (" + fila + "," + columna + ")");
        }
        return puntos[fila][columna];
    }

    private Linea buscarLinea(int x1, int y1, int x2, int y2) {

        // Puntos adyacentes horizontalmente
        if (x1 == x2 && Math.abs(y1 - y2) == 1) {
            int columna = Math.min(y1, y2);
            return lineasHorizontales[x1][columna];
        }

        // Puntos adyacentes verticalmente
        if (y1 == y2 && Math.abs(x1 - x2) == 1) {
            int fila = Math.min(x1, x2);
            return lineasVerticales[fila][y1];
        }

        return null;
    }
    
    
    
    private List<Punto> buscarJugadasPosibles(Punto punto) {
        List<Punto> posibles = new ArrayList<>();
        int fila = punto.getFila();
        int col = punto.getColumna();

        if (col > 0 && !lineasHorizontales[fila][col - 1].estaJugada()) {
            posibles.add(puntos[fila][col - 1]);
        }
        if (col < dimension - 1 && !lineasHorizontales[fila][col].estaJugada()) {
            posibles.add(puntos[fila][col + 1]);
        }
        if (fila > 0 && !lineasVerticales[fila - 1][col].estaJugada()) {
            posibles.add(puntos[fila - 1][col]);
        }
        if (fila < dimension - 1 && !lineasVerticales[fila][col].estaJugada()) {
            posibles.add(puntos[fila + 1][col]);
        }
        return posibles;
    }
    
    public boolean estaCompleto() {
        for (Recuadro[] fila : recuadros) {
            for (Recuadro r : fila) {
                if (!r.tieneDueño()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getDimension() { return dimension; }
    
}
