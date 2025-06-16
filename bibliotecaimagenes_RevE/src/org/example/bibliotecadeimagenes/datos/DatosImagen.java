package org.example.bibliotecadeimagenes.datos;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.example.bibliotecadeimagenes.carpetas.RecopilarDatos;

public class DatosImagen {
	private String ruta; // carpeta donde está almacenada la imagen
	private String nombreImagen; // nombre del fichero de la imagen sin la ruta
	private int ancho; // ancho de la imagen en pixels
	private int alto;  // alto de la imagen en pixels
	private Date fecha; // fecha de creación de la imagen
	private double[] gpsPosicion; // latitude, longitude;
	
	public DatosImagen() {
		
	}

	public DatosImagen(String ruta, String nombreImagen, int ancho, int alto, Date fecha, double[] gpsPosicion) {
		super();
		this.ruta = ruta;
		this.nombreImagen = nombreImagen;
		this.ancho = ancho;
		this.alto = alto;
		this.fecha = fecha;
		this.gpsPosicion = gpsPosicion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}
	
    // Método para calcular el área de la imagen
    public int getArea() {
        return ancho * alto;
    }


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double[] getGpsPosicion() {
		return gpsPosicion;
	}
	
	public String getPosicionGPS() {
		if (this.gpsPosicion == null)
			return " ";
		String pos1 = String.format("%.4f", gpsPosicion[0]);
		String pos2 = String.format("%.4f", gpsPosicion[1]);
		return pos1 + " " + pos2;
	}

	public void setGpsPosicion(double[] gpsPosicion) {
		this.gpsPosicion = gpsPosicion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreImagen);
	}

	@Override
	public boolean equals(Object obj) {
		// dos imágenes son iguales si tienen el mismo nombre de fichero y la misma ruta
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosImagen other = (DatosImagen) obj;
		return other.getRuta().equals(getRuta()) && other.getNombreImagen().equals(getNombreImagen());
	}
	
    @Override
	public String toString() {
		return nombreImagen + ", AnchoxAlto: " + ancho + " x " + alto + ", fecha=" + fecha
				+ ", gpsPosicion=" + Arrays.toString(gpsPosicion) + "]";
	}

	// Método para ordenar por fecha ascendente
    public static void ordenarPorFechaAscendente(List<DatosImagen> listaImagenes) {
        System.out.println("Ordenando por fecha ascendente!");
    	listaImagenes.sort(Comparator.comparing(DatosImagen::getFecha));
    	System.out.println("Ordenadas!");
    }

    // Método para ordenar por fecha descendente
    public static void ordenarPorFechaDescendente(List<DatosImagen> listaImagenes) {
        listaImagenes.sort(Comparator.comparing(DatosImagen::getFecha).reversed());
    }
    
    // Método para ordenar por ancho ascendente
    public static void ordenarPorAnchoAscendente(List<DatosImagen> listaImagenes) {
        listaImagenes.sort(Comparator.comparing(DatosImagen::getAncho));
    }
    
    // Método para ordenar por alto ascendente
    public static void ordenarPorAltoAscendente(List<DatosImagen> listaImagenes) {
        listaImagenes.sort(Comparator.comparing(DatosImagen::getAlto));
    }
    
    // Método para ordenar por area descente
    public static void ordenarPorAreaDescendente(List<DatosImagen> listaImagenes) {
        listaImagenes.sort(Comparator.comparing(DatosImagen::getArea).reversed());
    }
    
    // Función para filtrar imágenes por ancho mayor o igual a un valor dado
    public static List<DatosImagen> filtrarPorAncho(List<DatosImagen> lista, int anchoMinimo) {
        return lista.stream()
                .filter(imagen -> imagen.getAncho() >= anchoMinimo)
                .collect(Collectors.toList());
    }

    // Función para filtrar imágenes por alto mayor o igual a un valor dado
    public static List<DatosImagen> filtrarPorAlto(List<DatosImagen> lista, int altoMinimo) {
        return lista.stream()
                .filter(imagen -> imagen.getAlto() >= altoMinimo)
                .collect(Collectors.toList());
    }

    // Función para filtrar imágenes por fecha de creación
    public static List<DatosImagen> filtrarPorFecha(List<DatosImagen> lista, Date fecha) {
        return lista.stream()
                .filter(imagen -> imagen.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    public static List<DatosImagen> filtrarPorRangoDeFechas(List<DatosImagen> imagenes, Date fechaInicio, Date fechaFin) {
        List<DatosImagen> resultado = new ArrayList<>();
        for (DatosImagen imagen : imagenes) {
            Date fechaImagen = imagen.getFecha();
            if (fechaImagen != null && fechaImagen.after(fechaInicio) && fechaImagen.before(fechaFin)) {
                resultado.add(imagen);
            }
        }
        return resultado;
    }
	
    public static void main(String[] args) {
    	// Creamos lista con imágenes recorriendo recursivamente un directorio
        String directorio = "D:/carpetas_con_imagenes";
        List<DatosImagen> listaImagenes = new ArrayList<>();
        RecopilarDatos.recopilarDatosImagenes(new File(directorio), listaImagenes);

    	

    	// Ordenar por fecha ascendente
    	DatosImagen.ordenarPorFechaAscendente(listaImagenes);

    	// Ordenar por fecha descendente
    	//DatosImagen.ordenarPorFechaDescendente(listaImagenes);
    	
    	// Ordenar por ancho ascendente
    	// DatosImagen.ordenarPorAnchoAscendente(listaImagenes);

    	// Ordenar por alto ascendente
    	// DatosImagen.ordenarPorAltoAscendente(listaImagenes);
        
        // Ordenar por area descendente
    	// DatosImagen.ordenarPorAreaDescendente(listaImagenes);
        
        // Filtrar imágenes por ancho mayor o igual a 500 pixels
        //List<DatosImagen> imagenesAnchoMayor3000 = DatosImagen.filtrarPorAncho(listaImagenes, 3000);

        // Filtrar imágenes creadas en una fecha concreta
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//        Date fechaConcreta = null;
//		try {
//			fechaConcreta = formato.parse("08/08/1984");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        List<DatosImagen> imagenesFechaConcreta = DatosImagen.filtrarPorFecha(listaImagenes, fechaConcreta);
        
        // Definir el rango de fechas
        Calendar calInicio = new GregorianCalendar(1984, Calendar.JANUARY, 1);
        Calendar calFin = new GregorianCalendar(1984, Calendar.DECEMBER, 31);
        
        // Obtener la fechas del rango
        Date fechaInicio = calInicio.getTime();
        Date fechaFin = calFin.getTime();
        
        // Filtrar las imágenes por rango de fechas
        List<DatosImagen> imagenesFiltradas = filtrarPorRangoDeFechas(listaImagenes, fechaInicio, fechaFin);
        

        for (DatosImagen dato : listaImagenes) {
    		System.out.println(dato);    		
    	}
        
        System.out.println("\n\n");
        
        for (DatosImagen dato : imagenesFiltradas) {
    		System.out.println(dato);    		
    	}
	}
}
