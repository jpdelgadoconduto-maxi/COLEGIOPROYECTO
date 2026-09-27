package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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

// Importaciones de capa de modelo y arreglos
import arreglo.ArregloAlumnos;
import arreglo.ArregloGrados;
import arreglo.ArregloMatriculas;
import clases.Alumno;
import clases.Matricula;

public class ModuloMatricula extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCod;
	private JLabel lblNewLabel;
	private JLabel lblNMatricula;
	private JLabel lblCodalumno;
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
	private JButton btnMatricular;
	private JButton btnAnular;
	private JButton btnBorrar;
	private JTextArea textArea;
	private JComboBox<String> comboBox;
	private JButton btnBuscar;

	// Arreglos globales que gestionan los datos y archivos .txt
	private ArregloMatriculas am = new ArregloMatriculas();
	private ArregloAlumnos aa = new ArregloAlumnos();
	private ArregloGrados ag = new ArregloGrados();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuloMatricula frame = new ModuloMatricula();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModuloMatricula() {
		setTitle("Sistema de Matr�cula Escolar - Nivel Primaria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 399);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("MATR�CULA DE ALUMNOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(190, 10, 220, 32);
		contentPane.add(lblNewLabel);
		
		lblNMatricula = new JLabel("N� Matricula");
		lblNMatricula.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNMatricula.setBounds(20, 57, 85, 32);
		contentPane.add(lblNMatricula);
		
		textMatricula = new JTextField();
		textMatricula.setEditable(false);
		textMatricula.setBounds(115, 65, 110, 20);
		contentPane.add(textMatricula);
		
		lblCodalumno = new JLabel("CodAlumno");
		lblCodalumno.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodalumno.setBounds(20, 99, 85, 32);
		contentPane.add(lblCodalumno);

		textCod = new JTextField();
		textCod.setBounds(115, 107, 110, 20);
		contentPane.add(textCod);
		textCod.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscar.setBounds(235, 106, 85, 22);
		btnBuscar.addActionListener(this);
		contentPane.add(btnBuscar);
		
		lblGradoSeccion = new JLabel("Grado / Secci�n");
		lblGradoSeccion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGradoSeccion.setBounds(20, 141, 103, 32);
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
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		comboBox.setBounds(125, 147, 100, 23);
		comboBox.addActionListener(this);
		contentPane.add(comboBox);

		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFecha.setBounds(330, 57, 50, 32);
		contentPane.add(lblFecha);

		textFecha = new JTextField();
		textFecha.setEditable(false);
		textFecha.setBounds(380, 65, 80, 20);
		contentPane.add(textFecha);
		
		lblHora = new JLabel("Hora");
		lblHora.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHora.setBounds(470, 57, 40, 32);
		contentPane.add(lblHora);
		
		textHora = new JTextField();
		textHora.setEditable(false);
		textHora.setBounds(510, 65, 75, 20);
		contentPane.add(textHora);
		
		lblAlumno = new JLabel("Alumno");
		lblAlumno.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAlumno.setBounds(330, 99, 60, 32);
		contentPane.add(lblAlumno);

		textAlumno = new JTextField();
		textAlumno.setEditable(false);
		textAlumno.setBounds(390, 107, 195, 20);
		contentPane.add(textAlumno);
		
		lblVacante = new JLabel("Vacante");
		lblVacante.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVacante.setBounds(240, 141, 60, 32);
		contentPane.add(lblVacante);
		
		textVacante = new JTextField();
		textVacante.setEditable(false);
		textVacante.setBounds(300, 149, 60, 20);
		contentPane.add(textVacante);
		
		btnMatricular = new JButton("Matricular");
		btnMatricular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMatricular.setBounds(490, 194, 95, 32);
		btnMatricular.addActionListener(this);
		contentPane.add(btnMatricular);
		
		btnAnular = new JButton("Anular");
		btnAnular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnular.setBounds(490, 235, 95, 25);
		btnAnular.addActionListener(this);
		contentPane.add(btnAnular);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBorrar.setBounds(490, 270, 95, 25);
		btnBorrar.addActionListener(this);
		contentPane.add(btnBorrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 185, 460, 160);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		// M�todos de inicializaci�n
		nuevoRegistro();
		actualizarVacantes();
		listarMatriculas();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) {
			buscarAlumno();
		} else if (e.getSource() == btnMatricular) {
			matricularAlumno();
		} else if (e.getSource() == btnAnular) {
			anularMatricula();
		} else if (e.getSource() == btnBorrar) {
			limpiarCampos();
		} else if (e.getSource() == comboBox) {
			actualizarVacantes();
		}
	}

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
	private void matricularAlumno() {
		try {
			if (textAlumno.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Primero debe buscar un alumno registrado.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

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
			
			nuevoRegistro();
			actualizarVacantes();
			listarMatriculas();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al registrar la matr�cula.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void anularMatricula() {
		String numStr = JOptionPane.showInputDialog(this, "Ingrese el N� de Matr�cula a anular:");
		if (numStr != null && !numStr.trim().isEmpty()) {
			try {
				int numMat = Integer.parseInt(numStr.trim());
				Matricula m = am.buscar(numMat);

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
				} else {
					JOptionPane.showMessageDialog(this, "No se encontr� la matr�cula ingresada.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "N�mero de matr�cula inv�lido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void limpiarCampos() {
		textCod.setText("");
		textAlumno.setText("");
		textCod.requestFocus();
	}

	private void nuevoRegistro() {
		textMatricula.setText(String.valueOf(am.numeroCorrelativo()));
		textFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		textHora.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		textCod.setText("");
		textAlumno.setText("");
	}

	private void actualizarVacantes() {
		int capacidadMaxima = 30; // Vacantes por aula
		int codGradoSeleccionado = 1001 + comboBox.getSelectedIndex();
		int ocupados = am.contarPorGrado(codGradoSeleccionado);
		int vacantes = capacidadMaxima - ocupados;
		textVacante.setText(String.valueOf(vacantes));
	}

	private void listarMatriculas() {
		textArea.setText("");
		textArea.append("Nro MATRICULA\tCOD. ALUMNO\tFECHA\t\tHORA\n");
		textArea.append("-----------------------------------------------------------------------------------\n");
		for (int i = 0; i < am.tamaño(); i++) {
			Matricula m = am.obtener(i);
			textArea.append(m.getNumMatricula() + "\t" + 
							m.getCodAlumno() + "\t" + 
							m.getFecha() + "\t" + 
							m.getHora() + "\n");
		}
	}
}