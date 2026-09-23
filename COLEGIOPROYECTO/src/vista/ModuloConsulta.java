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
import Control.Alumno;
import Control.Alumnocontroler; 

public class ModuloConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private ModuloInicio ventanaInicio;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 93, 382, 161);
		scrollPane.setViewportView(textArea); 
		contentPane.add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(161, 25, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Codigo Alumno");
		lblNewLabel.setBounds(35, 24, 116, 23);
		contentPane.add(lblNewLabel);
		
		// Evento del Botón CONSULTA
		JButton btnNewButton = new JButton("CONSULTA");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dniInput = textField.getText().trim();
				
				if (dniInput.isEmpty() || dniInput.length() != 8 || !dniInput.matches("\\d+")) {
					javax.swing.JOptionPane.showMessageDialog(null, 
						"Por favor, ingrese un número de DNI válido (8 dígitos).", 
						"Colegio Miguel Grau", 
						javax.swing.JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Alumnocontroler controlador = new Alumnocontroler();
				Alumno alu = controlador.buscarPorDni(dniInput);
				
				if (alu != null) {
					StringBuilder sb = new StringBuilder();
					sb.append("============= FICHA DE CONSULTA =============\n");
					sb.append("Código Alumno: ").append(alu.getCodigo()).append("\n");
					sb.append("DNI: ").append(alu.getDni()).append("\n");
					sb.append("Estudiante: ").append(alu.getNombre()).append(" ").append(alu.getApellidos()).append("\n");
					sb.append("Edad: ").append(alu.getEdad()).append(" años\n");
					sb.append("Grado al que pasa: ").append(alu.getGrado()).append("\n");
					sb.append("Teléfono de Casa: ").append(alu.getTelefono()).append("\n");
					sb.append("---------------------------------------------\n");
					sb.append("DATOS DE APODERADOS:\n");
					sb.append("Nombre del Papá: ").append(alu.getPapa()).append("\n");
					sb.append("Nombre de la Mamá: ").append(alu.getMama()).append("\n");
					// 🌟 CORRECCIÓN AQUÍ: Se cambió a getCellularApoderado() con doble l para sincronizar con tu POO
					sb.append("Celular de Contacto: ").append(alu.getCellularApoderado()).append("\n");
					sb.append("Dirección Domicilio: ").append(alu.getDireccion()).append("\n");
					sb.append("---------------------------------------------\n");
					sb.append("ESTADO DE MATRÍCULA: ").append(alu.getEstadoMatricula()).append("\n");
					sb.append("Último Movimiento: ").append(alu.getFechaMovimiento()).append("\n");
					sb.append("Responsable: ").append(alu.getResponsableMovimiento()).append("\n");
					sb.append("=============================================");
					
					textArea.setText(sb.toString()); 
				} else {
					textArea.setText("");
					javax.swing.JOptionPane.showMessageDialog(null, 
						"El estudiante con el DNI " + dniInput + " no está registrado en el colegio.", 
						"Alumno No Encontrado", 
						javax.swing.JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(287, 0, 116, 23);
		contentPane.add(btnNewButton);
		
		// Evento del Botón LIMPIAR
		JButton btnNewButton_1 = new JButton("LIMPIAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea.setText("");
			}
		});
		btnNewButton_1.setBounds(287, 24, 116, 23);
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
		btnNewButton_2.setBounds(287, 59, 116, 23);
		contentPane.add(btnNewButton_2);
	}
}
