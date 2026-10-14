/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio;

/**
 *
 * @author luisb
 */
public class Jugador {
    
    private String nombre;
    private ColorJugador color;
    private int puntaje;
    private Avatar avatar;


    public Jugador(String nombre, ColorJugador color, Avatar avatar) {
        this.nombre = nombre;
        this.color = color;
        this.puntaje = 0;
        this.avatar = avatar;
        
    }
    
    protected void restarPuntos(){
        puntaje = puntaje -1;
    }
    
    protected void sumarPuntos(){
        puntaje += 1;
    }
    
    public char getInicial(){
        return nombre.charAt(0);
    }
    

    public String getNombre() {
        return nombre;
    }

    public ColorJugador getColor() {
        return color;
    }

    public int getPuntaje() {
        return puntaje;
    }
    
    public Avatar getAvatar() {
        return avatar;
    }
    
    
    
    
}
