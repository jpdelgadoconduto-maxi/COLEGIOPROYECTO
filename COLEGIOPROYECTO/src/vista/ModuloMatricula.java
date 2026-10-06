package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ModuloMatricula extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textDniAlumno;
	private JLabel lblTitulo;
	private JLabel lblNMatricula;
	private JLabel lblDniAlumno;
	private JTextField textMatricula;
	private JLabel lblGradoSeccion;
	private JLabel lblFecha;
	private JLabel lblAlumno;
	private JLabel lblVacante;
	private JTextField textVacante;
	private JTextField textAlumno;
	private JTextField textFecha;
	private JLabel lblHora;
	private JTextField textHora;
	private JLabel lblApoderado;
	private JTextField textApoderado;
	private JLabel lblDniApoderado;
	private JTextField textDniApoderado;
	private JLabel lblTelefono;
	private JTextField textTelefono;
	
	private JButton btnMatricular;
	private JButton btnAnular;
	private JButton btnBorrar;
	private JTextArea textArea;
	private JComboBox<String> comboBox;

	private final String ARCHIVO_ALUMNOS = "alumnos.txt";
	private int correlativo = 100001;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuloMatricula frame = new ModuloMatricula();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModuloMatricula() {
<<<<<<< HEAD
		setTitle("Sistema de Matrícula Escolar - Nivel Primaria");
=======
		setTitle("Sistema de Matr�cula Escolar - Nivel Primaria");
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 530);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
<<<<<<< HEAD

		// Título
		lblTitulo = new JLabel("MATRÍCULA DE ALUMNOS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(230, 10, 220, 25);
		contentPane.add(lblTitulo);

		// N° Matrícula
		lblNMatricula = new JLabel("N° Matrícula");
=======
		
		lblNewLabel = new JLabel("MATRICULA DE ALUMNOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(190, 10, 220, 32);
		contentPane.add(lblNewLabel);
		
		lblNMatricula = new JLabel("N Matricula");
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
		lblNMatricula.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNMatricula.setBounds(20, 45, 85, 25);
		contentPane.add(lblNMatricula);

		textMatricula = new JTextField();
		textMatricula.setEditable(false);
		textMatricula.setBounds(115, 47, 90, 22);
		contentPane.add(textMatricula);

		// Fecha y Hora
		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFecha.setBounds(230, 45, 45, 25);
		contentPane.add(lblFecha);

<<<<<<< HEAD
		textFecha = new JTextField();
		textFecha.setEditable(false);
		textFecha.setBounds(280, 47, 85, 22);
		contentPane.add(textFecha);

		lblHora = new JLabel("Hora");
		lblHora.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHora.setBounds(380, 45, 40, 25);
		contentPane.add(lblHora);

		textHora = new JTextField();
		textHora.setEditable(false);
		textHora.setBounds(425, 47, 80, 22);
		contentPane.add(textHora);

		// Datos del Alumno
		lblDniAlumno = new JLabel("DNI Alumno");
		lblDniAlumno.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDniAlumno.setBounds(20, 80, 85, 25);
		contentPane.add(lblDniAlumno);

		textDniAlumno = new JTextField();
		textDniAlumno.setBounds(115, 82, 110, 22);
		contentPane.add(textDniAlumno);

		lblAlumno = new JLabel("Nombres Alumno");
		lblAlumno.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAlumno.setBounds(240, 80, 115, 25);
		contentPane.add(lblAlumno);

		textAlumno = new JTextField();
		textAlumno.setBounds(360, 82, 280, 22);
		contentPane.add(textAlumno);

		// Datos del Apoderado
		lblDniApoderado = new JLabel("DNI Apoderado");
		lblDniApoderado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDniApoderado.setBounds(20, 115, 100, 25);
		contentPane.add(lblDniApoderado);

		textDniApoderado = new JTextField();
		textDniApoderado.setBounds(125, 117, 100, 22);
		contentPane.add(textDniApoderado);

		lblApoderado = new JLabel("Nombres Apoderado");
		lblApoderado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblApoderado.setBounds(235, 115, 130, 25);
		contentPane.add(lblApoderado);

		textApoderado = new JTextField();
		textApoderado.setBounds(370, 117, 270, 22);
		contentPane.add(textApoderado);

		// Campo Teléfono
		lblTelefono = new JLabel("Teléfono");
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTelefono.setBounds(20, 150, 85, 25);
		contentPane.add(lblTelefono);

		textTelefono = new JTextField();
		textTelefono.setBounds(100, 152, 125, 22);
		contentPane.add(textTelefono);

		// Grado / Sección y Vacantes
		lblGradoSeccion = new JLabel("Grado / Sección");
=======
		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscar.setBounds(235, 106, 85, 22);
		btnBuscar.addActionListener(this);
		contentPane.add(btnBuscar);
		
		lblGradoSeccion = new JLabel("Grado / Seccion");
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
		lblGradoSeccion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGradoSeccion.setBounds(240, 150, 110, 25);
		contentPane.add(lblGradoSeccion);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
			"1 - A", "1 - B", "1 - C", 
			"2 - A", "2 - B", "2 - C", 
			"3 - A", "3 - B", "3 - C", 
			"4 - A", "4 - B", "4 - C", 
			"5 - A", "5 - B", "5 - C", 
			"6 - A", "6 - B", "6 - C"
		}));
		comboBox.setBackground(Color.WHITE);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox.setBounds(355, 151, 90, 23);
		comboBox.addActionListener(this);
		contentPane.add(comboBox);

		lblVacante = new JLabel("Vacantes");
		lblVacante.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVacante.setBounds(460, 150, 65, 25);
		contentPane.add(lblVacante);

		textVacante = new JTextField();
		textVacante.setEditable(false);
		textVacante.setBounds(530, 152, 50, 22);
		contentPane.add(textVacante);

		// Botones
		btnMatricular = new JButton("Matricular");
		btnMatricular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMatricular.setBounds(535, 195, 105, 32);
		btnMatricular.addActionListener(this);
		contentPane.add(btnMatricular);

		btnAnular = new JButton("Anular");
		btnAnular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnular.setBounds(535, 240, 105, 30);
		btnAnular.addActionListener(this);
		contentPane.add(btnAnular);

		btnBorrar = new JButton("Limpiar");
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBorrar.setBounds(535, 280, 105, 30);
		btnBorrar.addActionListener(this);
		contentPane.add(btnBorrar);

		// Área de Texto con Scroll
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 195, 500, 270);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

<<<<<<< HEAD
		// Inicialización leyendo de alumnos.txt
		cargarCorrelativo();
=======
		// M�todos de inicializaci�n
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
		nuevoRegistro();
		actualizarVacantes();
		listarMatriculas();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnMatricular) {
			matricularAlumno();
		} else if (e.getSource() == btnAnular) {
			anularMatricula();
		} else if (e.getSource() == btnBorrar) {
			limpiarCampos();
		} else if (e.getSource() == comboBox) {
			actualizarVacantes();
		}
	}

<<<<<<< HEAD
=======
	// 1. Relaci�n: Buscar Alumno usando la clase Alumno y ArregloAlumnos
	private void buscarAlumno() {
		try {
			if (textCod.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Ingrese el c�digo de alumno.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int cod = Integer.parseInt(textCod.getText().trim());
			
			// Se consulta la lista y retorna un objeto de tipo Alumno
			Alumno a = aa.buscar(cod);

			if (a != null) {
				// Relaci�n: Se extraen los atributos del objeto con sus getters
				textAlumno.setText(a.getNombres() + " " + a.getApellidos());
				if (a.getEstado() == 1) {
					JOptionPane.showMessageDialog(this, "El alumno ya se encuentra MATRICULADO.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else if (a.getEstado() == 2) {
					JOptionPane.showMessageDialog(this, "El alumno se encuentra RETIRADO.", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "C�digo de alumno no registrado en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
				textAlumno.setText("");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El c�digo debe ser un n�mero entero v�lido.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// 2. Relaci�n: Registrar Matr�cula y cambiar Estado de Alumno a 1
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
	private void matricularAlumno() {
		String dniAlu = textDniAlumno.getText().trim();
		String nomAlu = textAlumno.getText().trim();
		String dniApo = textDniApoderado.getText().trim();
		String nomApo = textApoderado.getText().trim();
		String telefono = textTelefono.getText().trim();

		// 1. Validar que no haya campos vacíos
		if (dniAlu.isEmpty() || nomAlu.isEmpty() || dniApo.isEmpty() || nomApo.isEmpty() || telefono.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor complete todos los campos, incluyendo el Teléfono.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 2. Validar disponibilidad de vacantes
		int vacantes = Integer.parseInt(textVacante.getText());
		if (vacantes <= 0) {
			JOptionPane.showMessageDialog(this, "No quedan vacantes disponibles en esta sección.", "Aforo Completo", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String numMatricula = textMatricula.getText();
		String fecha = textFecha.getText();
		String hora = textHora.getText();
		String gradoSeccion = (String) comboBox.getSelectedItem();

		// 3. Cadena para guardar en el archivo alumnos.txt
		String lineaGuardado = numMatricula + ";" + fecha + ";" + hora + ";" + dniAlu + ";" + nomAlu + ";" + dniApo + ";" + nomApo + ";" + telefono + ";" + gradoSeccion;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_ALUMNOS, true))) {
			// Escribir en el archivo alumnos.txt
			bw.write(lineaGuardado);
			bw.newLine();

			// Si el JTextArea está vacío o recién inicia, le agregamos la cabecera de la tabla
			if (textArea.getText().trim().isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%-8s %-10s %-8s %-18s %-10s\n", "NRO MAT", "DNI ALU", "GRADO", "ALUMNO", "TELÉFONO"));
				sb.append("----------------------------------------------------------------------------------\n");
				textArea.setText(sb.toString());
			}

<<<<<<< HEAD
			// 4. Formatear y agregar la nueva fila directamente al JTextArea de forma inmediata
			String nuevaFila = String.format("%-8s %-10s %-8s %-18s %-10s\n",
					numMatricula, 
					dniAlu, 
					gradoSeccion, 
					nomAlu.length() > 16 ? nomAlu.substring(0, 16) : nomAlu,
					telefono);
=======
			int codAlumno = Integer.parseInt(textCod.getText().trim());
			Alumno a = aa.buscar(codAlumno);

			if (a.getEstado() != 0) {
				JOptionPane.showMessageDialog(this, "Solo se pueden matricular alumnos en estado 0 (Registrado).", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int vacantesDisponibles = Integer.parseInt(textVacante.getText());
			if (vacantesDisponibles <= 0) {
				JOptionPane.showMessageDialog(this, "No quedan vacantes disponibles en este grado/secci�n.", "Aforo Lleno", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int numMatricula = Integer.parseInt(textMatricula.getText());
			int codGrado = 1001 + comboBox.getSelectedIndex();
			String fecha = textFecha.getText();
			String hora = textHora.getText(); // Corregido: se usa textHora en lugar de textField_6

			// Instanciaci�n de la clase Matricula
			Matricula m = new Matricula(numMatricula, codAlumno, codGrado, fecha, hora);
			am.adicionar(m);

			// Actualizaci�n del estado del objeto Alumno
			a.setEstado(1); // 1 = Matriculado
			aa.guardar(); // Persistencia en alumnos.txt

			JOptionPane.showMessageDialog(this, "�Matr�cula N� " + numMatricula + " realizada con �xito!");
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
			
			textArea.append(nuevaFila); // Visualización inmediata en pantalla

			JOptionPane.showMessageDialog(this, "¡Matrícula N° " + numMatricula + " registrada exitosamente!");

			// 5. Actualización de estado y vacantes
			correlativo++;
			limpiarCampos();
			nuevoRegistro();
			actualizarVacantes();

		} catch (Exception ex) {
<<<<<<< HEAD
			JOptionPane.showMessageDialog(this, "Error al escribir en " + ARCHIVO_ALUMNOS + ": " + ex.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
=======
			JOptionPane.showMessageDialog(this, "Error al registrar la matr�cula.", "Error", JOptionPane.ERROR_MESSAGE);
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
		}
	}

	private void anularMatricula() {
<<<<<<< HEAD
		String numStr = JOptionPane.showInputDialog(this, "Ingrese el N° de Matrícula a anular:");
		if (numStr == null || numStr.trim().isEmpty()) {
			return;
		}
=======
		String numStr = JOptionPane.showInputDialog(this, "Ingrese el N� de Matr�cula a anular:");
		if (numStr != null && !numStr.trim().isEmpty()) {
			try {
				int numMat = Integer.parseInt(numStr.trim());
				Matricula m = am.buscar(numMat);
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git

<<<<<<< HEAD
		String targetMatricula = numStr.trim();
		File archivo = new File(ARCHIVO_ALUMNOS);

		if (!archivo.exists()) {
			JOptionPane.showMessageDialog(this, "El archivo " + ARCHIVO_ALUMNOS + " no existe aún.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		List<String> lineasConservadas = new ArrayList<>();
		boolean encontrado = false;

		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(";");
				if (datos.length > 0 && datos[0].equals(targetMatricula)) {
					encontrado = true;
=======
				if (m != null) {
					Alumno a = aa.buscar(m.getCodAlumno());
					if (a != null) {
						a.setEstado(0); // El alumno regresa a estar Registrado
						aa.guardar();
					}
					am.eliminar(m);
					JOptionPane.showMessageDialog(this, "Matr�cula N� " + numMat + " anulada correctamente.");
					
					nuevoRegistro();
					actualizarVacantes();
					listarMatriculas();
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
				} else {
<<<<<<< HEAD
					lineasConservadas.add(linea);
=======
					JOptionPane.showMessageDialog(this, "No se encontr� la matr�cula ingresada.", "Error", JOptionPane.ERROR_MESSAGE);
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
				}
<<<<<<< HEAD
=======
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "N�mero de matr�cula inv�lido.", "Error", JOptionPane.ERROR_MESSAGE);
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al leer " + ARCHIVO_ALUMNOS, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (encontrado) {
			try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, false))) {
				for (String l : lineasConservadas) {
					pw.println(l);
				}
				JOptionPane.showMessageDialog(this, "Matrícula N° " + targetMatricula + " anulada correctamente.");
				actualizarVacantes();
				listarMatriculas();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al reescribir " + ARCHIVO_ALUMNOS, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se encontró la matrícula N° " + targetMatricula + " en " + ARCHIVO_ALUMNOS, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiarCampos() {
		textDniAlumno.setText("");
		textAlumno.setText("");
		textDniApoderado.setText("");
		textApoderado.setText("");
		textTelefono.setText("");
		textDniAlumno.requestFocus();
	}

	private void nuevoRegistro() {
		textMatricula.setText(String.valueOf(correlativo));
		textFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		textHora.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
	}

	private void cargarCorrelativo() {
		File file = new File(ARCHIVO_ALUMNOS);
		if (!file.exists()) return;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String linea;
			int max = correlativo;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(";");
				if (datos.length > 0) {
					try {
						int num = Integer.parseInt(datos[0]);
						if (num >= max) {
							max = num + 1;
						}
					} catch (NumberFormatException ignored) {}
				}
			}
			correlativo = max;
		} catch (Exception ignored) {}
	}

	private void actualizarVacantes() {
		int capacidadMaxima = 30;
		String gradoSeleccionado = (String) comboBox.getSelectedItem();
		int ocupados = 0;

		File file = new File(ARCHIVO_ALUMNOS);
		if (file.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String linea;
				while ((linea = br.readLine()) != null) {
					String[] datos = linea.split(";");
					if (datos.length >= 8 && datos[datos.length - 1].equals(gradoSeleccionado)) {
						ocupados++;
					}
				}
			} catch (Exception ignored) {}
		}

		int vacantes = capacidadMaxima - ocupados;
		textVacante.setText(String.valueOf(vacantes));
	}

	// Carga y muestra todos los registros del TXT en el JTextArea
	private void listarMatriculas() {
<<<<<<< HEAD
	    // 1. Limpiar el contenido actual del JTextArea
	    textArea.setText("");
	    
	    // 2. Establecer fuente Monospaced para que los anchos de columna se alineen correctamente
	    textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

	    StringBuilder sb = new StringBuilder();
	    
	    // Cabecera de la tabla que se verá en el área de texto
	    sb.append(String.format("%-8s %-10s %-8s %-20s %-10s\n", "NRO MAT", "DNI ALU", "GRADO", "ALUMNO", "TELÉFONO"));
	    sb.append("----------------------------------------------------------------------------------\n");

	    File file = new File(ARCHIVO_ALUMNOS);

	    if (file.exists()) {
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                if (linea.trim().isEmpty()) continue; // Salta líneas vacías

	                String[] datos = linea.split(";");

	                // Verifica que la línea tenga la estructura adecuada
	                if (datos.length >= 8) {
	                    String numMat   = datos[0];
	                    String dniAlu   = datos[3];
	                    String nomAlu   = datos[4];
	                    String telefono = (datos.length >= 9) ? datos[7] : "S/N";
	                    String grado    = datos[datos.length - 1];

	                    // Recortar el nombre si es muy largo para no desencajar la tabla
	                    if (nomAlu.length() > 18) {
	                        nomAlu = nomAlu.substring(0, 18);
	                    }

	                    // Formatear la fila
	                    sb.append(String.format("%-8s %-10s %-8s %-20s %-10s\n", 
	                            numMat, dniAlu, grado, nomAlu, telefono));
	                }
	            }
	        } catch (Exception e) {
	            sb.append("Error al leer el archivo de matrículas.");
	        }
	    } else {
	        sb.append("No hay registros de matrícula guardados aún.\n");
	    }

	    // 3. Insertar todo el texto generado en el JTextArea
	    textArea.setText(sb.toString());
=======
		textArea.setText("");
		textArea.append("N MATRICULA\tCOD. ALUMNO\tFECHA\t\tHORA\n");
		textArea.append("-----------------------------------------------------------------------------------\n");
		for (int i = 0; i < am.tamaño(); i++) {
			Matricula m = am.obtener(i);
			textArea.append(m.getNumMatricula() + "\t" + 
							m.getCodAlumno() + "\t" + 
							m.getFecha() + "\t" + 
							m.getHora() + "\n");
		}
>>>>>>> branch 'master' of https://github.com/jpdelgadoconduto-maxi/COLEGIOPROYECTO.git
	}
}