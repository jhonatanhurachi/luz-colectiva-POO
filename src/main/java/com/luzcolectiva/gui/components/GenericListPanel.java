package com.luzcolectiva.gui.components;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GenericListPanel extends JPanel {
    private JTable table;
    private JButton btnRefresh;
    private JLabel lblTitle;
    private java.util.function.Supplier<TableModel> dataLoader;

    public GenericListPanel(String title) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        btnRefresh = new JButton("Actualizar");
        btnRefresh.addActionListener(e -> refreshData());
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnRefresh, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

        table = new JTable();
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setDataLoader(java.util.function.Supplier<TableModel> loader) {
        this.dataLoader = loader;
        refreshData();
    }

    public void refreshData() {
        if (dataLoader != null) {
            try {
                table.setModel(dataLoader.get());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo cargar la información.\nVerifique la conexión a la base de datos.", 
                    "Error de Conexión", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    public JTable getTable() {
        return table;
    }
}