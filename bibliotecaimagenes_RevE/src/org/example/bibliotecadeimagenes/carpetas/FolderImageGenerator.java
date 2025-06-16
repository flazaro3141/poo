package org.example.bibliotecadeimagenes.carpetas;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.example.bibliotecadeimagenes.datos.MetadataManipulation;
import org.example.bibliotecadeimagenes.imagenes.ImageCreator;

public class FolderImageGenerator {

    public static void crearCarpetasConImagenes(String baseDir, int maxNiveles, int maxCarpetasPorNivel, int maxImagenesPorCarpeta) {
    	
        crearCarpetasRecursivo(baseDir, maxNiveles, maxCarpetasPorNivel, maxImagenesPorCarpeta);
    }

    public static void crearCarpetasConImagenes(File file, int maxNiveles, int maxCarpetasPorNivel, int maxImagenesPorCarpeta) {
    	String baseDir = file.getAbsolutePath();
        crearCarpetasRecursivo(baseDir, maxNiveles, maxCarpetasPorNivel, maxImagenesPorCarpeta);
    }

    private static void crearCarpetasRecursivo(String ruta, int nivelMaximo, int maxCarpetasPorNivel, int maxImagenesPorCarpeta) {
    			
    	if (nivelMaximo <= 0) return;

        File directorio = new File(ruta);
        directorio.mkdirs();  // Crea el directorio base si no existe

        // Crea imágenes en la carpeta actual
        crearImagenesEnCarpeta(ruta, maxImagenesPorCarpeta);

        // Crea subcarpetas recursivamente
        for (int i = 0; i < maxCarpetasPorNivel; i++) {
            String subDirectorio = ruta + File.separator + "Carpeta_" + nivelMaximo + "_" + (i + 1);
            crearCarpetasRecursivo(subDirectorio, nivelMaximo - 1, maxCarpetasPorNivel, maxImagenesPorCarpeta);
        }
    }

    private static void crearImagenesEnCarpeta(String carpeta, int cantidadImagenes) {
        // Genera el ancho y alto de la imagen en el rango de 100 a 4000 (inclusive)
        
        for (int i = 0; i < cantidadImagenes; i++) {
            int anchoImagen = (int) (Math.random() * (4001 - 100)) + 100;
            int altoImagen = (int) (Math.random() * (4001 - 100)) + 100;  
            // Genera un identificador único aleatorio para el nombre de la imagen
            String nombreRnd = UUID.randomUUID().toString();
            String rutaImagen = carpeta + File.separator + "imagen_" + nombreRnd + "_.jpg";
            String rutaImagen2 = carpeta + File.separator + "imagen_" + nombreRnd + ".jpg";
            ImageCreator.crearImagen(rutaImagen, anchoImagen, altoImagen);

            // Modificar los metadatos de la imagen
            try {
            	File file1 = new File(rutaImagen);
            	File file2 = new File(rutaImagen2);
                MetadataManipulation.editMetadata(file1, file2);
                file1.delete(); // borro el archivo sin metadata
                
                System.out.println("Metadatos modificados para: " + rutaImagen);
            } catch (IOException e) {
                System.out.println("Error al modificar los metadatos para: " + rutaImagen);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String baseDir = "D:/carpetas_con_imagenes"; // Directorio base donde se crearán las carpetas

        // Crea las carpetas y las imágenes en cada carpeta
        crearCarpetasConImagenes(baseDir, 4, 3, 5);
    }

}