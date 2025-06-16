package org.example.bibliotecadeimagenes.carpetas;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FolderCreatorTest {

    @TempDir
    Path tempDir;

    @Test
    void creaTresNivelesConMaxDosCarpetas() {
        String base = tempDir.resolve("base").toString();
        // nivo=3, maxCarpPorNivel=2 → niveles 3→2→1
        FolderCreator.crearCarpetas(base, 3, 2);

        File nivel3 = new File(base);
        assertTrue(nivel3.exists() && nivel3.isDirectory());

        // Esperamos entre 1 y 2 subcarpetas en cada nivel
        File[] sub1 = nivel3.listFiles(File::isDirectory);
        assertNotNull(sub1);
        assertTrue(sub1.length >= 1 && sub1.length <= 2);

        // Para cada subcarpeta, comprobamos que haya a su vez entre 1 y 2 subcarpetas
        Arrays.stream(sub1).forEach(f -> {
            File[] sub2 = f.listFiles(File::isDirectory);
            assertNotNull(sub2);
            assertTrue(sub2.length >= 1 && sub2.length <= 2);
        });
    }

	private void assertTrue(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void assertNotNull(File[] sub2) {
		// TODO Auto-generated method stub
		
	}
}
