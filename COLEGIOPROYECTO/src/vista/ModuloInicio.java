package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModuloInicio extends JFrame {
    private static final long serialVersionUID = 1L;

    public ModuloInicio() {
        // 1. Configuración de la ventana principal
        setTitle("Sistema de Gestión - Colegio Miguel Grau");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
        getContentPane().setLayout(new BorderLayout()); 

        // 2. Encabezado superior con el nombre del colegio
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(24, 43, 73)); // Azul institucional
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN - COLEGIO MIGUEL GRAU");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelSuperior.add(lblTitulo);
        
        getContentPane().add(panelSuperior, BorderLayout.NORTH);

        // --- CONFIGURACIÓN DE LA IMAGEN DE FONDO ---
        JLabel lblEscudoFondo = new JLabel();
        ImageIcon iconoOriginal = new ImageIcon("src/recursos/COLEIMA.jpg");
        java.awt.Image imagenAchicada = iconoOriginal.getImage().getScaledInstance(900, 600, java.awt.Image.SCALE_SMOOTH);
        lblEscudoFondo.setIcon(new ImageIcon(imagenAchicada));
        lblEscudoFondo.setHorizontalAlignment(SwingConstants.CENTER);
        lblEscudoFondo.setLayout(new BorderLayout());

        // 3. Panel Central para tus 4 módulos
        JPanel panelModulos = new JPanel();
        panelModulos.setLayout(new GridLayout(2, 2, 40, 40)); 
        panelModulos.setBorder(BorderFactory.createEmptyBorder(100, 180, 100, 180)); 
        panelModulos.setOpaque(false); // Transparente para ver el fondo

        // Creación de los botones
        JButton btnMatricula = new JButton("MATRÍCULA");
        JButton btnMantenimiento = new JButton("MANTENIMIENTO");
        JButton btnConsulta = new JButton("CONSULTAS");
        JButton btnReporte = new JButton("REPORTES");

        // 🌟 CORRECCIÓN CRÍTICA: Se le pasa "ModuloInicio.this" para conectar la navegación POO
        btnConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                ModuloConsulta ventanaConsulta = new ModuloConsulta(ModuloInicio.this);
                ventanaConsulta.setVisible(true);
                setVisible(false); // Oculta la ventana de inicio actual
            }
        });

        // Estilo de texto para los botones
        Font fuenteBotones = new Font("Arial", Font.BOLD, 18);
        btnMatricula.setFont(fuenteBotones);
        btnMantenimiento.setFont(fuenteBotones);
        btnConsulta.setFont(fuenteBotones);
        btnReporte.setFont(fuenteBotones);

        // Agregar los botones al panel
        panelModulos.add(btnMatricula);
        panelModulos.add(btnMantenimiento);
        panelModulos.add(btnConsulta);
        panelModulos.add(btnReporte);

        // Metemos los botones encima del escudo y agregamos todo al centro
        lblEscudoFondo.add(panelModulos, BorderLayout.CENTER);
        getContentPane().add(lblEscudoFondo, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModuloInicio().setVisible(true);
        });
    }
}
