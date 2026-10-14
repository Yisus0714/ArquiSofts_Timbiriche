/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 *
 * @author luisb
 */
public class Recuadro {
    
private Linea arriba;    
private Linea abajo;  
private Linea izquierda;  
private Linea derecha;
private Jugador jugadorDueño;
private int fila;
private int columna;

public Recuadro() {

}

    public Recuadro(Linea arriba, Linea abajo, Linea izquierda, Linea derecha, int fila, int columna) {
        this.arriba = arriba;
        this.abajo = abajo;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.fila = fila;
        this.columna = columna;
        this.jugadorDueño = null;
    }

    public Jugador getJugadorDueño() {
        return jugadorDueño;
    }
    
    public boolean estaCerrado() {
        if(
                arriba.estaJugada() &&
                abajo.estaJugada() &&
                izquierda.estaJugada() &&
                derecha.estaJugada()){
            return true;
        }
        return false;
    }
    
    
    public void asignarJugadorDueño(Jugador jugador) {
        this.jugadorDueño = jugador;
    }
    
    public void liberarRecuadro() {
        this.jugadorDueño = null;
    }
    
    public boolean tieneDueño() {
        return this.jugadorDueño != null;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
    
    






}
