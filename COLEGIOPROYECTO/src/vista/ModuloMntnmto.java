package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModuloMntnmto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ModuloInicio ventanaInicio;

	public ModuloMntnmto(ModuloInicio inicio) {
		this.ventanaInicio = inicio;
		initialize();
	}

	private void initialize() {
		setTitle("Mantenimiento de Alumnos - Colegio Miguel Grau");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("MANTENIMIENTO DE ALUMNOS");
		lblTitulo.setBounds(230, 10, 250, 25);
		contentPane.add(lblTitulo);

		JButton btnCerrar = new JButton("CERRAR");
		btnCerrar.setBounds(560, 420, 110, 30);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ventanaInicio != null) {
					ventanaInicio.setVisible(true);
				}
				dispose();
			}
		});
		contentPane.add(btnCerrar);
	}
}