package org.example.bibliotecadeimagenes.carpetas;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.imaging.Imaging;
//import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.example.bibliotecadeimagenes.datos.DatosImagen;

public class RecopilarDatos {

    public static void recopilarDatosImagenes(File carpeta, List<DatosImagen> lista) {
        if (carpeta.isDirectory()) { // hacemos búsqueda en profundidad
            File[] archivos = carpeta.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) { // vamos a través de los ficheros
                    if (archivo.isDirectory()) {
                        recopilarDatosImagenes(archivo, lista); // Llamada recursiva para las subcarpetas
                    } else {
                        if (esImagen(archivo)) {
                            try {
                                DatosImagen datos = obtenerDatosImagen(archivo);
                                lista.add(datos);
                            } catch (IOException /*| ImageReadException*/ e) {
                                System.out.println("Error al leer los metadatos de la imagen: " + archivo.getAbsolutePath());
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean esImagen(File archivo) {
        String nombreArchivo = archivo.getName().toLowerCase();
        return nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".jpeg") || nombreArchivo.endsWith(".png")
                || nombreArchivo.endsWith(".gif") || nombreArchivo.endsWith(".bmp");
    }

    public static DatosImagen obtenerDatosImagen(File archivo) throws IOException/*, ImageReadException*/ {
        String ruta = archivo.getParent();
        String nombreImagen = archivo.getName();
        int ancho = 0;
        int alto = 0;
        double[] gpsPosicion = null;
        Date fecha = null;
        JpegImageMetadata metadata = (JpegImageMetadata) Imaging.getMetadata(archivo);
        if (metadata != null) {
            TiffImageMetadata exif = metadata.getExif();
            if (exif != null) {
            	if (exif.findField(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH) != null) {
                    ancho = exif.findField(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH).getIntValue();
                }
                if (exif.findField(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH) != null) {
                    alto = exif.findField(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH).getIntValue();
                }
                // Obtener la posición GPS si está disponible
                if (exif.getGpsInfo() != null) {
                    gpsPosicion = new double[]{exif.getGpsInfo().getLatitudeAsDegreesNorth(), exif.getGpsInfo().getLongitudeAsDegreesEast()};
                }
                // Obtener la fecha de la imagen si está disponible
                // ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL
                if (exif.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL) != null) {
                    TiffField fechaField = exif.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);

                    Object fieldValue = fechaField.getValue();
                    if (fieldValue instanceof String) {
                        String fechaStr = (String) fieldValue;
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                            fecha = dateFormat.parse(fechaStr);
                        } catch (ParseException e) {
                            // Maneja la excepción si hay un error al analizar la fecha
                            e.printStackTrace();
                        }
                    } else {
                        // Maneja el caso si el valor del campo no es una cadena
                        System.out.println("El valor del campo no es una cadena.");
                    }
                    
                }
            }
        }
        return new DatosImagen(ruta, nombreImagen, ancho, alto, fecha, gpsPosicion);
    }

    public static void main(String[] args) {
        String directorio = "D:/carpetas_con_imagenes";
        List<DatosImagen> datosImagenes = new ArrayList<>();
        recopilarDatosImagenes(new File(directorio), datosImagenes);
        System.out.println("Fin de recolección de datos.");
        // Aquí puedes usar la lista datosImagenes como necesites
    }


}