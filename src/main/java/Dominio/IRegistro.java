/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dominio;

import java.util.List;

/**
 *
 * @author luisb
 */
public interface IRegistro {
    public void registrarJugador(String nombre, Avatar avatar) throws Exception;
    public List<Avatar> getAvataresDisponibles();


    
}
