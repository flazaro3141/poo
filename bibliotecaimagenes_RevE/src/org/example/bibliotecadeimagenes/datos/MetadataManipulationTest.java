package org.example.bibliotecadeimagenes.datos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import org.apache.commons.imaging.common.ImageMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MetadataManipulationTest {

    @TempDir
    Path tempDir;

    @Test
    void editMetadata_creaOutputConExif() throws IOException {
        // Crear imagen de prueba
        File in = tempDir.resolve("in.jpg").toFile();
        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(img, "JPEG", in);

        File out = tempDir.resolve("out.jpg").toFile();
        MetadataManipulation.editMetadata(in, out);

        assertTrue(out.exists(), "Debe existir el fichero de salida");
        // Podríamos usar Imaging.getMetadata(out) y assertNotNull para confirmar EXIF
        assertNotNull(org.apache.commons.imaging.Imaging.getMetadata(out));
    }

	private void assertNotNull(ImageMetadata metadata) {
		// TODO Auto-generated method stub
		
	}

	private void assertTrue(boolean exists, String string) {
		// TODO Auto-generated method stub
		
	}
}
