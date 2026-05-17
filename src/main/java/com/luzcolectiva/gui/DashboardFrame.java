package com.luzcolectiva.gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel sidebar;

    public DashboardFrame() {
        setTitle("Luz Colectiva - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel lblWelcome = new JLabel("¡Bienvenido al sistema Luz Colectiva!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        welcomePanel.add(lblWelcome, BorderLayout.CENTER);
        
        cardPanel.add(welcomePanel, "Welcome");
        
        add(cardPanel, BorderLayout.CENTER);

        setupSidebar();
        
        registerListPanels();
    }

    private void registerListPanels() {
        addListPanel("UsersList", ListPanelsFactory.createUsersPanel());
        addListPanel("CustomersList", ListPanelsFactory.createCustomersPanel());
        addListPanel("ReceiptsList", ListPanelsFactory.createReceiptsPanel());
        addListPanel("PaymentsList", ListPanelsFactory.createPaymentsPanel());
        addListPanel("ProviderBillsList", ListPanelsFactory.createProviderBillsPanel());
        addListPanel("MeterReadingsList", ListPanelsFactory.createMeterReadingsPanel());
        addListPanel("ExtraChargesList", ListPanelsFactory.createExtraChargesPanel());
        addListPanel("ExtraChargeAssignmentsList", ListPanelsFactory.createExtraChargeAssignmentsPanel());
        addListPanel("ExtraChargePaymentsList", ListPanelsFactory.createExtraChargePaymentsPanel());
        addListPanel("CustomerServiceEventsList", ListPanelsFactory.createCustomerServiceEventsPanel());
        addListPanel("SettingsList", ListPanelsFactory.createAppSettingsPanel());
    }

    private void setupSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        sidebar.setPreferredSize(new Dimension(220, 0));
        
        sidebar.setBackground(new Color(45, 52, 54));

        JLabel menuTitle = new JLabel("MENÚ PRINCIPAL");
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 14));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        sidebar.add(menuTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        addMenuButton("Inicio", "Welcome");
        addMenuButton("Usuarios", "UsersList");
        addMenuButton("Clientes", "CustomersList");
        addMenuButton("Lecturas", "MeterReadingsList");
        addMenuButton("Recibos Proveedor", "ProviderBillsList");
        addMenuButton("Recibos Generados", "ReceiptsList");
        addMenuButton("Pagos Registrados", "PaymentsList");
        addMenuButton("Cargos Extra", "ExtraChargesList");
        addMenuButton("Asignaciones", "ExtraChargeAssignmentsList");
        addMenuButton("Pagos Extra", "ExtraChargePaymentsList");
        addMenuButton("Servicio al Cliente", "CustomerServiceEventsList");
        addMenuButton("Configuración", "SettingsList");

        add(sidebar, BorderLayout.WEST);
    }

    public void addMenuButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        
        btn.setContentAreaFilled(false); 
        btn.setOpaque(true); 
        
        btn.setBackground(new Color(99, 110, 114));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(btn);
        sidebar.revalidate();
        sidebar.repaint();
    }
    
    public void addListPanel(String cardName, JPanel panel) {
        cardPanel.add(panel, cardName);
    }
}