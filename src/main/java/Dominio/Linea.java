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
public class Linea {
    
    private Punto inicio;
    private Punto fin;
    private Orientacion orientacion;
    private Jugador jugador;
    private List<Recuadro> recuadros;

    public Linea() {
    }

    public Linea(Punto inicio, Punto fin, Orientacion orientacion) {
        this.inicio = inicio;
        this.fin = fin;
        this.orientacion = orientacion;
        this.jugador = null;
        this.recuadros = new ArrayList<>();
    }
    
    public boolean estaJugada(){
        return jugador != null;
    }
    
    public void quitarJugador(){
        if (!estaJugada()) {
            throw new IllegalStateException(
                "Esta línea no está jugada"
            );
        }

        // La línea vuelve a estar disponible.
        this.jugador = null;


        for (Recuadro recuadro : recuadros) {

            // Si quedó abierto y tenía dueño, hay que liberarlo.
            if (!recuadro.estaCerrado() && recuadro.tieneDueño()) {

                Jugador dueñoAnterior = recuadro.getJugadorDueño();
                
                recuadro.liberarRecuadro();

                dueñoAnterior.restarPuntos();
            }
        }
    }
    
    
    public List<Recuadro> asignarJugador(Jugador jugador) {
        
        if (estaJugada()) {
            throw new IllegalStateException("Esta línea ya fue jugada");
        }
        
        this.jugador = jugador;
        
        List<Recuadro> recuadrosCompletados = new ArrayList<>();
        
        for(Recuadro recuadro: recuadros) {
            if(recuadro.estaCerrado()) {
                recuadro.asignarJugadorDueño(jugador);
                recuadrosCompletados.add(recuadro);
                jugador.sumarPuntos();
            }
        }
        
        return recuadrosCompletados;
    }
    
    
    public void agregarRecuadro(Recuadro recuadro){
        recuadros.add(recuadro);
    }
    
    

    public Punto getInicio() {
        return inicio;
    }

    public Punto getFin() {
        return fin;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public List<Recuadro> getRecuadros() {
        return recuadros;
    }
    
    
    
    
}
