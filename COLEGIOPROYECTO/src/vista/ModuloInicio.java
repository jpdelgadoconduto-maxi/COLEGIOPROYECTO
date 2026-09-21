package vista;

import javax.swing.*;
import java.awt.*;

public class ModuloInicio extends JFrame {

    public ModuloInicio() {
        // 1. Configuración de la ventana principal
        setTitle("Sistema de Gestión - Colegio Miguel Grau");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout()); 

        // 2. Encabezado superior con el nombre del colegio
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(24, 43, 73)); // Azul institucional
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN - COLEGIO MIGUEL GRAU");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelSuperior.add(lblTitulo);
        
        add(panelSuperior, BorderLayout.NORTH);

        // 3. Panel Central para tus 4 módulos
        JPanel panelModulos = new JPanel();
        panelModulos.setLayout(new GridLayout(2, 2, 40, 40)); 
        panelModulos.setBorder(BorderFactory.createEmptyBorder(100, 180, 100, 180)); // Cuadros un poco más chicos

        // Creación de los 4 botones
        JButton btnMatricula = new JButton("MATRÍCULA");
        JButton btnMantenimiento = new JButton("MANTENIMIENTO");
        JButton btnConsulta = new JButton("CONSULTAS");
        JButton btnReporte = new JButton("REPORTES");

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

        // --- CONFIGURACIÓN DE LA IMAGEN DE FONDO ACHICADA ---
        JLabel lblEscudoFondo = new JLabel();
        
        // 1. Cargamos el icono original
        ImageIcon iconoOriginal = new ImageIcon("src/recursos/COLEIMA.jpg");
        
        // 2. Redimensionamos la imagen a 350x350 píxeles
        java.awt.Image imagenAchicada = iconoOriginal.getImage().getScaledInstance(900, 600, java.awt.Image.SCALE_SMOOTH);
        
        // 3. Le asignamos la nueva imagen achicada a la etiqueta
        lblEscudoFondo.setIcon(new ImageIcon(imagenAchicada));
        lblEscudoFondo.setHorizontalAlignment(SwingConstants.CENTER);

        // Metemos el panel de tus botones DENTRO de la etiqueta del escudo
        lblEscudoFondo.setLayout(new BorderLayout());
        panelModulos.setOpaque(false); 
        lblEscudoFondo.add(panelModulos, BorderLayout.CENTER);
        
        // Agregamos el escudo con todo adentro al centro de la ventana
        add(lblEscudoFondo, BorderLayout.CENTER);
    }

    // Método main limpio y corregido para que no dé errores al compilar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModuloInicio().setVisible(true);
        });
    }
}
