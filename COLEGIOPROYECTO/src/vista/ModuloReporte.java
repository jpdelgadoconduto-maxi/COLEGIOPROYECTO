package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import Control.Alumno;
import Control.ArregloAlumno;

/**
 * REPORTES - ALUMNO
 * Lee los datos reales de alumnos.txt (a través de ArregloAlumno) cada vez que
 * se genera un reporte, así siempre muestra la información actualizada.
 */
public class ModuloReporte extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String LINEA = "==============================================================\n";
    private static final String SEP   = "--------------------------------------------------------------\n";

    private JTextArea txtReporte;
    private JTextField txtBuscar;
    private JButton btnIndividual, btnMatricula, btnApoderados, btnMovimiento;
    private JButton btnGeneral, btnImprimir, btnLimpiar, btnCerrar;

    private ModuloInicio ventanaInicio;

    public ModuloReporte(ModuloInicio inicio) {
        this.ventanaInicio = inicio;
        setTitle("Módulo de Reportes - Colegio Miguel Grau");
        setSize(900, 650);
        setLocationRelativeTo(null);
        // La X de la ventana vuelve al menú de inicio
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { cerrar(); }
        });
        inicializar();
    }

    private void inicializar() {
        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("SISTEMA DE REPORTES DE MATRÍCULA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(25, 55, 95));

        txtBuscar = new JTextField();
        txtBuscar.setToolTipText("Código del alumno o DNI (8 dígitos)");
        // Enter en el cuadro de búsqueda genera el reporte individual
        txtBuscar.addActionListener(e -> reporteIndividual());

        JPanel panelBusqueda = new JPanel(new BorderLayout(8, 8));
        panelBusqueda.add(new JLabel("Código o DNI:"), BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        JPanel encabezado = new JPanel(new BorderLayout(5, 10));
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(panelBusqueda, BorderLayout.SOUTH);

        txtReporte = new JTextArea();
        txtReporte.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtReporte.setEditable(false);
        txtReporte.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(txtReporte);
        scroll.setBorder(BorderFactory.createTitledBorder("Contenido del reporte"));

        btnIndividual = new JButton("Reporte individual");
        btnMatricula = new JButton("Matrícula");
        btnApoderados = new JButton("Apoderados");
        btnMovimiento = new JButton("Movimientos");
        btnGeneral = new JButton("Reporte general");
        btnImprimir = new JButton("Imprimir");
        btnLimpiar = new JButton("Limpiar");
        btnCerrar = new JButton("CERRAR");

        JPanel botones = new JPanel(new GridLayout(0, 2, 8, 8));
        botones.add(btnIndividual);
        botones.add(btnMatricula);
        botones.add(btnApoderados);
        botones.add(btnMovimiento);
        botones.add(btnGeneral);
        botones.add(btnImprimir);
        botones.add(btnLimpiar);
        botones.add(btnCerrar);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(scroll, BorderLayout.CENTER);
        principal.add(botones, BorderLayout.SOUTH);
        setContentPane(principal);

        btnIndividual.addActionListener(e -> reporteIndividual());
        btnMatricula.addActionListener(e -> reporteMatricula());
        btnApoderados.addActionListener(e -> reporteApoderados());
        btnMovimiento.addActionListener(e -> reporteMovimiento());
        btnGeneral.addActionListener(e -> reporteGeneral());
        btnImprimir.addActionListener(e -> imprimirReporte());
        btnLimpiar.addActionListener(e -> { txtReporte.setText(""); txtBuscar.setText(""); txtBuscar.requestFocus(); });
        btnCerrar.addActionListener(e -> cerrar());

        // Al abrir se muestra el reporte general
        reporteGeneral();
    }

    // ---------- Utilidades ----------

    private void cerrar() {
        if (ventanaInicio != null) ventanaInicio.setVisible(true);
        dispose();
    }

    // Lee alumnos.txt en este momento (siempre datos actualizados)
    private ArregloAlumno cargarDatos() {
        return new ArregloAlumno();
    }

    // Busca por código o por DNI. Devuelve null (y avisa) si no existe.
    private Alumno buscarAlumno(ArregloAlumno arreglo) {
        String busqueda = txtBuscar.getText().trim();
        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Ingrese el código o el DNI del alumno.",
                "Dato requerido", JOptionPane.WARNING_MESSAGE);
            txtBuscar.requestFocus();
            return null;
        }
        Alumno a = arreglo.buscarCodigo(busqueda);
        if (a == null) a = arreglo.buscarDni(busqueda);
        if (a == null) {
            JOptionPane.showMessageDialog(this,
                "No se encontró un alumno con ese código o DNI.",
                "Alumno no encontrado", JOptionPane.WARNING_MESSAGE);
            txtBuscar.requestFocus();
            txtBuscar.selectAll();
        }
        return a;
    }

    // Busca en alumnos.txt la línea de matrícula del alumno (N°;fecha;hora;...)
    // Devuelve null si el alumno no tiene matrícula registrada.
    private String[] buscarLineaMatricula(Alumno a) {
        File f = new File("alumnos.txt");
        if (!f.exists()) return null;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.matches("\\d+;.*")) {
                    String[] p = linea.split(";", -1);
                    if (p.length >= 9 && p[3].trim().equals(a.getDni())) return p;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String vacio(String s) {
        return (s == null || s.trim().isEmpty()) ? "(sin dato)" : s.trim();
    }

    private String cortar(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 1) + ".";
    }

    private void mostrar(StringBuilder r) {
        txtReporte.setText(r.toString());
        txtReporte.setCaretPosition(0);
    }

    // ---------- Reportes ----------

    private void reporteIndividual() {
        ArregloAlumno arreglo = cargarDatos();
        Alumno a = buscarAlumno(arreglo);
        if (a == null) return;

        StringBuilder r = new StringBuilder();
        r.append(LINEA);
        r.append("          REPORTE INDIVIDUAL DEL ALUMNO\n");
        r.append("          COLEGIO MIGUEL GRAU SEMINARIO\n");
        r.append(LINEA).append("\n");
        r.append("Código:            ").append(a.getCodigo()).append("\n");
        r.append("DNI:               ").append(a.getDni()).append("\n");
        r.append("Nombres:           ").append(vacio(a.getNombre())).append("\n");
        r.append("Apellidos:         ").append(vacio(a.getApellidos())).append("\n");
        r.append("Edad:              ").append(a.getEdad() > 0 ? a.getEdad() + " años" : "(sin dato)").append("\n");
        r.append("Grado / Sección:   ").append(vacio(a.getGrado())).append("\n");
        r.append("Teléfono contacto: ").append(vacio(a.getCellularApoderado())).append("\n");
        r.append("Dirección:         ").append(vacio(a.getDireccion())).append("\n\n");
        r.append("Estado de matrícula: ").append(ArregloAlumno.textoEstado(a.getEstadoMatricula())).append("\n");
        mostrar(r);
    }

    private void reporteMatricula() {
        ArregloAlumno arreglo = cargarDatos();
        Alumno a = buscarAlumno(arreglo);
        if (a == null) return;

        String[] m = buscarLineaMatricula(a);

        StringBuilder r = new StringBuilder();
        r.append(LINEA);
        r.append("                REPORTE DE MATRÍCULA\n");
        r.append(LINEA).append("\n");
        r.append("Código del alumno:   ").append(a.getCodigo()).append("\n");
        r.append("DNI:                 ").append(a.getDni()).append("\n");
        r.append("Alumno:              ").append(a.getNombre()).append(" ").append(a.getApellidos()).append("\n");
        r.append("Grado / Sección:     ").append(vacio(a.getGrado())).append("\n");
        r.append("Estado de matrícula: ").append(ArregloAlumno.textoEstado(a.getEstadoMatricula())).append("\n\n");
        r.append(SEP);
        if (m != null) {
            r.append("N° de matrícula:     ").append(m[0].trim()).append("\n");
            r.append("Fecha de matrícula:  ").append(m[1].trim()).append("\n");
            r.append("Hora de matrícula:   ").append(m[2].trim()).append("\n");
        } else {
            r.append("El alumno no tiene una matrícula registrada.\n");
        }
        r.append("Fecha del movimiento: ").append(vacio(a.getFechaMovimiento())).append("\n");
        r.append("Responsable:          ").append(vacio(a.getResponsableMovimiento())).append("\n");
        mostrar(r);
    }

    private void reporteApoderados() {
        ArregloAlumno arreglo = cargarDatos();
        Alumno a = buscarAlumno(arreglo);
        if (a == null) return;

        StringBuilder r = new StringBuilder();
        r.append(LINEA);
        r.append("               REPORTE DE APODERADOS\n");
        r.append(LINEA).append("\n");
        r.append("Alumno:          ").append(a.getNombre()).append(" ").append(a.getApellidos()).append("\n");
        r.append("DNI del alumno:  ").append(a.getDni()).append("\n\n");
        r.append(SEP);
        r.append("Apoderado:       ").append(vacio(arreglo.getApoderado(a))).append("\n");
        r.append("DNI apoderado:   ").append(vacio(arreglo.getDniApoderado(a))).append("\n");
        r.append("Celular:         ").append(vacio(a.getCellularApoderado())).append("\n");
        r.append("Dirección:       ").append(vacio(a.getDireccion())).append("\n");
        mostrar(r);
    }

    private void reporteMovimiento() {
        ArregloAlumno arreglo = cargarDatos();
        Alumno a = buscarAlumno(arreglo);
        if (a == null) return;

        StringBuilder r = new StringBuilder();
        r.append(LINEA);
        r.append("               HISTORIAL DE MOVIMIENTO\n");
        r.append(LINEA).append("\n");
        r.append("Código:                      ").append(a.getCodigo()).append("\n");
        r.append("Alumno:                      ").append(a.getNombre()).append(" ").append(a.getApellidos()).append("\n");
        r.append("Estado:                      ").append(ArregloAlumno.textoEstado(a.getEstadoMatricula())).append("\n");
        r.append("Fecha del último movimiento: ").append(vacio(a.getFechaMovimiento())).append("\n");
        r.append("Responsable del movimiento:  ").append(vacio(a.getResponsableMovimiento())).append("\n");
        mostrar(r);
    }

    private void reporteGeneral() {
        ArregloAlumno arreglo = cargarDatos();
        int total = arreglo.tamano();

        StringBuilder r = new StringBuilder();
        r.append(LINEA);
        r.append("                  REPORTE GENERAL\n");
        r.append("          COLEGIO MIGUEL GRAU SEMINARIO\n");
        r.append(LINEA).append("\n");

        if (total == 0) {
            r.append("No hay alumnos registrados en alumnos.txt.\n");
            mostrar(r);
            return;
        }

        int registrados = 0, matriculados = 0;
        Map<String, Integer> porGrado = new TreeMap<>();

        r.append(String.format("%-13s %-9s %-26s %-8s %-15s%n",
            "CÓDIGO", "DNI", "ALUMNO", "GRADO", "ESTADO"));
        r.append(SEP);
        for (int i = 0; i < total; i++) {
            Alumno a = arreglo.obtener(i);
            String estado = a.getEstadoMatricula();
            if (ArregloAlumno.ESTADO_REGISTRADO.equals(estado)) registrados++;
            if (ArregloAlumno.ESTADO_MATRICULADO.equals(estado)) {
                matriculados++;
                String g = (a.getGrado() == null || a.getGrado().isEmpty()) ? "(sin grado)" : a.getGrado();
                porGrado.merge(g, 1, Integer::sum);
            }
            String nombreCompleto = a.getNombre() + " " + a.getApellidos();
            r.append(String.format("%-13s %-9s %-26s %-8s %-15s%n",
                a.getCodigo(), a.getDni(), cortar(nombreCompleto, 26),
                vacio(a.getGrado()).equals("(sin dato)") ? "-" : a.getGrado(),
                ArregloAlumno.textoEstado(estado)));
        }

        r.append(SEP);
        r.append("Total de alumnos:  ").append(total).append("\n");
        r.append("Registrados (0):   ").append(registrados).append("\n");
        r.append("Matriculados (1):  ").append(matriculados).append("\n\n");

        if (!porGrado.isEmpty()) {
            r.append("Matriculados por grado / sección:\n");
            for (Map.Entry<String, Integer> e : porGrado.entrySet()) {
                r.append(String.format("   %-12s %d%n", e.getKey(), e.getValue()));
            }
        }
        mostrar(r);
    }

    private void imprimirReporte() {
        if (txtReporte.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero genere un reporte.");
            return;
        }
        try {
            txtReporte.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Error al imprimir: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModuloReporte(null).setVisible(true));
    }
}
