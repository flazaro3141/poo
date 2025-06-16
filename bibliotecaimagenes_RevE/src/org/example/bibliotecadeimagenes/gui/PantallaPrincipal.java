package org.example.bibliotecadeimagenes.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.example.bibliotecadeimagenes.carpetas.FolderImageGenerator;
import org.example.bibliotecadeimagenes.carpetas.RecopilarDatos;
import org.example.bibliotecadeimagenes.datos.DatosImagen;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PantallaPrincipal extends JFrame {
    private static List<DatosImagen> lista = new ArrayList<>();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PantallaPrincipal frame = new PantallaPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PantallaPrincipal() {
        // Configurar el marco
        setTitle("Gestor de Imágenes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear la barra de menús
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú Archivo
        JMenu mnArchivo = new JMenu("Archivo");
        menuBar.add(mnArchivo);

        JMenuItem mntmGenerarImagenes = new JMenuItem("Generar Imágenes");
        mnArchivo.add(mntmGenerarImagenes);

        JMenuItem mntmCargarImagenes = new JMenuItem("Cargar Imágenes");
        mnArchivo.add(mntmCargarImagenes);

        JMenuItem mntmSalir = new JMenuItem("Salir");
        mnArchivo.add(mntmSalir);

        // Acción de salir
        mntmSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Acción de cargar imágenes
        mntmCargarImagenes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear un JFileChooser para seleccionar directorios
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Aquí puedes manejar la carpeta seleccionada
                    // Cargar imágenes
                    RecopilarDatos.recopilarDatosImagenes(selectedFile, lista);

                    JOptionPane.showMessageDialog(null, "Datos cargados desde: " + selectedFile.getAbsolutePath());
                    // Mostrar datos en JTable
                    mostrarDatosEnTabla(lista);
                }
            }
        });

        // Opción Generar imágenes donde se debe leer la ruta de la carpeta donde irán las imágenes.
        mntmGenerarImagenes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Solicitar parámetros al usuario
                try {
                    int niveles = Integer.parseInt(JOptionPane.showInputDialog("Número de niveles:"));
                    int maxCarpetasPorNivel = Integer.parseInt(JOptionPane.showInputDialog("Máximo de carpetas por nivel:"));
                    int maxImagenesPorCarpeta = Integer.parseInt(JOptionPane.showInputDialog("Máximo de imágenes por carpeta:"));
                    // Crear un JFileChooser para seleccionar el directorio de destino
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedDirectory = fileChooser.getSelectedFile();
                        // Generar imágenes en la carpeta seleccionada
                        FolderImageGenerator.crearCarpetasConImagenes(selectedDirectory, niveles, maxCarpetasPorNivel, maxImagenesPorCarpeta);

                        JOptionPane.showMessageDialog(null, "Imágenes generadas en: " + selectedDirectory.getAbsolutePath());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce valores numéricos válidos.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Menú Ordenar
        JMenu mnOrdenar = new JMenu("Ordenar");
        menuBar.add(mnOrdenar);

        JMenuItem mntmPorFecha = new JMenuItem("Por Fecha Ascendente");
        mnOrdenar.add(mntmPorFecha);

        JMenuItem mntmPorFechaD = new JMenuItem("Por Fecha Descendente");
        mnOrdenar.add(mntmPorFechaD);

        JMenuItem mntmPorAncho = new JMenuItem("Por Ancho");
        mnOrdenar.add(mntmPorAncho);

        JMenuItem mntmPorAlto = new JMenuItem("Por Alto");
        mnOrdenar.add(mntmPorAlto);

        // Acción de ordenar por fecha ascendente
        mntmPorFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatosImagen.ordenarPorFechaAscendente(lista);
                actualizarDatosEnTabla(lista);
            }
        });

        // Acción de ordenar por fecha descendente
        mntmPorFechaD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatosImagen.ordenarPorFechaDescendente(lista);
                actualizarDatosEnTabla(lista);
            }
        });

        // Acción de ordenar por ancho ascendente
        mntmPorAncho.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatosImagen.ordenarPorAnchoAscendente(lista);
                actualizarDatosEnTabla(lista);
            }
        });

        // Acción de ordenar por alto ascendente
        mntmPorAlto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatosImagen.ordenarPorAltoAscendente(lista);
                actualizarDatosEnTabla(lista);
            }
        });

        // Menú Filtrar
        JMenu mnFiltrar = new JMenu("Filtrar");
        menuBar.add(mnFiltrar);

        JMenuItem mntmPorAnchoFiltro = new JMenuItem("Por Ancho");
        mnFiltrar.add(mntmPorAnchoFiltro);

        JMenuItem mntmPorAltoFiltro = new JMenuItem("Por Alto");
        mnFiltrar.add(mntmPorAltoFiltro);

        JMenuItem mntmPorRangoFechas = new JMenuItem("Por Rango de Fechas");
        mnFiltrar.add(mntmPorRangoFechas);

        // Acción de filtrar por ancho
        mntmPorAnchoFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int anchoMinimo = Integer.parseInt(JOptionPane.showInputDialog("Introduce el ancho mínimo:"));
                    List<DatosImagen> imagenesAnchoMayor = DatosImagen.filtrarPorAncho(lista, anchoMinimo);
                    actualizarDatosEnTabla(imagenesAnchoMayor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un valor numérico válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción de filtrar por alto
        mntmPorAltoFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int altoMinimo = Integer.parseInt(JOptionPane.showInputDialog("Introduce el alto mínimo:"));
                    List<DatosImagen> imagenesAltoMayor = DatosImagen.filtrarPorAlto(lista, altoMinimo);
                    actualizarDatosEnTabla(imagenesAltoMayor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un valor numérico válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción de filtrar por rango de fechas
        mntmPorRangoFechas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaInicioStr = JOptionPane.showInputDialog("Introduce la fecha de inicio (dd/MM/yyyy):");
                    String fechaFinStr = JOptionPane.showInputDialog("Introduce la fecha de fin (dd/MM/yyyy):");

                    Date fechaInicio = dateFormat.parse(fechaInicioStr);
                    Date fechaFin = dateFormat.parse(fechaFinStr);

                    List<DatosImagen> imagenesFiltradas = DatosImagen.filtrarPorRangoDeFechas(lista, fechaInicio, fechaFin);
                    actualizarDatosEnTabla(imagenesFiltradas);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce una fecha válida en el formato dd/MM/yyyy.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configurar el panel principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Inicializar la tabla y el JScrollPane
        tableModel = new DefaultTableModel(new String[] {"Nombre de la imagen", "Ancho", "Alto", "Fecha", "Posición de GPS"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar la edición de celdas
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Habilitar la selección de filas
        table.setColumnSelectionAllowed(false); // Deshabilitar la selección de columnas
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 760, 540);
        contentPane.add(scrollPane);

        // Añadir un MouseListener para detectar clics en las filas
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic
                    System.out.println("Doble clic");
                    int selectedRow = table.getSelectedRow();
                    System.out.println("Se detectó doble clic en fila " + selectedRow);
                    if (selectedRow != -1) {
                        DatosImagen imagenSeleccionada = lista.get(selectedRow);
                        mostrarImagenEnVentana(imagenSeleccionada);
                    }
                }
            }
        });
    }

    private void mostrarDatosEnTabla(List<DatosImagen> listaImagenes) {
        // Limpiar el modelo de la tabla
        tableModel.setRowCount(0);

        // Agregar los datos al modelo de la tabla
        for (DatosImagen imagen : listaImagenes) {
            Object[] rowData = {
                imagen.getNombreImagen(),
                imagen.getAncho(),
                imagen.getAlto(),
                imagen.getFecha(),
                imagen.getPosicionGPS()
            };
            if (!imagen.getPosicionGPS().equals(" "))
            	tableModel.addRow(rowData);
        }

        // Ajustar el ancho de las columnas
        ajustarAnchoColumnas();
    }

    private void actualizarDatosEnTabla(List<DatosImagen> listaImagenes) {
        mostrarDatosEnTabla(listaImagenes);
    }

    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(330); // Nombre de la imagen
        columnModel.getColumn(1).setPreferredWidth(50); // Ancho
        columnModel.getColumn(2).setPreferredWidth(50); // Alto
        columnModel.getColumn(3).setPreferredWidth(200); // Fecha
        columnModel.getColumn(4).setPreferredWidth(120); // Posición de GPS
    }

    private void mostrarImagenEnVentana(DatosImagen imagen) {
        // Cargar la imagen
        File imageFile = new File(imagen.getRuta() + "//" + imagen.getNombreImagen());
        System.out.println("Ruta: " + imagen.getRuta());
        if (!imageFile.exists()) {
            JOptionPane.showMessageDialog(null, "No se encontró la imagen: " + imagen.getRuta(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            URL imageUrl = imageFile.toURI().toURL();
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image originalImage = originalIcon.getImage();
            
            int originalWidth = originalIcon.getIconWidth();
            int originalHeight = originalIcon.getIconHeight();

            // Calcular el tamaño inicial del JFrame proporcional al tamaño de la imagen
            int initialWidth = originalWidth > 800 ? 800 : originalWidth;
            int initialHeight = (int) ((double) initialWidth / originalWidth * originalHeight);
            if (initialHeight > 600) {
                initialHeight = 600;
                initialWidth = (int) ((double) initialHeight / originalHeight * originalWidth);
            }

            JFrame imageFrame = new JFrame("Vista de Imagen");
            imageFrame.setSize(initialWidth, initialHeight);
            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            imageFrame.setLocationRelativeTo(null);

            // Crear un JPanel
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // Crear un JLabel para la imagen
            JLabel imageLabel = new JLabel();
            panel.add(imageLabel, BorderLayout.CENTER);

            // Añadir un listener para redimensionar la imagen cuando se cambie el tamaño de la ventana
            imageFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    int frameWidth = imageFrame.getWidth();
                    int frameHeight = imageFrame.getHeight();
                    double frameAspectRatio = (double) frameWidth / frameHeight;
                    double imageAspectRatio = (double) originalWidth / originalHeight;

                    int newWidth, newHeight;

                    if (frameAspectRatio > imageAspectRatio) {
                        // Window is wider than image aspect ratio
                        newHeight = frameHeight;
                        newWidth = (int) (newHeight * imageAspectRatio);
                    } else {
                        // Window is narrower than image aspect ratio
                        newWidth = frameWidth;
                        newHeight = (int) (newWidth / imageAspectRatio);
                    }

                    Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    imageLabel.setIcon(scaledIcon);
                    imageLabel.setHorizontalAlignment(JLabel.CENTER);
                    imageLabel.setVerticalAlignment(JLabel.CENTER);
                }
            });

            // Inicializar con la imagen escalada al tamaño inicial del JFrame
            Image scaledImage = originalImage.getScaledInstance(initialWidth, initialHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel.setIcon(scaledIcon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);

            // Añadir el JPanel al JFrame
            imageFrame.add(panel);

            // Mostrar el JFrame
            imageFrame.setVisible(true);
        } catch (MalformedURLException e) {
            System.err.println("URL malformada: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "URL malformada: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
