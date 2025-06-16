package org.example.bibliotecadeimagenes.datos;

//import org.apache.commons.imaging.ImageReadException;
//import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
//import org.apache.commons.imaging.formats.tiff.TiffOutputSet;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;


public class MetadataManipulation {
    // Genera una fecha aleatoria entre el 1 de enero de 1980 y hoy
    public static Date generateRandomDate() {
        // Establece la fecha mínima como el 1 de enero de 1980
        Calendar minDate = Calendar.getInstance();
        minDate.set(1980, 1, 1);

        // Obtiene la fecha actual
        Date currentDate = new Date();

        // Genera una fecha aleatoria entre la fecha mínima y la actual
        long randomMillis = minDate.getTimeInMillis() + (long) (Math.random() * (currentDate.getTime() - minDate.getTimeInMillis()));
        return new Date(randomMillis);
    }

    // Formatea la fecha en el formato esperado por el campo EXIF
    public static String formatExifDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        return sdf.format(date);
    }

	
	
	
	
    // Genera una posición GPS aleatoria dentro de un rango razonable
    public static double[] generateRandomCoordinates() {
        Random random = new Random();
        
        // Rango aproximado de latitud y longitud (ajusta según tus necesidades)
        double minLatitude = -90.0; // Mínimo valor de latitud
        double maxLatitude = 90.0;  // Máximo valor de latitud
        double minLongitude = -180.0; // Mínimo valor de longitud
        double maxLongitude = 180.0;  // Máximo valor de longitud
        
        // Genera la latitud y longitud aleatorias dentro del rango
        double latitude = minLatitude + (maxLatitude - minLatitude) * random.nextDouble();
        double longitude = minLongitude + (maxLongitude - minLongitude) * random.nextDouble();
        
        // Retorna un arreglo con la latitud y longitud generadas
        return new double[]{latitude, longitude};
    }

    // Función para manipular los metadatos de una imagen
    public static void editMetadata(File imageFile, File outputFile) throws IOException/*, ImageReadException, ImageWriteException*/ {
        // Lee los metadatos de la imagen
        JpegImageMetadata metadata = (JpegImageMetadata) Imaging.getMetadata(imageFile);

        // Crea un nuevo conjunto de metadatos EXIF si no existen
        TiffOutputSet outputSet;
        if (metadata != null) {
            outputSet = metadata.getExif().getOutputSet();
        } else {
            outputSet = new TiffOutputSet();
        }
        
        // Obtén las dimensiones de la imagen (ancho y alto)
        BufferedImage image = ImageIO.read(imageFile);
        int width = image.getWidth();
        int height = image.getHeight();


        // Generar fecha aleatoriamente
        Date randomDate = generateRandomDate();
        String exifDateTime = formatExifDateTime(randomDate);
        
        // Modifica los metadatos según sea necesario
        // Agrega la posición GPS (ejemplo: latitud y longitud)
        // Esto es solo un ejemplo, asegúrate de proporcionar valores reales de latitud y longitud
        double[] posicion = generateRandomCoordinates();
        //outputSet.setGpsInDegrees(40.7128, -74.0060);
        outputSet.setGpsInDegrees(posicion[0], posicion[1]);
        
        // Agrega el ancho de la imagen al conjunto de metadatos
        outputSet.getOrCreateExifDirectory().removeField(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);
        outputSet.getOrCreateExifDirectory().add(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH, (short) width);

        // Agrega el alto de la imagen al conjunto de metadatos
        outputSet.getOrCreateExifDirectory().removeField(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);
        outputSet.getOrCreateExifDirectory().add(ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH, (short) height);
        
        
        //double[] coordinates = generateRandomCoordinates();
        //outputSet.setGpsInDegrees(coordinates[0], coordinates[1]);

        // Agrega la fecha aleatoria a los metadatos
        //outputSet.getOrCreateExifDirectory().removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        //outputSet.getOrCreateExifDirectory().add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, randomDate.toString());

        
        // Modifica los metadatos según sea necesario
        // Por ejemplo, cambia la fecha de captura
        // Aquí se debe agregar el campo si no existe
        // Si el campo ya existe, se sobrescribirá con el nuevo valor
        outputSet.getOrCreateExifDirectory().removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        
        //outputSet.getOrCreateExifDirectory().add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, "2022:04:26 12:00:00");
        outputSet.getOrCreateExifDirectory().add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, exifDateTime);
        
        // Guarda los metadatos modificados en el archivo de salida
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            new ExifRewriter().updateExifMetadataLossless(imageFile, fos, outputSet);
        }
    }

    // Ejemplo de uso de la función editMetadata
    public static void main(String[] args) {
        try {
            File imageFile = new File("C:\\Users\\flaza\\Documents\\image.jpg");
            File outputFile = new File("C:\\Users\\flaza\\Documents\\imagen1.jpg");

            // Llama a la función editMetadata para manipular los metadatos de la imagen
            editMetadata(imageFile, outputFile);

            System.out.println("Metadatos de la imagen modificados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al manipular los metadatos de la imagen: " + e.getMessage());
        }
    }
}

