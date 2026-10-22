package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel; 

public class ModuloConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private ModuloInicio ventanaInicio;
	private JComboBox<String> comboBox;

	/**
	 * Constructor de la ventana de consulta
	 */
	public ModuloConsulta(ModuloInicio inicio) {
		this.ventanaInicio = inicio; 
		initialize();
	}

	/**
	 * Inicializa los contenidos del frame.
	 */
	private void initialize() {
		// Al cerrar con la "X", liberamos memoria, pero controlamos el retorno a la ventana de inicio
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 437, 472);
		setTitle("Módulo de Consultas de Estudiantes");
		
		// Evento para controlar cuando el usuario cierra la ventana desde la "X" superior derecha
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (ventanaInicio != null) {
					ventanaInicio.setVisible(true); 
				}
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Etiquetas (JLabels) para dar contexto a la interfaz gráfica
		JLabel lblBuscarPor = new JLabel("Buscar por:");
		lblBuscarPor.setBounds(32, 8, 108, 14);
		contentPane.add(lblBuscarPor);
		
		JLabel lblIngreseDato = new JLabel("Ingrese el identificador:");
		lblIngreseDato.setBounds(161, 8, 120, 14);
		contentPane.add(lblIngreseDato);
		
		textArea = new JTextArea();
		textArea.setEditable(false); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 129, 382, 293); // Ajustado para un mejor campo visual
		scrollPane.setViewportView(textArea); 
		contentPane.add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(161, 25, 110, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"DNI", "Nro Matricula"}));
		comboBox.setBounds(32, 24, 108, 22);
		contentPane.add(comboBox);
		
		// Evento del Botón CONSULTA (Búsqueda en alumnos.txt y pendientes.txt de forma unificada)
		JButton btnNewButton = new JButton("CONSULTA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String criterioBusqueda = comboBox.getSelectedItem().toString();
				String inputTexto = textField.getText().trim();
				
				// 1. VALIDACIONES DE ENTRADA (Exactamente 8 dígitos para DNI)
				if (criterioBusqueda.equals("DNI")) {
					if (!inputTexto.matches("\\d{8}")) {
						javax.swing.JOptionPane.showMessageDialog(null, 
							"Error: El identificador debe contener exactamente 8 dígitos numéricos.", 
							"Validación de Datos", 
							javax.swing.JOptionPane.WARNING_MESSAGE);
						return;
					}
				} else if (criterioBusqueda.equals("Nro Matricula")) {
					if (inputTexto.isEmpty() || !inputTexto.matches("\\d+")) {
						javax.swing.JOptionPane.showMessageDialog(null, 
							"Error: Ingrese un Número de Matrícula válido (solo números).", 
							"Validación de Datos", 
							javax.swing.JOptionPane.WARNING_MESSAGE);
						return;
					}
				}

				// 2. PROCESO DE LECTURA SECUENCIAL EN AMBAS BASES DE DATOS (.TXT)
				textArea.setText(""); 
				boolean encontroRegistro = false;
				String[] archivosDestino = {"alumnos.txt", "pendientes.txt"};

				for (String nombreArchivo : archivosDestino) {
					if (encontroRegistro) break; // Si ya lo encontramos en el primer archivo, detenemos la búsqueda global

					try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
						String linea;
						
						while ((linea = br.readLine()) != null) {
							String[] campos = linea.split(";");
							
							if (campos.length >= 7) {
								String nroMatricula   = campos[0].trim();
								String fechaOperacion = campos[1].trim();
								String horaOperacion  = campos[2].trim();
								String codigoAlumno   = campos[3].trim();
								String nombreEstudiante = campos[4].trim();
								String dniEstudiante   = campos[5].trim();
								String apoderado       = campos[6].trim();
								
								// Campos opcionales al final del registro
								String celular = (campos.length > 7) ? campos[7].trim() : "No registrado";
								String grado   = (campos.length > 8) ? campos[8].trim() : "No registrado";

								boolean cumpleFiltro = false;

								if (criterioBusqueda.equals("DNI")) {
									if (dniEstudiante.equals(inputTexto) || codigoAlumno.equals(inputTexto)) {
										cumpleFiltro = true;
									}
								} else if (criterioBusqueda.equals("Nro Matricula") && nroMatricula.equals(inputTexto)) {
									cumpleFiltro = true;
								}

								// 3. SI COINCIDE, PINTA LA FICHA ESPECIFICANDO EL ESTADO
								if (cumpleFiltro) {
									encontroRegistro = true;
									
									// Determinamos visualmente el estado del alumno según el archivo de origen
									String estadoAlumno = nombreArchivo.equals("alumnos.txt") ? "MATRICULADO" : "PENDIENTE DE MATRÍCULA";
									
									StringBuilder sb = new StringBuilder();
									sb.append("============= FICHA DE CONSULTA =============\n");
									sb.append("ESTADO ACTUAL:       [").append(estadoAlumno).append("]\n");
									sb.append("---------------------------------------------\n");
									sb.append("Nro. Matrícula:      ").append(nroMatricula).append("\n");
									sb.append("Código Alumno:       ").append(codigoAlumno).append("\n");
									sb.append("DNI Estudiante:      ").append(dniEstudiante).append("\n");
									sb.append("Estudiante:          ").append(nombreEstudiante).append("\n");
									sb.append("Grado / Sección:     ").append(grado).append("\n");
									sb.append("---------------------------------------------\n");
									sb.append("DATOS DE APODERADO:\n");
									sb.append("Nombre Apoderado:    ").append(apoderado).append("\n");
									sb.append("Celular de Contacto: ").append(celular).append("\n");
									sb.append("---------------------------------------------\n");
									sb.append("REGISTRO DE OPERACIÓN:\n");
									sb.append("Origen de Base Datos:").append(nombreArchivo).append("\n");
									sb.append("Fecha de Registro:   ").append(fechaOperacion).append("\n");
									sb.append("Hora de Registro:    ").append(horaOperacion).append("\n");
									sb.append("=============================================");
									
									textArea.setText(sb.toString());
									break; // Rompe el bucle de líneas de este archivo
								}
							}
						}
					} catch (IOException ex) {
						javax.swing.JOptionPane.showMessageDialog(null, 
							"Error al leer el archivo '" + nombreArchivo + "': " + ex.getMessage(), 
							"Error de Archivo", 
							javax.swing.JOptionPane.ERROR_MESSAGE);
					}
				}

				// 4. NOTIFICACIÓN SI NO EXISTE EN NINGÚN DOCUMENTO
				if (!encontroRegistro) {
					javax.swing.JOptionPane.showMessageDialog(null, 
						"El estudiante con el identificador '" + inputTexto + "' no fue encontrado en el sistema.", 
						"Alumno No Encontrado", 
						javax.swing.JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(287, 22, 116, 23);
		contentPane.add(btnNewButton);
		
		// Evento del Botón LIMPIAR
		JButton btnNewButton_1 = new JButton("LIMPIAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea.setText("");
				comboBox.setSelectedIndex(0); // Línea duplicada eliminada correctamente
			}
		});
		btnNewButton_1.setBounds(287, 59, 116, 23);
		contentPane.add(btnNewButton_1);
		
		// Evento del Botón CERRAR
		JButton btnNewButton_2 = new JButton("CERRAR");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ventanaInicio != null) {
					ventanaInicio.setVisible(true); 
				}
				dispose(); 
			}
		});
		btnNewButton_2.setBounds(287, 95, 116, 23);
		contentPane.add(btnNewButton_2);
	}
}
