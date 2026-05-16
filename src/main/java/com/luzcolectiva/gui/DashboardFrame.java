package com.luzcolectiva.gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Luz Colectiva - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        JLabel lblWelcome = new JLabel("¡Bienvenido al sistema Luz Colectiva!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblWelcome, BorderLayout.CENTER);
    }
}