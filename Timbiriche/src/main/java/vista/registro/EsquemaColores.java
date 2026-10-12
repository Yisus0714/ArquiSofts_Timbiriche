
package vista.registro;

import java.awt.Color;

public enum EsquemaColores {

    CLASICO("Clásico", new Color[]{
            new Color(47, 101, 232),
            new Color(229, 45, 50),
            new Color(255, 133, 44),
            new Color(16, 152, 117)
    }),

    AQUA_POP("Aqua Pop", new Color[]{
            new Color(46, 171, 220),
            new Color(117, 203, 69),
            new Color(255, 145, 73),
            new Color(242, 82, 114)
    }),

    PACIFIC("Pacific", new Color[]{
            new Color(34, 159, 163),
            new Color(34, 100, 163),
            new Color(243, 107, 97),
            new Color(231, 191, 98)
    }),

    NEON("Neón", new Color[]{
            new Color(130, 49, 185),
            new Color(8, 172, 204),
            new Color(226, 56, 136),
            new Color(83, 177, 140)
    });

    private final String nombre;
    private final Color[] colores;

    EsquemaColores(String nombre, Color[] colores) {
        this.nombre = nombre;
        this.colores = colores.clone();
    }

    public String getNombre() {
        return nombre;
    }

    public Color[] getColores() {
        return colores.clone();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
