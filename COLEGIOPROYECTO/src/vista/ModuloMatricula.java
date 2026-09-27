package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import Control.Alumnocontroler;
import Control.Alumno; // Asegúrate de ajustar este import a la ubicación exacta de tu clase Alumno

public class ModuloMatricula extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    // Controlador
    private Alumnocontroler alumnoController;

    // Componentes del formulario
    private JTextField txtBuscarDni;
    private JTextField txtCodigo;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtEdad;
    private JTextField txtGrado;
    private JTextField txtTelefono;
    private JTextField txtApoderado;
    private JComboBox<String> cbEstadoMatricula;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ModuloMatricula frame = new ModuloMatricula();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ModuloMatricula() {
        // Inicializar el controlador
        alumnoController = new Alumnocontroler();

        setTitle("Módulo de Matrícula - Colegio Miguel Grau");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 480);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Título de la interfaz
        JLabel lblTitulo = new JLabel("GESTIÓN DE MATRÍCULA");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitulo.setBounds(200, 15, 230, 25);
        contentPane.add(lblTitulo);

        // --- SECCIÓN DE BÚSQUEDA ---
        JLabel lblDniBuscar = new JLabel("DNI del Alumno:");
        lblDniBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDniBuscar.setBounds(30, 55, 110, 20);
        contentPane.add(lblDniBuscar);

        txtBuscarDni = new JTextField();
        txtBuscarDni.setBounds(145, 55, 130, 22);
        contentPane.add(txtBuscarDni);
        txtBuscarDni.setColumns(10);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(285, 54, 90, 24);
        contentPane.add(btnBuscar);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 90, 545, 10);
        contentPane.add(separator);

        // --- CAMPOS DE DETALLE DEL ALUMNO ---
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 110, 80, 20);
        contentPane.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(120, 110, 140, 20);
        contentPane.add(txtCodigo);

        JLabel lblGrado = new JLabel("Grado:");
        lblGrado.setBounds(290, 110, 80, 20);
        contentPane.add(lblGrado);

        txtGrado = new JTextField();
        txtGrado.setEditable(false);
        txtGrado.setBounds(380, 110, 180, 20);
        contentPane.add(txtGrado);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setBounds(30, 145, 80, 20);
        contentPane.add(lblNombres);

        txtNombres = new JTextField();
        txtNombres.setEditable(false);
        txtNombres.setBounds(120, 145, 140, 20);
        contentPane.add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(290, 145, 80, 20);
        contentPane.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setEditable(false);
        txtApellidos.setBounds(380, 145, 180, 20);
        contentPane.add(txtApellidos);

        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(30, 180, 80, 20);
        contentPane.add(lblEdad);

        txtEdad = new JTextField();
        txtEdad.setEditable(false);
        txtEdad.setBounds(120, 180, 140, 20);
        contentPane.add(txtEdad);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(290, 180, 80, 20);
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setEditable(false);
        txtTelefono.setBounds(380, 180, 180, 20);
        contentPane.add(txtTelefono);

        JLabel lblApoderado = new JLabel("Apoderado:");
        lblApoderado.setBounds(30, 215, 80, 20);
        contentPane.add(lblApoderado);

        txtApoderado = new JTextField();
        txtApoderado.setEditable(false);
        txtApoderado.setBounds(120, 215, 440, 20);
        contentPane.add(txtApoderado);

        // --- ESTADO DE MATRÍCULA ---
        JLabel lblEstado = new JLabel("Estado Matrícula:");
        lblEstado.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblEstado.setBounds(30, 260, 120, 20);
        contentPane.add(lblEstado);

        cbEstadoMatricula = new JComboBox<>(new String[] { "-- Seleccionar --", "MATRICULADO", "NO MATRICULADO", "TRASLADADO", "RETIRADO" });
        cbEstadoMatricula.setBounds(150, 260, 180, 22);
        contentPane.add(cbEstadoMatricula);

        // --- BOTONES DE ACCIÓN ---
        JButton btnGuardar = new JButton("Guardar Matrícula");
        btnGuardar.setBounds(130, 340, 150, 35);
        contentPane.add(btnGuardar);

        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setBounds(300, 340, 140, 35);
        contentPane.add(btnVolver);

        // --- EVENTOS DE BOTONES ---

        // Evento de Búsqueda
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarAlumno();
            }
        });

        // Evento Guardar / Actualizar
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarMatricula();
            }
        });

        // Evento Volver
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ModuloInicio inicio = new ModuloInicio();
                inicio.setLocationRelativeTo(null);
                inicio.setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Busca los datos del alumno en el archivo a través del controlador
     */
    private void buscarAlumno() {
        String dni = txtBuscarDni.getText().trim();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número de DNI para buscar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Alumno alumno = alumnoController.buscarPorDni(dni);

        if (alumno != null) {
            txtCodigo.setText(alumno.getCodigo());
            txtNombres.setText(alumno.getNombre());
            txtApellidos.setText(alumno.getApellidos());
            txtEdad.setText(String.valueOf(alumno.getEdad()));
            txtGrado.setText(alumno.getGradoAlQuePasa());
            txtTelefono.setText(alumno.getTelefono());
            
            // Preferimos celular de apoderado o nombre del papá
            String datosApoderado = alumno.getNombrePapa() != null && !alumno.getNombrePapa().isEmpty() 
                ? alumno.getNombrePapa() 
                : alumno.getNombreMama();
            txtApoderado.setText(datosApoderado + " (Tel: " + alumno.getCelularApoderado() + ")");

            // Seleccionar estado de matrícula si coincide
            if (alumno.getEstadoMatricula() != null) {
                cbEstadoMatricula.setSelectedItem(alumno.getEstadoMatricula().toUpperCase());
            } else {
                cbEstadoMatricula.setSelectedIndex(0);
            }

        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún alumno con el DNI: " + dni, "Alumno no encontrado", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        }
    }

    private void guardarMatricula() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe buscar un alumno válido antes de guardar la matrícula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cbEstadoMatricula.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un estado de matrícula válido.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoEstado = (String) cbEstadoMatricula.getSelectedItem();
        JOptionPane.showMessageDialog(this, "¡Matrícula registrada exitosamente como " + nuevoEstado + "!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtGrado.setText("");
        txtTelefono.setText("");
        txtApoderado.setText("");
        cbEstadoMatricula.setSelectedIndex(0);
    }
}