/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Dominio.Avatar;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author luisb
 */
public final class RecursosAvatar {
 
    private static final String CARPETA = "/avatares/";
    private static final String EXTENSION = ".png";
 
    private record Clave(Avatar avatar, int tamano) { }
 
    private static final Map<Avatar, BufferedImage> ORIGINALES = new EnumMap<>(Avatar.class);
    private static final Map<Clave, ImageIcon> ICONOS = new HashMap<>();
 
    private RecursosAvatar() { }
 
    /** Ruta de classpath del recurso de un avatar. Locale.ROOT evita el bug de "I" en equipos con idioma turco. */
    static String ruta(Avatar avatar) {
        return CARPETA + avatar.name().toLowerCase(Locale.ROOT) + EXTENSION;
    }
 
    /** Opcional: llamar al arrancar para que la primera ventana no pague la carga. */
    public static void precargar() {
        for (Avatar avatar : Avatar.values()) {
            original(avatar);
        }
    }
 
    /** Icono cuadrado del avatar en el tamaño pedido (px). Seguro para llamar muchas veces. */
    public static ImageIcon icono(Avatar avatar, int tamano) {
        if (tamano <= 0) {
            throw new IllegalArgumentException("Tamaño inválido: " + tamano);
        }
        Avatar a = (avatar != null) ? avatar : Avatar.values()[0];
        return ICONOS.computeIfAbsent(new Clave(a, tamano),
                k -> new ImageIcon(escalar(original(k.avatar()), k.tamano())));
    }
 
    /** Variante atenuada, p. ej. para avatares ya ocupados en el registro. */
    public static ImageIcon iconoDeshabilitado(Avatar avatar, int tamano) {
        return new ImageIcon(javax.swing.GrayFilter.createDisabledImage(icono(avatar, tamano).getImage()));
    }
 
    // ------------------------------------------------------------------ interno
 
    private static BufferedImage original(Avatar avatar) {
        return ORIGINALES.computeIfAbsent(avatar, RecursosAvatar::cargar);
    }
 
    private static BufferedImage cargar(Avatar avatar) {
        String ruta = ruta(avatar);
        try (InputStream in = RecursosAvatar.class.getResourceAsStream(ruta)) {
            if (in == null) {
                System.err.println("[RecursosAvatar] No se encontró " + ruta + "; se usa imagen por defecto.");
            } else {
                BufferedImage img = ImageIO.read(in);
                if (img != null) {
                    return img;
                }
                System.err.println("[RecursosAvatar] Formato no soportado en " + ruta + "; se usa imagen por defecto.");
            }
        } catch (IOException ex) {
            System.err.println("[RecursosAvatar] Error leyendo " + ruta + ": " + ex.getMessage());
        }
        return generarPorDefecto(avatar);
    }
 
    /** Escala con interpolación bicúbica. Conviene entregar los PNG a ~2x del tamaño máximo mostrado (p. ej. 128 px). */
    private static BufferedImage escalar(BufferedImage origen, int tamano) {
        BufferedImage destino = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = destino.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(origen, 0, 0, tamano, tamano, null);
        g.dispose();
        return destino;
    }
 
    /** Fallback: círculo gris con la inicial del avatar (128 px, se escala como cualquier otro original). */
    private static BufferedImage generarPorDefecto(Avatar avatar) {
        int t = 128;
        BufferedImage img = new BufferedImage(t, t, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(new Color(203, 213, 225));
        g.fillOval(0, 0, t, t);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("SansSerif", Font.BOLD, 64));
        String inicial = avatar.name().substring(0, 1);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(inicial, (t - fm.stringWidth(inicial)) / 2, (t - fm.getHeight()) / 2 + fm.getAscent());
        g.dispose();
        return img;
    }
}

