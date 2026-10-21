package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import Control.Alumno;
import Control.ArregloAlumno;

/**
 * MANTENIMIENTO - ALUMNO
 *
 * Adicionar : código (DNI + correlativo), nombres, apellidos, dni y teléfono,
 *             estado fijo en 0 (registrado), el dni no se repite.
 * Consultar : la tabla muestra todos los alumnos con sus datos completos.
 * Modificar : cualquier dato menos código y dni.
 * Eliminar  : solo si el estado es 0 (registrado), eliminación física con confirmación.
 * Grabar    : guarda los cambios en alumnos.txt.
 */
public class ModuloMntnmto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ModuloInicio ventanaInicio;
	private ArregloAlumno arreglo = new ArregloAlumno();

	// Datos obligatorios al adicionar
	private JTextField txtCodigo, txtDni, txtNombres, txtApellidos, txtTelefono;
	// Datos complementarios (opcionales)
	private JTextField txtApoderado, txtDniApoderado;
	private JComboBox<String> cmbGrado;
	// Solo lectura
	private JTextField txtEstado;
	private JLabel lblTotal;

	private JButton btnAdicionar, btnModificar, btnEliminar, btnGrabar, btnLimpiar, btnCerrar;
	private JTable tabla;
	private DefaultTableModel modeloTabla;

	private boolean hayCambios = false;   // hay cambios sin grabar
	private boolean enEdicion = false;    // hay un alumno seleccionado de la tabla

	// Excepción propia para errores de datos
	private static class DatosInvalidosException extends Exception {
		private static final long serialVersionUID = 1L;
		public DatosInvalidosException(String mensaje) { super(mensaje); }
	}

	public ModuloMntnmto(ModuloInicio inicio) {
		this.ventanaInicio = inicio;
		initialize();
		limpiar();
		listar();
	}

	private JTextField campo(String texto, int x, int y, int anchoCampo, int anchoLabel) {
		JLabel lbl = new JLabel(texto);
		lbl.setBounds(x, y, anchoLabel, 25);
		contentPane.add(lbl);
		JTextField t = new JTextField();
		t.setBounds(x + anchoLabel, y + 2, anchoCampo, 22);
		contentPane.add(t);
		return t;
	}

	// Solo letras (con tildes, ñ) y espacios.
	private void validarSoloLetras(JTextField campo, String nombreCampo) throws DatosInvalidosException {
		String valor = campo.getText().trim();
		if (!valor.matches("[A-Za-zÁÉÍÓÚÜáéíóúüÑñ ]+")) {
			campo.requestFocus();
			campo.selectAll();
			throw new DatosInvalidosException(
				"El campo \"" + nombreCampo + "\" solo debe contener letras.\n"
				+ "No se permiten números ni caracteres combinados. Por favor, corrija el dato.");
		}
	}

	// Solo números con cantidad exacta o rango de dígitos
	private void validarSoloNumeros(JTextField campo, String nombreCampo, int min, int max) throws DatosInvalidosException {
		String valor = campo.getText().trim();
		if (!valor.matches("\\d{" + min + "," + max + "}")) {
			campo.requestFocus();
			campo.selectAll();
			String cant = (min == max) ? min + " dígitos" : "entre " + min + " y " + max + " dígitos";
			throw new DatosInvalidosException(
				"El campo \"" + nombreCampo + "\" debe contener solo números (" + cant + ").\n"
				+ "Por favor, corrija el dato.");
		}
	}

	private void initialize() {
		setTitle("Mantenimiento de Alumnos - Colegio Miguel Grau");
		// La X de la ventana pasa por cerrar() para preguntar si hay cambios sin grabar
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) { cerrar(); }
		});
		setBounds(100, 100, 1010, 690);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("MANTENIMIENTO DE ALUMNOS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(330, 10, 270, 25);
		contentPane.add(lblTitulo);

		// Fila 1: código (DNI + correlativo, automático), dni, estado (fijo)
		txtCodigo = campo("Código", 20, 45, 120, 70);
		txtCodigo.setEditable(false);
		txtDni = campo("DNI", 260, 45, 100, 40);
		// El código es el DNI + correlativo: se genera apenas se escribe el DNI completo
		txtDni.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarCodigo(); }
			public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarCodigo(); }
			public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarCodigo(); }
		});
		txtEstado = campo("Estado", 420, 45, 120, 50);
		txtEstado.setEditable(false);
		// Fila 2
		txtNombres = campo("Nombres", 20, 80, 200, 70);
		txtApellidos = campo("Apellidos", 330, 80, 250, 70);
		// Fila 3
		txtTelefono = campo("Teléfono", 20, 115, 100, 70);

		// Datos complementarios
		JLabel lblOpc = new JLabel("Datos complementarios (opcionales)");
		lblOpc.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblOpc.setBounds(20, 150, 300, 20);
		contentPane.add(lblOpc);

		JLabel lblGrado = new JLabel("Grado/Secc.");
		lblGrado.setBounds(20, 172, 70, 25);
		contentPane.add(lblGrado);
		String[] grados = new String[19];
		grados[0] = "";
		int k = 1;
		for (int g = 1; g <= 6; g++) {
			for (char sec = 'A'; sec <= 'C'; sec++) grados[k++] = g + " - " + sec;
		}
		cmbGrado = new JComboBox<>(grados);
		cmbGrado.setBounds(90, 174, 80, 22);
		contentPane.add(cmbGrado);

		txtApoderado = campo("Apoderado", 20, 207, 250, 70);
		txtDniApoderado = campo("DNI Apoderado", 400, 207, 100, 95);

		lblTotal = new JLabel("Total de alumnos: 0");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTotal.setBounds(20, 245, 250, 20);
		contentPane.add(lblTotal);

		// Botones
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(880, 45, 100, 28);
		contentPane.add(btnAdicionar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(880, 80, 100, 28);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(880, 115, 100, 28);
		contentPane.add(btnEliminar);

		btnGrabar = new JButton("Grabar");
		btnGrabar.setBounds(880, 150, 100, 28);
		contentPane.add(btnGrabar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(880, 185, 100, 28);
		contentPane.add(btnLimpiar);

		btnCerrar = new JButton("CERRAR");
		btnCerrar.setBounds(880, 580, 100, 30);
		contentPane.add(btnCerrar);

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { adicionar(); }
		});
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { modificar(); }
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { eliminar(); }
		});
		btnGrabar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { grabar(); }
		});
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { limpiar(); }
		});
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { cerrar(); }
		});

		// Tabla con TODOS los datos de cada alumno
		modeloTabla = new DefaultTableModel(
			new Object[] { "Código", "DNI", "Nombres", "Apellidos", "Grado/Secc.", "Teléfono",
				"Apoderado", "DNI Apoderado", "Estado", "Fecha Mov.", "Responsable" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int f, int c) { return false; }
		};
		tabla = new JTable(modeloTabla);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // scroll horizontal para ver todas las columnas
		int[] anchos = { 100, 80, 130, 140, 75, 85, 190, 95, 100, 85, 100 };
		for (int i = 0; i < anchos.length; i++) {
			tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}
		tabla.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
				mostrarSeleccionado();
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(20, 270, 840, 340);
		contentPane.add(scroll);
	}

	// ---------- Lógica ----------

	// Consultar: muestra todos los alumnos con sus datos completos
	private void listar() {
		modeloTabla.setRowCount(0);
		for (int i = 0; i < arreglo.tamano(); i++) {
			Alumno a = arreglo.obtener(i);
			modeloTabla.addRow(new Object[] { a.getCodigo(), a.getDni(), a.getNombre(),
				a.getApellidos(), a.getGrado(), a.getCellularApoderado(),
				arreglo.getApoderado(a), arreglo.getDniApoderado(a),
				ArregloAlumno.textoEstado(a.getEstadoMatricula()),
				a.getFechaMovimiento(), a.getResponsableMovimiento() });
		}
		lblTotal.setText("Total de alumnos: " + arreglo.tamano());
	}

	private void mostrarSeleccionado() {
		String cod = (String) modeloTabla.getValueAt(tabla.getSelectedRow(), 0);
		Alumno a = arreglo.buscarCodigo(cod);
		if (a == null) return;
		enEdicion = true; // antes de llenar el DNI, para que no regenere el código
		txtCodigo.setText(a.getCodigo());
		txtDni.setText(a.getDni());
		txtNombres.setText(a.getNombre());
		txtApellidos.setText(a.getApellidos());
		txtTelefono.setText(a.getCellularApoderado());
		seleccionarGrado(a.getGrado());
		txtApoderado.setText(arreglo.getApoderado(a));
		txtDniApoderado.setText(arreglo.getDniApoderado(a));
		txtEstado.setText(ArregloAlumno.textoEstado(a.getEstadoMatricula()));
		// Código y DNI nunca se modifican
		txtDni.setEditable(false);
	}

	// Prepara el formulario para un alumno nuevo
	private void limpiar() {
		tabla.clearSelection();
		enEdicion = false;
		txtCodigo.setText("");
		txtDni.setEditable(true);
		txtDni.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtTelefono.setText("");
		cmbGrado.setSelectedIndex(0);
		txtApoderado.setText("");
		txtDniApoderado.setText("");
		txtEstado.setText(ArregloAlumno.textoEstado(ArregloAlumno.ESTADO_REGISTRADO));
		txtDni.requestFocus();
	}

	// Devuelve true si todos los datos son válidos
	private boolean validar(boolean esNuevo) {
		try {
			// Obligatorios: nombres, apellidos, dni (solo al adicionar) y teléfono
			JTextField[] obligatorios = esNuevo
				? new JTextField[] { txtDni, txtNombres, txtApellidos, txtTelefono }
				: new JTextField[] { txtNombres, txtApellidos, txtTelefono };
			for (JTextField t : obligatorios) {
				if (t.getText().trim().isEmpty()) {
					t.requestFocus();
					throw new DatosInvalidosException(
						"Complete nombres, apellidos, DNI y teléfono.");
				}
			}

			validarSoloLetras(txtNombres, "Nombres");
			validarSoloLetras(txtApellidos, "Apellidos");
			if (esNuevo) validarSoloNumeros(txtDni, "DNI", 8, 8);
			validarSoloNumeros(txtTelefono, "Teléfono", 9, 9);

			// Datos opcionales: solo se validan si se escribieron
			if (!txtApoderado.getText().trim().isEmpty()) validarSoloLetras(txtApoderado, "Apoderado");
			if (!txtDniApoderado.getText().trim().isEmpty()) validarSoloNumeros(txtDniApoderado, "DNI Apoderado", 8, 8);

			// El DNI no debe repetirse
			if (esNuevo && arreglo.buscarDni(txtDni.getText().trim()) != null) {
				txtDni.requestFocus();
				txtDni.selectAll();
				throw new DatosInvalidosException("Ya existe un alumno con ese DNI.");
			}

			return true;

		} catch (DatosInvalidosException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
				"Dato incorrecto - Requiere corrección", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void adicionar() {
		if (enEdicion) {
			mensaje("Hay un alumno seleccionado.\nPresione Limpiar para ingresar un alumno nuevo.");
			return;
		}
		if (!validar(true)) return;
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		String dni = txtDni.getText().trim();
		Alumno a = new Alumno(arreglo.codigoCorrelativo(dni), dni,
			txtNombres.getText().trim(), txtApellidos.getText().trim(),
			0, grado(), "", "", "",
			txtTelefono.getText().trim(), "",
			ArregloAlumno.ESTADO_REGISTRADO, fecha, "Mantenimiento");
		arreglo.setApoderado(a, txtApoderado.getText().trim());
		arreglo.setDniApoderado(a, txtDniApoderado.getText().trim());
		arreglo.adicionar(a);
		hayCambios = true;
		mensaje("Alumno adicionado (código " + a.getCodigo() + ").\nRecuerde presionar Grabar para guardar los cambios.");
		listar();
		limpiar();
	}

	private void modificar() {
		Alumno a = enEdicion ? arreglo.buscarCodigo(txtCodigo.getText()) : null;
		if (a == null) {
			mensaje("Seleccione un alumno de la tabla para modificar.");
			return;
		}
		if (!validar(false)) return;
		// Código, DNI y estado no se tocan
		a.setNombre(txtNombres.getText().trim());
		a.setApellidos(txtApellidos.getText().trim());
		a.setCelularApoderado(txtTelefono.getText().trim());
		a.setGrado(grado());
		arreglo.setApoderado(a, txtApoderado.getText().trim());
		arreglo.setDniApoderado(a, txtDniApoderado.getText().trim());
		a.setFechaMovimiento(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		a.setResponsableMovimiento("Mantenimiento");
		hayCambios = true;
		mensaje("Alumno modificado.\nRecuerde presionar Grabar para guardar los cambios.");
		listar();
		limpiar();
	}

	private void eliminar() {
		Alumno a = enEdicion ? arreglo.buscarCodigo(txtCodigo.getText()) : null;
		if (a == null) {
			mensaje("Seleccione un alumno de la tabla para eliminar.");
			return;
		}
		// Solo se elimina si el estado es 0 (registrado)
		if (!arreglo.esRegistrado(a)) {
			JOptionPane.showMessageDialog(this,
				"Solo se puede eliminar un alumno con estado 0 (registrado).\n"
				+ "Estado actual: " + ArregloAlumno.textoEstado(a.getEstadoMatricula()),
				"No se puede eliminar", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int r = JOptionPane.showConfirmDialog(this,
			"¿Eliminar definitivamente a " + a.getNombre() + " " + a.getApellidos() + "?",
			"Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (r == JOptionPane.YES_OPTION) {
			arreglo.eliminar(a); // eliminación física
			hayCambios = true;
			listar();
			limpiar();
		}
	}

	// Graba los cambios en alumnos.txt. Devuelve true si se grabó bien.
	private boolean grabar() {
		if (arreglo.grabar()) {
			hayCambios = false;
			mensaje("Cambios grabados correctamente.");
			return true;
		}
		JOptionPane.showMessageDialog(this, "No se pudo grabar el archivo alumnos.txt.",
			"Error de archivo", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private void cerrar() {
		if (hayCambios) {
			int r = JOptionPane.showConfirmDialog(this,
				"Hay cambios sin grabar. ¿Desea grabarlos antes de salir?",
				"Cambios pendientes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (r == JOptionPane.CANCEL_OPTION || r == JOptionPane.CLOSED_OPTION) return;
			if (r == JOptionPane.YES_OPTION && !grabar()) return;
		}
		if (ventanaInicio != null) ventanaInicio.setVisible(true);
		dispose();
	}

	private void actualizarCodigo() {
		if (enEdicion) return;
		String dni = txtDni.getText().trim();
		txtCodigo.setText(dni.matches("\\d{8}") ? arreglo.codigoCorrelativo(dni) : "");
	}

	private String grado() {
		String g = (String) cmbGrado.getSelectedItem();
		return g == null ? "" : g.trim();
	}

	private void seleccionarGrado(String g) {
		if (g == null || g.trim().isEmpty()) { cmbGrado.setSelectedIndex(0); return; }
		cmbGrado.setSelectedItem(g.trim());
		if (!g.trim().equals(cmbGrado.getSelectedItem())) cmbGrado.setSelectedIndex(0);
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(this, texto, "Colegio Miguel Grau", JOptionPane.INFORMATION_MESSAGE);
	}
}
