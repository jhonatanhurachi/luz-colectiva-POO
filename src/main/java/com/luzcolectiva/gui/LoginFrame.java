package com.luzcolectiva.gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Luz Colectiva - Inicio de Sesión");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        setupListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));

        formPanel.add(new JLabel("Usuario:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        btnLogin = new JButton("Ingresar");
        buttonPanel.add(btnLogin);

        JLabel lblHelp = new JLabel("Ingresar con usuario: demo, contraseña: demo", SwingConstants.CENTER);
        lblHelp.setFont(new Font("Arial", Font.ITALIC, 11));
        lblHelp.setForeground(Color.GRAY);

        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(lblHelp, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setupListeners() {
        btnLogin.addActionListener(e -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if ("demo".equals(username) && "demo".equals(password)) {
            JOptionPane.showMessageDialog(this, 
                "¡Bienvenido, " + username + "!", 
                "Login Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
                
            new DashboardFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}