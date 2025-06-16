package org.example.bibliotecadeimagenes.datos;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class DatosImagenTest {

    @Test
    void ordenarYFiltrar() throws InterruptedException {
        DatosImagen a = new DatosImagen("r", "a.jpg", 100, 200, new Date(1000), null);
        DatosImagen b = new DatosImagen("r", "b.jpg", 300, 100, new Date(2000), null);
        DatosImagen c = new DatosImagen("r", "c.jpg", 150, 150, new Date(1500), null);

        List<DatosImagen> lista = Arrays.asList(b, c, a);

        DatosImagen.ordenarPorFechaAscendente(lista);
        assertEquals("a.jpg", lista.get(0).getNombreImagen());
        assertEquals("b.jpg", lista.get(2).getNombreImagen());

        List<DatosImagen> anchoMin200 = DatosImagen.filtrarPorAncho(lista, 200);
        assertEquals(1, anchoMin200.size());
        assertEquals("b.jpg", anchoMin200.get(0).getNombreImagen());
    }

	private void assertEquals(int i, int size) {
		// TODO Auto-generated method stub
		
	}

	private void assertEquals(String string, String nombreImagen) {
		// TODO Auto-generated method stub
		
	}
}
