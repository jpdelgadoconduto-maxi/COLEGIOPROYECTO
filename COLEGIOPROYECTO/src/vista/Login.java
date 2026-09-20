package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon iconoOriginal = new ImageIcon(Login.class.getResource("/recursos/COLEIMA.jpg"));
		java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(550, 400, java.awt.Image.SCALE_SMOOTH);
		
		JLabel lblNewLabel = new JLabel("USUARIO");
		lblNewLabel.setBounds(121, 91, 67, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CONTRASEÑA");
		lblNewLabel_1.setBounds(121, 132, 81, 14);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(221, 88, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(220, 129, 97, 20);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("INGRESAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {String usuario = textField.getText();
			String password = new String(passwordField.getPassword());

			if (usuario.equals("admin") && password.equals("12345")) {
			    javax.swing.JOptionPane.showMessageDialog(null, "¡Bienvenido al Sistema de Matrícula Miguel Grau!");
			    // Aquí más adelante abriremos el menú principal del ERP
			} else {
			    javax.swing.JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de Acceso", javax.swing.JOptionPane.ERROR_MESSAGE);
			}

			}
		});
		btnNewButton.setBounds(180, 190, 113, 23);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255, 200));
        panel.setBounds(48, 51, 372, 176);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("ima");
		lblNewLabel_2.setIcon(new ImageIcon(imagenEscalada));
		
				lblNewLabel_2.setBounds(-56, -16, 510, 351);
				
						contentPane.add(lblNewLabel_2);

	}
}
