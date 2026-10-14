/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Dominio.Jugada;
import Dominio.Jugador;
import Dominio.Linea;
import Dominio.Punto;
import Dominio.PuntoSeleccionado;
import Dominio.Recuadro;
import Dominio.Votacion;
import Dominio.ResultadoVotacion;
import Dominio.Voto;
import dto.JugadaDTO;
import dto.JugadorFinalDTO;
import dto.JugadorLobbyDTO;
import dto.JugadorPanelDTO;
import dto.LineaDTO;
import dto.PuntoDTO;
import dto.RecuadroDTO;
import dto.SeleccionDTO;
import dto.VotacionDTO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisb
 */
public class DTOMapper {
    
    
    public static JugadorPanelDTO toPanelDTO(
            Jugador jugador,
            boolean esTu,
            boolean enTurno,
            EsquemaColores esquema) {

        return new JugadorPanelDTO(
            jugador.getNombre(),
            jugador.getPuntaje(),
            esTu,
            enTurno,
            esquema.resolver(jugador.getColor()),
            jugador.getAvatar()
        );
    }
    
    public static List<JugadorPanelDTO> toPanelDTO(
        List<Jugador> jugadores,
        String nombreJugadorLocal,
        Jugador jugadorEnTurno,
        EsquemaColores esquema) {

        List<JugadorPanelDTO> jugadoresDTO = new ArrayList<>();

        for (Jugador jugador : jugadores) {

            boolean esTu = jugador.getNombre().equalsIgnoreCase(nombreJugadorLocal);
            boolean enTurno = jugador.equals(jugadorEnTurno);

            jugadoresDTO.add(
                toPanelDTO(jugador, esTu, enTurno, esquema)
            );
        }

        return jugadoresDTO;
    }
    
    public static JugadaDTO toJugadaDTO(
            Jugada jugada,
            EsquemaColores esquema) {

        if (jugada == null) {
            return null;
        }

        return new JugadaDTO(
            toLineaDTO(jugada.getLinea()),
            jugada.getRecuadrosCompletados()
                .stream()
                .filter(Recuadro::tieneDueño)      // tras un abandono algunos recuadros se liberan
                .map(DTOMapper::toRecuadroDTO)
                .toList(),
            esquema.resolver(jugada.getJugador().getColor()),
            jugada.getJugador().getInicial()
        );
    }
    
    public static List<JugadaDTO> toJugadasDTO(
        List<Jugada> jugadas,
        EsquemaColores esquema) {

        return jugadas.stream()
                .map(jugada -> toJugadaDTO(jugada, esquema))
                .toList();
    }

    private static LineaDTO toLineaDTO(Linea linea) {
        return new LineaDTO(
            toPuntoDTO(linea.getInicio()),
            toPuntoDTO(linea.getFin())
        );
    }

    private static PuntoDTO toPuntoDTO(Punto punto) {
        return new PuntoDTO(
            punto.getFila(),
            punto.getColumna()
        );
    }

    private static RecuadroDTO toRecuadroDTO(Recuadro recuadro) {
        return new RecuadroDTO(
            recuadro.getFila(),
            recuadro.getColumna()
        );
    }
    
    public static SeleccionDTO toSeleccionDTO(
        PuntoSeleccionado seleccion,
        Color color) {

        if (seleccion == null) {
            return null;
        }

        return new SeleccionDTO(
            toPuntoDTO(seleccion.getPutnoJugado()),
            seleccion.getJugadasDsiponibles()
                .stream()
                .map(DTOMapper::toPuntoDTO)
                .toList(),
            color
        );
    }
    
    public static List<JugadorLobbyDTO> toJugadoresLobbyDTO(
        List<Jugador> jugadores,
        String nombreJugadorLocal,
        EsquemaColores esquema,
        Votacion votacion) {

        return jugadores.stream()
                .map(jugador -> new JugadorLobbyDTO(
                    jugador.getNombre(),
                    esquema.resolver(jugador.getColor()),
                    votacion.obtenerVoto(jugador) != null,
                    jugador.getNombre().equalsIgnoreCase(nombreJugadorLocal),
                    jugador.getAvatar()
                ))
                .toList();
    }
    
    
    public static List<JugadorFinalDTO> toJugadoresFinalDTO(
        List<Jugador> clasificacion,
        List<Jugador> ganadores,
        String nombreJugadorLocal,
        EsquemaColores esquema) {

        List<JugadorFinalDTO> jugadoresDTO = new ArrayList<>();

        for (Jugador jugador : clasificacion) {

            // Los empatados comparten puesto y el siguiente salta los lugares ocupados
            int puesto = 1;
            for (Jugador otro : clasificacion) {
                if (otro.getPuntaje() > jugador.getPuntaje()) {
                    puesto++;
                }
            }

            jugadoresDTO.add(new JugadorFinalDTO(
                jugador.getNombre(),
                jugador.getPuntaje(),
                puesto,
                ganadores.contains(jugador),
                jugador.getNombre().equalsIgnoreCase(nombreJugadorLocal),
                esquema.resolver(jugador.getColor()),
                jugador.getAvatar()
            ));
        }

        return jugadoresDTO;
    }
    
    public static VotacionDTO toVotacionDTO(
        ResultadoVotacion resultado,
        Votacion votacion,
        Jugador jugadorLocal) {

        boolean yaVoto = jugadorLocal != null && votacion.obtenerVoto(jugadorLocal) != null;

        return new VotacionDTO(
            resultado,
            votacion.contarVotos(Voto.FAVOR),
            votacion.contarVotos(Voto.EN_CONTRA),
            yaVoto
        );
    }
    
}
