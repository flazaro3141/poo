package org.example.bibliotecadeimagenes.carpetas;

import java.io.File;
import java.util.Random;

public class FolderCreator {
    public static void crearCarpetas(String ruta, int nivelMaximo, int maxCarpetasPorNivel) {
        if (nivelMaximo <= 0) return;

        File directorio = new File(ruta);
        directorio.mkdirs();  // Crea el directorio base si no existe

        Random random = new Random();
        int numeroCarpetas = random.nextInt(maxCarpetasPorNivel) + 1;

        for (int i = 0; i < numeroCarpetas; i++) {
            String subDirectorio = ruta + File.separator + "Carpeta_" + (nivelMaximo-1) + "_" + (i+1);
            crearCarpetas(subDirectorio, nivelMaximo - 1, maxCarpetasPorNivel);
        }
    }
    
    public static void crearCarpetas(File directorio, int nivelMaximo, int maxCarpetasPorNivel) {
        directorio.mkdirs();  // Crea el directorio base si no existe
        String ruta = directorio.getAbsolutePath();
        
        Random random = new Random();
        int numeroCarpetas = random.nextInt(maxCarpetasPorNivel) + 1;

        for (int i = 0; i < numeroCarpetas; i++) {
            String subDirectorio = ruta + File.separator + "Carpeta_" + (nivelMaximo-1) + "_" + (i+1);
            crearCarpetas(subDirectorio, nivelMaximo - 1, maxCarpetasPorNivel);
        }
    }

    public static void main(String[] args) {
        crearCarpetas("D:/Imagenes", 4, 3);  // Ejemplo: Crear carpetas hasta 3 niveles de profundidad con hasta 5 carpetas por nivel
    }

}
