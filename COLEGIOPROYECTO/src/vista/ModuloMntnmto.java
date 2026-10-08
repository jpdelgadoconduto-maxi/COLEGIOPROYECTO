package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import Control.Alumno;
import Control.ArregloAlumnos;

public class ModuloMntnmto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ModuloInicio ventanaInicio;
	private ArregloAlumnos arreglo = new ArregloAlumnos();

	private JTextField txtCodigo, txtDni, txtNombres, txtApellidos, txtEdad;
	private JTextField txtGrado, txtTelefono, txtPapa, txtMama, txtCelApo, txtDireccion;
	private JButton btnAdicionar, btnModificar, btnEliminar, btnLimpiar, btnCerrar;
	private JTable tabla;
	private DefaultTableModel modeloTabla;

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

	private void initialize() {
		setTitle("Mantenimiento de Alumnos - Colegio Miguel Grau");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 860, 600);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("MANTENIMIENTO DE ALUMNOS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(300, 10, 270, 25);
		contentPane.add(lblTitulo);

		// Fila 1
		txtCodigo = campo("Código", 20, 45, 100, 70);
		txtCodigo.setEditable(false);
		txtDni = campo("DNI", 220, 45, 100, 40);
		txtEdad = campo("Edad", 400, 45, 50, 40);
		txtGrado = campo("Grado", 520, 45, 100, 50);
		// Fila 2
		txtNombres = campo("Nombres", 20, 80, 200, 70);
		txtApellidos = campo("Apellidos", 330, 80, 250, 70);
		// Fila 3
		txtTelefono = campo("Teléfono", 20, 115, 100, 70);
		txtCelApo = campo("Cel. Apoderado", 220, 115, 100, 100);
		// Fila 4
		txtPapa = campo("Papá", 20, 150, 250, 70);
		txtMama = campo("Mamá", 400, 150, 250, 50);
		// Fila 5
		txtDireccion = campo("Dirección", 20, 185, 450, 70);

		// Botones
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(730, 230, 100, 28);
		contentPane.add(btnAdicionar);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(730, 265, 100, 28);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(730, 300, 100, 28);
		contentPane.add(btnEliminar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(730, 335, 100, 28);
		contentPane.add(btnLimpiar);

		btnCerrar = new JButton("CERRAR");
		btnCerrar.setBounds(730, 520, 100, 30);
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
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { limpiar(); }
		});
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ventanaInicio != null) ventanaInicio.setVisible(true);
				dispose();
			}
		});

		// Tabla
		modeloTabla = new DefaultTableModel(
			new Object[] { "Código", "DNI", "Nombres", "Apellidos", "Edad", "Grado", "Celular Apo.", "Estado" }, 0) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int f, int c) { return false; }
		};
		tabla = new JTable(modeloTabla);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
				mostrarSeleccionado();
			}
		});

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBounds(20, 225, 695, 325);
		contentPane.add(scroll);
	}

	// ---------- Lógica ----------

	private void listar() {
		modeloTabla.setRowCount(0);
		for (int i = 0; i < arreglo.tamano(); i++) {
			Alumno a = arreglo.obtener(i);
			modeloTabla.addRow(new Object[] { a.getCodigo(), a.getDni(), a.getNombre(),
				a.getApellidos(), a.getEdad(), a.getGrado(), a.getCellularApoderado(),
				a.getEstadoMatricula() });
		}
	}

	private void mostrarSeleccionado() {
		String cod = (String) modeloTabla.getValueAt(tabla.getSelectedRow(), 0);
		Alumno a = arreglo.buscarCodigo(cod);
		if (a == null) return;
		txtCodigo.setText(a.getCodigo());
		txtDni.setText(a.getDni());
		txtNombres.setText(a.getNombre());
		txtApellidos.setText(a.getApellidos());
		txtEdad.setText(String.valueOf(a.getEdad()));
		txtGrado.setText(a.getGrado());
		txtTelefono.setText(a.getTelefono());
		txtPapa.setText(a.getPapa());
		txtMama.setText(a.getMama());
		txtCelApo.setText(a.getCellularApoderado());
		txtDireccion.setText(a.getDireccion());
	}

	private void limpiar() {
		tabla.clearSelection();
		txtCodigo.setText(arreglo.codigoCorrelativo());
		txtDni.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtEdad.setText("");
		txtGrado.setText("");
		txtTelefono.setText("");
		txtPapa.setText("");
		txtMama.setText("");
		txtCelApo.setText("");
		txtDireccion.setText("");
		txtDni.requestFocus();
	}

	// Devuelve true si todos los datos son válidos
	private boolean validar(boolean esNuevo) {
		JTextField[] todos = { txtDni, txtNombres, txtApellidos, txtEdad, txtGrado,
			txtTelefono, txtPapa, txtMama, txtCelApo, txtDireccion };
		for (JTextField t : todos) {
			if (t.getText().trim().isEmpty()) {
				mensaje("Complete todos los campos.");
				return false;
			}
			if (t.getText().contains(":") && false) return false;
		}
		if (!txtDni.getText().trim().matches("\\d{8}")) {
			mensaje("El DNI debe tener 8 dígitos.");
			return false;
		}
		if (!txtEdad.getText().trim().matches("\\d{1,2}")) {
			mensaje("La edad debe ser un número válido.");
			return false;
		}
		if (!txtCelApo.getText().trim().matches("\\d{9}")) {
			mensaje("El celular del apoderado debe tener 9 dígitos.");
			return false;
		}
		Alumno existente = arreglo.buscarDni(txtDni.getText().trim());
		if (existente != null && (esNuevo || !existente.getCodigo().equals(txtCodigo.getText()))) {
			mensaje("Ya existe un alumno con ese DNI.");
			return false;
		}
		return true;
	}

	private void adicionar() {
		if (!validar(true)) return;
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Alumno a = new Alumno(txtCodigo.getText(), txtDni.getText().trim(),
			txtNombres.getText().trim(), txtApellidos.getText().trim(),
			Integer.parseInt(txtEdad.getText().trim()), txtGrado.getText().trim(),
			txtTelefono.getText().trim(), txtPapa.getText().trim(), txtMama.getText().trim(),
			txtCelApo.getText().trim(), txtDireccion.getText().trim(),
			"Registrado", fecha, "Mantenimiento");
		arreglo.adicionar(a);
		mensaje("Alumno adicionado correctamente.");
		listar();
		limpiar();
	}

	private void modificar() {
		Alumno a = arreglo.buscarCodigo(txtCodigo.getText());
		if (a == null) {
			mensaje("Seleccione un alumno de la tabla para modificar.");
			return;
		}
		if (!validar(false)) return;
		a.setDni(txtDni.getText().trim());
		a.setNombre(txtNombres.getText().trim());
		a.setApellidos(txtApellidos.getText().trim());
		a.setEdad(Integer.parseInt(txtEdad.getText().trim()));
		a.setGrado(txtGrado.getText().trim());
		a.setTelefono(txtTelefono.getText().trim());
		a.setPapa(txtPapa.getText().trim());
		a.setMama(txtMama.getText().trim());
		a.setCelularApoderado(txtCelApo.getText().trim());
		a.setDireccion(txtDireccion.getText().trim());
		a.setFechaMovimiento(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		a.setResponsableMovimiento("Mantenimiento");
		arreglo.modificar();
		mensaje("Alumno modificado correctamente.");
		listar();
		limpiar();
	}

	private void eliminar() {
		Alumno a = arreglo.buscarCodigo(txtCodigo.getText());
		if (a == null) {
			mensaje("Seleccione un alumno de la tabla para eliminar.");
			return;
		}
		int r = JOptionPane.showConfirmDialog(this,
			"¿Eliminar a " + a.getNombre() + " " + a.getApellidos() + "?",
			"Confirmar", JOptionPane.YES_NO_OPTION);
		if (r == JOptionPane.YES_OPTION) {
			arreglo.eliminar(a);
			listar();
			limpiar();
		}
	}

	private void mensaje(String texto) {
		JOptionPane.showMessageDialog(this, texto, "Colegio Miguel Grau", JOptionPane.INFORMATION_MESSAGE);
	}
}