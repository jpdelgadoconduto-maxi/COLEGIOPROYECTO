package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;//permite buscar automatica por DNI
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;//permite volver a la ventana inicio al cerrar
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import clases.Matricula;

public class ModuloMatricula extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel, lblDniApoderado, lblNewLabel_2, lblNewLabel_3;
	private JLabel lblFecha, lblNewLabel_4, lblNombresApoderado, lblNewLabel_5, lblNewLabel_6, lblHora, lblNewLabel_1;
	private JTextField textMatricula, textDniAlumno, textDniApoderado, textTelefono;
	private JTextField textFecha, textNombresAlumno, textNombresApoderado, textVacantes, textHora;
	private JComboBox<String> comboBox;
	private JTextArea textArea;
	private JButton btnCerrar, btnLimpiar, btnAnular, btnMatricular;

	private ArrayList<Matricula> listaMatriculas = new ArrayList<>();
	private int vacantesDisponibles = 30;
	private JButton btnGuardar;
	private ModuloInicio moduloInicio;//me hara referencia a la ventana inicio

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuloMatricula frame = new ModuloMatricula(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModuloMatricula(ModuloInicio moduloInicio) {
		this.moduloInicio = moduloInicio;
		setTitle("Matricula Escolar - Nivel Primaria");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// Usamos DISPOSE_ON_CLOSE para manejar la acción de cierre manualmente
		setBounds(100, 100, 674, 545);
		setLocationRelativeTo(null);
		
		// Evento para que al pulsar la "X" de la ventana vuelva a ModuloInicio
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				volverAlInicio();
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("N° Matrícula");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(22, 52, 88, 20);
		contentPane.add(lblNewLabel);

		lblDniApoderado = new JLabel("DNI Apoderado");
		lblDniApoderado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDniApoderado.setBounds(22, 133, 102, 20);
		contentPane.add(lblDniApoderado);

		lblNewLabel_2 = new JLabel("DNI Alumno");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(22, 93, 88, 20);
		contentPane.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Telefono");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(22, 174, 88, 20);
		contentPane.add(lblNewLabel_3);

		textMatricula = new JTextField();
		textMatricula.setBounds(131, 54, 96, 18);
		contentPane.add(textMatricula);
		textMatricula.setColumns(10);
		aplicarFiltroNumerico(textMatricula, 11);// Limitar N° Matrícula a 11 dígitos numéricos

		textDniAlumno = new JTextField();
		textDniAlumno.setColumns(10);
		textDniAlumno.setBounds(131, 95, 96, 18);
		contentPane.add(textDniAlumno);
		aplicarFiltroNumerico(textDniAlumno, 8);// Limitar DNI Alumno a 8 dígitos numéricos
		
		// Evento para buscar automáticamente en pendientes.txt al escribir el DNI del alumno
		textDniAlumno.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        String dni = textDniAlumno.getText().trim();
		        // Solo buscar si el DNI ingresado tiene exactamente 8 dígitos
		        if (dni.length() == 8) {
		            buscarEnPendientePorDni(dni);
		        }
		    }
		});

		textDniApoderado = new JTextField();
		textDniApoderado.setColumns(10);
		textDniApoderado.setBounds(131, 135, 96, 18);
		contentPane.add(textDniApoderado);
		aplicarFiltroNumerico(textDniApoderado, 8);// Limitar DNI Apoderado a 8 dígitos numéricos

		textTelefono = new JTextField();
		textTelefono.setColumns(10);
		textTelefono.setBounds(131, 176, 96, 18);
		contentPane.add(textTelefono);
		aplicarFiltroNumerico(textTelefono, 9);// Limitar teléfono a 9 dígitos numéricos

		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFecha.setBounds(278, 52, 45, 20);
		contentPane.add(lblFecha);

		lblNewLabel_4 = new JLabel("Nombres Alumno");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(278, 93, 112, 20);
		contentPane.add(lblNewLabel_4);

		lblNombresApoderado = new JLabel("Nombres Apoderado");
		lblNombresApoderado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombresApoderado.setBounds(278, 133, 134, 20);
		contentPane.add(lblNombresApoderado);

		lblNewLabel_5 = new JLabel("Grado / Seccion");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(278, 174, 112, 20);
		contentPane.add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Matricula de Alumnos");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(241, 10, 171, 20);
		contentPane.add(lblNewLabel_6);

		textFecha = new JTextField();
		textFecha.setEditable(false);
		textFecha.setColumns(10);
		textFecha.setBounds(329, 54, 75, 18);
		contentPane.add(textFecha);

		textNombresAlumno = new JTextField();
		textNombresAlumno.setColumns(10);
		textNombresAlumno.setBounds(419, 93, 189, 18);
		contentPane.add(textNombresAlumno);

		textNombresApoderado = new JTextField();
		textNombresApoderado.setColumns(10);
		textNombresApoderado.setBounds(419, 133, 189, 18);
		contentPane.add(textNombresApoderado);

		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
			"1 - A", "1 - B", "1 - C", "2 - A", "2 - B", "2 - C", 
			"3 - A", "3 - B", "3 - C", "4 - A", "4 - B", "4 - C", 
			"5 - A", "5 - B", "5 - C", "6 - A", "6 - B", "6 - C"
		}));
		comboBox.setBounds(419, 175, 72, 20);
		contentPane.add(comboBox);

		lblHora = new JLabel("Hora");
		lblHora.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHora.setBounds(418, 52, 45, 20);
		contentPane.add(lblHora);

		lblNewLabel_1 = new JLabel("Vacantes");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(520, 174, 64, 20);
		contentPane.add(lblNewLabel_1);

		textVacantes = new JTextField();
		textVacantes.setEditable(false);
		textVacantes.setColumns(10);
		textVacantes.setBounds(586, 176, 64, 18);
		contentPane.add(textVacantes);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 238, 515, 260);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		btnCerrar = new JButton("CERRAR");
		btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCerrar.setBounds(547, 407, 105, 30);
		btnCerrar.addActionListener(this);
		contentPane.add(btnCerrar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLimpiar.setBounds(545, 323, 105, 30);
		btnLimpiar.addActionListener(this);
		contentPane.add(btnLimpiar);

		btnAnular = new JButton("Anular");
		btnAnular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnular.setBounds(545, 283, 105, 30);
		btnAnular.addActionListener(this);
		contentPane.add(btnAnular);

		btnMatricular = new JButton("Matricular");
		btnMatricular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMatricular.setBounds(545, 238, 105, 32);
		btnMatricular.addActionListener(this);
		contentPane.add(btnMatricular);

		textHora = new JTextField();
		textHora.setEditable(false);
		textHora.setColumns(10);
		textHora.setBounds(454, 54, 64, 18);
		contentPane.add(textHora);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardar.setBounds(545, 367, 105, 30);
		contentPane.add(btnGuardar);

		cargarFechaHora();
		actualizarVacantes();
		imprimirEncabezado();
	}

	// Método auxiliar para restringir la cantidad máxima de caracteres y permitir solo números
	private void aplicarFiltroNumerico(JTextField textField, int limite) {
		((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (string == null) return;
				if ((string.isEmpty() || esNumerico(string)) && (fb.getDocument().getLength() + string.length()) <= limite) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if (text == null) return;
				if (text.isEmpty() || (esNumerico(text) && (fb.getDocument().getLength() - length + text.length()) <= limite)) {
					super.replace(fb, offset, length, text, attrs);
				}
			}

			private boolean esNumerico(String text) {
				return text.matches("\\d+");
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btnMatricular) {
	        procesarMatricula();
	    } else if (e.getSource() == btnAnular) {
	        anularMatricula();
	    } else if (e.getSource() == btnLimpiar) {
	        limpiarCampos();
	    } else if (e.getSource() == btnGuardar) {
	        procesarGuardarPendiente();
	    } else if (e.getSource() == btnCerrar) {
	        volverAlInicio();
	    }
	}
	
	//Método para regresar a la ventana principal ModuloInicio
	private void volverAlInicio() {
		if (moduloInicio != null) {
			moduloInicio.setVisible(true);//
		}
		this.dispose();
	}

	private void imprimirEncabezado() {
		textArea.setText("");
		String cabecera = String.format("%-12s %-10s %-20s %-10s %-20s %-10s %-10s %-10s %-6s\n",
				"N° MATR.", "DNI ALUMNO", "ALUMNO", "DNI APOD.", "APODERADO", "TELÉFONO", "GRADO/SEC", "FECHA", "HORA");
		String separador = "==============================================================================================================\n";
		textArea.append(cabecera);
		textArea.append(separador);
	}

	private void procesarMatricula() {
	    if (vacantesDisponibles <= 0) {
	        JOptionPane.showMessageDialog(this, "No quedan vacantes disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    String numMatricula = textMatricula.getText().trim();
	    String dniAlumno = textDniAlumno.getText().trim();
	    String dniApoderado = textDniApoderado.getText().trim();
	    String nombresAlumno = textNombresAlumno.getText().trim();
	    String nombresApoderado = textNombresApoderado.getText().trim();
	    String telefono = textTelefono.getText().trim();
	    String gradoSeccion = comboBox.getSelectedItem().toString();

	    if (numMatricula.isEmpty() || dniAlumno.isEmpty() || dniApoderado.isEmpty() || 
	        nombresAlumno.isEmpty() || nombresApoderado.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    if (dniAlumno.length() != 8) {
	        JOptionPane.showMessageDialog(this, "El DNI del alumno debe tener exactamente 8 dígitos.", "Error de validación", JOptionPane.WARNING_MESSAGE);
	        textDniAlumno.requestFocus();
	        return;
	    }

	    if (dniApoderado.length() != 8) {
	        JOptionPane.showMessageDialog(this, "El DNI del apoderado debe tener exactamente 8 dígitos.", "Error de validación", JOptionPane.WARNING_MESSAGE);
	        textDniApoderado.requestFocus();
	        return;
	    }

	    cargarFechaHora();
	    vacantesDisponibles--;
	    actualizarVacantes();

	    Matricula m = new Matricula(numMatricula, dniAlumno, dniApoderado, telefono, 
	            textFecha.getText(), textHora.getText(), nombresAlumno, nombresApoderado, gradoSeccion, vacantesDisponibles);

	    listaMatriculas.add(m);
	    listarTodas();

	    // 1. Guardar automáticamente en el archivo alumnos.txt
	    guardarEnArchivo(m);

	 // 2. Si venía de pendientes, eliminarlo del archivo pendientes.txt
	    eliminarDePendientes(dniAlumno);

		JOptionPane.showMessageDialog(this, "Matrícula registrada exitosamente en alumnos.txt.");

		limpiarCampos();
	}
	// Método que escribe la matrícula en alumnos.txt (añadiendo al final)
	private void guardarEnArchivo(Matricula m) {
	    try (PrintWriter pw = new PrintWriter(new FileWriter("alumnos.txt", true))) {
	        // Formato exacto mostrado en tu alumnos.txt:
	        // Matricula;Fecha;Hora;DniAlumno;NombreAlumno;DniApoderado;NombreApoderado;Telefono;GradoSeccion
	        String linea = m.getMatricula() + ";" +
	                       m.getFecha() + ";" +
	                       m.getHora() + ";" +
	                       m.getDniAlumno() + ";" +
	                       m.getNombresAlumno() + ";" +
	                       m.getDniApoderado() + ";" +
	                       m.getNombresApoderado() + ";" +
	                       m.getTelefono() + ";" +
	                       m.getGradoSeccion();
	        pw.println(linea);
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Error al guardar en alumnos.txt: " + e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
	    }
	}

	// Método para guardar los datos en el archivo pendiente.txt
	private void guardarEnPendiente(Matricula m) {
	    // Usamos FileWriter en modo append (true) para agregar al final
		try (FileWriter fw = new FileWriter("pendientes.txt", true);
		         BufferedWriter bw = new BufferedWriter(fw);
		         PrintWriter pw = new PrintWriter(bw)) {
			
			String linea = m.getMatricula() + ";" +
                    m.getFecha() + ";" +
                    m.getHora() + ";" +
                    m.getDniAlumno() + ";" +
                    m.getNombresAlumno() + ";" +
                    m.getDniApoderado() + ";" +
                    m.getNombresApoderado() + ";" +
                    m.getTelefono() + ";" +
                    m.getGradoSeccion();
     
			pw.println(linea);
	        pw.flush();// Asegura que la información se escriba físicamente en el archivo de inmediato

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error al guardar en pendientes.txt: " + e.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método que procesa la acción del botón Guardar
	private void procesarGuardarPendiente() {
	    String numMatricula = textMatricula.getText().trim();
	    String dniAlumno = textDniAlumno.getText().trim();
	    String dniApoderado = textDniApoderado.getText().trim();
	    String nombresAlumno = textNombresAlumno.getText().trim();
	    String nombresApoderado = textNombresApoderado.getText().trim();
	    String telefono = textTelefono.getText().trim();
	    String gradoSeccion = comboBox.getSelectedItem().toString();
	    
	    // Validación básica de campos
	    if (numMatricula.isEmpty() || dniAlumno.isEmpty() || nombresAlumno.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Por favor complete al menos N° Matrícula, DNI Alumno y Nombres del Alumno.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    if (dniAlumno.length() != 8) {
	        JOptionPane.showMessageDialog(this, "El DNI del alumno debe tener 8 dígitos.", "Error de validación", JOptionPane.WARNING_MESSAGE);
	        textDniAlumno.requestFocus();
	        return;
	    }

	    cargarFechaHora();

	    // Creamos el objeto Matricula
	    Matricula m = new Matricula(numMatricula, dniAlumno, dniApoderado, telefono, 
	            textFecha.getText(), textHora.getText(), nombresAlumno, nombresApoderado, gradoSeccion, vacantesDisponibles);

	    // 1. Añadimos a la lista local para mostrarlo en pantalla
	    listaMatriculas.add(m);

	    // 2. Imprimimos/Actualizamos el JTextArea con la nueva fila
	    listarTodas();

	    // 3. Escribimos la información en pendiente.txt
	    guardarEnPendiente(m);

	    JOptionPane.showMessageDialog(this, "Registro guardado correctamente en pendiente.txt.");
	    
	    // 4. Limpiamos los campos del formulario
	    limpiarCampos();
	}
	// Busca los datos en pendientes.txt y autocompleta la vista
	private void buscarEnPendientePorDni(String dni) {
		File archivo = new File("pendientes.txt");
		if (!archivo.exists()) return;

		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(";");
				if (datos.length >= 9) {
					String dniAlu = datos[3].trim();
					if (dniAlu.equals(dni)) {
						textMatricula.setText(datos[0].trim());
						textNombresAlumno.setText(datos[4].trim());
						textDniApoderado.setText(datos[5].trim());
						textNombresApoderado.setText(datos[6].trim());
						textTelefono.setText(datos[7].trim());

						String grado = datos[8].trim();
						for (int i = 0; i < comboBox.getItemCount(); i++) {
							if (comboBox.getItemAt(i).equalsIgnoreCase(grado)) {
								comboBox.setSelectedIndex(i);
								break;
							}
						}
						JOptionPane.showMessageDialog(this, "Datos de alumno pendiente cargados automáticamente.");
						break;
					}
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error al leer pendientes.txt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
		
		// Remueve un registro de pendientes.txt tras ser matriculado
		private void eliminarDePendientes(String dniAlumno) {
			File archivoOriginal = new File("pendientes.txt");
			if (!archivoOriginal.exists()) return;

			File archivoTemporal = new File("pendientes_temp.txt");

			try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
				 PrintWriter pw = new PrintWriter(new FileWriter(archivoTemporal))) {

				String linea;
				while ((linea = br.readLine()) != null) {
					String[] datos = linea.split(";");
					if (datos.length >= 4 && datos[3].trim().equals(dniAlumno)) {
						continue; // Saltear la línea a eliminar
					}
					pw.println(linea);
				}

			} catch (IOException e) {
				return;
			}

			if (archivoOriginal.delete()) {
				archivoTemporal.renameTo(archivoOriginal);
			}
		}
	
		private void anularMatricula() {
		    String numMatricula = JOptionPane.showInputDialog(this, "Ingrese el N° de Matrícula a anular:");
		    if (numMatricula == null || numMatricula.trim().isEmpty()) return;
		    numMatricula = numMatricula.trim();

		    File f = new File("alumnos.txt");
		    if (!f.exists()) {
		        JOptionPane.showMessageDialog(this, "No existe el archivo alumnos.txt.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    ArrayList<String> lineas = new ArrayList<>();
		    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
		        String l;
		        while ((l = br.readLine()) != null) lineas.add(l);
		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(this, "Error al leer alumnos.txt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    // 1. Buscar y quitar la línea de matrícula (formato "N°;fecha;hora;...")
		    boolean encontrado = false;
		    for (int i = 0; i < lineas.size(); i++) {
		        if (lineas.get(i).trim().startsWith(numMatricula + ";")) {
		            lineas.remove(i);
		            encontrado = true;
		            break;
		        }
		    }

		    if (!encontrado) {
		        JOptionPane.showMessageDialog(this, "No se encontró el N° de matrícula ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    // 2. Eliminar el bloque completo del alumno (desde "Código: ..." hasta "====")
		    for (int i = 0; i < lineas.size(); i++) {
		        if (lineas.get(i).trim().equals("Código: " + numMatricula)) {
		            int fin = i;
		            while (fin < lineas.size() && !lineas.get(fin).trim().startsWith("====")) fin++;
		            // borrar de atrás hacia adelante para no desordenar los índices
		            for (int k = Math.min(fin, lineas.size() - 1); k >= i; k--) lineas.remove(k);
		            break;
		        }
		    }

		    // 3. Guardar el archivo
		    try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
		        for (String l : lineas) pw.println(l);
		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(this, "Error al guardar alumnos.txt: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    // 4. Quitar de la lista en pantalla (si estaba en esta sesión) y liberar vacante
		    for (int i = 0; i < listaMatriculas.size(); i++) {
		        if (listaMatriculas.get(i).getMatricula().equalsIgnoreCase(numMatricula)) {
		            listaMatriculas.remove(i);
		            break;
		        }
		    }
		    if (vacantesDisponibles < 30) vacantesDisponibles++;
		    actualizarVacantes();
		    listarTodas();

		    JOptionPane.showMessageDialog(this, "Matrícula " + numMatricula + " anulada correctamente.\n"
		            + "El alumno vuelve a estado 0 (Registrado).");
		}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void listarTodas() {
		imprimirEncabezado();
		for (Matricula m : listaMatriculas) {
			textArea.append(m.obtenerFila());
		}
	}

	private void limpiarCampos() {
		textMatricula.setText("");
		textDniAlumno.setText("");
		textDniApoderado.setText("");
		textNombresAlumno.setText("");
		textNombresApoderado.setText("");
		textTelefono.setText("");
		comboBox.setSelectedIndex(0);
		textMatricula.requestFocus();
	}

	private void cargarFechaHora() {
	    LocalDate hoy = LocalDate.now();
	    LocalTime ahora = LocalTime.now();
	    textFecha.setText(hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	    textHora.setText(ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss"))); // <-- Formato con segundos
	}

	private void actualizarVacantes() {
		textVacantes.setText(String.valueOf(vacantesDisponibles));
	}
	protected void actionPerformedBtnGuardar(ActionEvent e) {
		
	}
}