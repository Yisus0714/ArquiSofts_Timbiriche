/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Model;

import Dominio.ColorJugador;
import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author luisb
 */
public enum EsquemaColores {
    ORIGINAl(
        Color.decode("#2563EB"), // rojo
        Color.decode("#DC2626"), // azul oscuro
        Color.decode("#FF852D"), // verde azulado
        Color.decode("#059669")  // naranja
    ),
        AQUA_POP(
        Color.decode("#29ABE2"), // azul cielo
        Color.decode("#7BCF45"), // verde lima
        Color.decode("#FF8C42"), // naranja mandarina
        Color.decode("#F45B69")  // rosa sandía
    ),

    PACIFIC(
        Color.decode("#18A6A6"), // turquesa
        Color.decode("#145DA0"), // azul profundo
        Color.decode("#F26B5E"), // coral
        Color.decode("#E9C46A")  // amarillo arena
    ),

    NEON(
        Color.decode("#7B2CBF"), // violeta eléctrico
        Color.decode("#00B4D8"), // cian
        Color.decode("#E83E8C"), // fucsia
        Color.decode("#52B788")  // verde menta
    );

    private final Map<ColorJugador, Color> colores;

    EsquemaColores(Color c1, Color c2, Color c3, Color c4) {
        this.colores = new EnumMap<>(ColorJugador.class);
        colores.put(ColorJugador.COLOR_1, c1);
        colores.put(ColorJugador.COLOR_2, c2);
        colores.put(ColorJugador.COLOR_3, c3);
        colores.put(ColorJugador.COLOR_4, c4);
    }

    public Color resolver(ColorJugador colorLogico) {
        return colores.get(colorLogico);
    }
}