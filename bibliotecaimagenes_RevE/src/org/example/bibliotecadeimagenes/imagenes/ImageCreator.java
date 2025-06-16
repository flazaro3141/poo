package org.example.bibliotecadeimagenes.imagenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class ImageCreator {
    public static void crearImagen(String ruta, int ancho, int alto) {
        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagen.createGraphics();

        // Dibuja el fondo con un color aleatorio
        Random random = new Random();
        Color fondoColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        g2d.setColor(fondoColor);
        g2d.fillRect(0, 0, ancho, alto);

        // Dibuja formas aleatorias
        int numFormas = 10 + random.nextInt(20); // Número aleatorio de formas
        for (int i = 0; i < numFormas; i++) {
            // Color aleatorio para la forma
            Color formaColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g2d.setColor(formaColor);

            int forma = random.nextInt(3); // Selecciona aleatoriamente entre línea, cuadrado y círculo
            int x = random.nextInt(ancho); // Posición x aleatoria
            int y = random.nextInt(alto); // Posición y aleatoria
            int size = 20 + random.nextInt(100); // Tamaño aleatorio

            switch (forma) {
                case 0: // Dibuja una línea aleatoria
                    int x2 = random.nextInt(ancho);
                    int y2 = random.nextInt(alto);
                    g2d.drawLine(x, y, x2, y2);
                    break;
                case 1: // Dibuja un cuadrado aleatorio
                    g2d.fillRect(x, y, size, size);
                    break;
                case 2: // Dibuja un círculo aleatorio
                    g2d.fillOval(x, y, size, size);
                    break;
            }
        }

        g2d.dispose(); // Libera recursos

        try {
            ImageIO.write(imagen, "JPEG", new File(ruta));
            System.out.println("Imagen creada exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al crear la imagen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        crearImagen("C:\\Users\\flaza\\Documents\\image.jpg", 800, 600);
    }
}
