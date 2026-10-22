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
	private JComboBox comboBox;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 437, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 129, 382, 232);
		scrollPane.setViewportView(textArea); 
		contentPane.add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(161, 25, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"DNI", "Nro Matricula"}));
		comboBox.setBounds(32, 24, 108, 22);
		contentPane.add(comboBox);
		
		// Evento del Botón CONSULTA (Búsqueda Directa en alumnos.txt)
		JButton btnNewButton = new JButton("CONSULTA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String criterioBusqueda = comboBox.getSelectedItem().toString();
				String inputTexto = textField.getText().trim();
				
				// 1. VALIDACIONES DE ENTRADA (Cambiado a 8 dígitos para DNI)
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

				// 2. PROCESO DE LECTURA DIRECTA DEL ARCHIVO CORRECTO
				textArea.setText(""); 
				String nombreArchivoMatriculas = "alumnos.txt"; // 🌟 Nombre corregido a alumnos.txt
				boolean encontroRegistro = false;

				try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivoMatriculas))) {
					String linea;
					
					while ((linea = br.readLine()) != null) {
						String[] campos = linea.split(";");
						
						// Verificamos que la línea tenga las columnas suficientes
						if (campos.length >= 7) {
							String nroMatricula   = campos[0].trim();
							String fechaOperacion = campos[1].trim();
							String horaOperacion  = campos[2].trim();
							String codigoAlumno   = campos[3].trim();
							String nombreEstudiante = campos[4].trim();
							String dniEstudiante   = campos[5].trim();
							String apoderado       = campos[6].trim();
							
							// Manejo dinámico para campos opcionales finales (Celular, Grado)
							String celular = (campos.length > 7) ? campos[7].trim() : "No registrado";
							String grado   = (campos.length > 8) ? campos[8].trim() : "No registrado";

							boolean cumpleFiltro = false;

							// Evaluamos la búsqueda según el ComboBox (DNI busca por DNI real o Código de Alumno)
							if (criterioBusqueda.equals("DNI")) {
								if (dniEstudiante.equals(inputTexto) || codigoAlumno.equals(inputTexto)) {
									cumpleFiltro = true;
								}
							} else if (criterioBusqueda.equals("Nro Matricula") && nroMatricula.equals(inputTexto)) {
								cumpleFiltro = true;
							}

							// 3. SI COINCIDE, PINTA LA INFORMACIÓN FORMATEADA
							if (cumpleFiltro) {
								encontroRegistro = true;
								StringBuilder sb = new StringBuilder();
								sb.append("============= FICHA DE CONSULTA =============\n");
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
								sb.append("Fecha de Registro:   ").append(fechaOperacion).append("\n");
								sb.append("Hora de Registro:    ").append(horaOperacion).append("\n");
								sb.append("=============================================");
								
								textArea.setText(sb.toString());
								break; // Detiene el bucle al encontrar el alumno
							}
						}
					}

					if (!encontroRegistro) {
						javax.swing.JOptionPane.showMessageDialog(null, 
							"El estudiante con el identificador '" + inputTexto + "' no fue encontrado.", 
							"Alumno No Encontrado", 
							javax.swing.JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (IOException ex) {
					javax.swing.JOptionPane.showMessageDialog(null, 
						"Error crítico al intentar abrir el archivo: " + ex.getMessage(), 
						"Error de Archivo", 
						javax.swing.JOptionPane.ERROR_MESSAGE);
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
				comboBox.setSelectedIndex(0);
				comboBox.setSelectedIndex(0);
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
