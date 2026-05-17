package com.luzcolectiva.gui;

import com.luzcolectiva.dao.DaoFactory;
import com.luzcolectiva.gui.components.GenericListPanel;
import com.luzcolectiva.modelo.AppSettings;
import com.luzcolectiva.modelo.Customer;
import com.luzcolectiva.modelo.Payment;
import com.luzcolectiva.modelo.Receipt;
import com.luzcolectiva.modelo.User;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Optional;

public class ListPanelsFactory {

    public static GenericListPanel createUsersPanel() {
        GenericListPanel panel = new GenericListPanel("Listado de Usuarios");

        panel.setDataLoader(() -> {
            List<User> users = DaoFactory.users().findAllOrderByName();
            String[] columns = {"ID", "Nombre Completo", "Email", "Teléfono", "Registrado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (User u : users) {
                model.addRow(new Object[]{
                        u.getId(),
                        u.getFullName(),
                        u.getEmail(),
                        u.getPhone(),
                        u.getCreatedAt() != null ? u.getCreatedAt().toLocalDate() : ""
                });
            }
            return model;
        });

        return panel;
    }

    public static GenericListPanel createCustomersPanel() {
        GenericListPanel panel = new GenericListPanel("Listado de Clientes");

        panel.setDataLoader(() -> {
            List<Customer> customers = DaoFactory.customers().findAllOrderByName();
            String[] columns = {"ID Registro", "DNI/Doc", "Nombres", "Apellidos", "Manzana", "Lote", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (Customer c : customers) {
                model.addRow(new Object[]{
                        c.getRegistrationNumber(),
                        c.getDocumentNumber(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getBlock(),
                        c.getLot(),
                        c.getStatus()
                });
            }
            return model;
        });

        return panel;
    }

    public static GenericListPanel createAppSettingsPanel() {
        GenericListPanel panel = new GenericListPanel("Configuración Global");

        panel.setDataLoader(() -> {
            Optional<AppSettings> settingsOpt = DaoFactory.appSettings().findSingleton();
            
            String[] columns = {"Parámetro", "Valor"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            if (settingsOpt.isPresent()) {
                AppSettings s = settingsOpt.get();
                model.addRow(new Object[]{"Organización", s.getOrganizationName()});
                model.addRow(new Object[]{"Moneda", s.getCurrencyCode()});
                model.addRow(new Object[]{"Unidad de Energía", s.getEnergyUnitLabel()});
                model.addRow(new Object[]{"Precio Unitario Energía", s.getEnergyUnitPrice()});
                model.addRow(new Object[]{"Pago Mínimo", s.getMinimumPayment()});
                model.addRow(new Object[]{"Cuota de Reconexión", s.getReconnectionFee()});
                model.addRow(new Object[]{"Contrato Colectivo", s.getCollectiveContractNumber()});
            }
            
            return model;
        });

        return panel;
    }

    public static GenericListPanel createReceiptsPanel() {
        GenericListPanel panel = new GenericListPanel("Recibos Generados");

        panel.setDataLoader(() -> {
            List<Receipt> receipts = DaoFactory.receipts().findAllOrderByPeriod();
            String[] columns = {"ID Cliente", "Periodo", "N° Recibo", "Subtotal", "Total", "Pagado", "Saldo", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (Receipt r : receipts) {
                String periodo = String.format("%04d-%02d", r.getPeriodYear(), r.getPeriodMonth());
                model.addRow(new Object[]{
                        r.getCustomerId(),
                        periodo,
                        r.getReceiptNumber(),
                        r.getSubtotal(),
                        r.getTotal(),
                        r.getPaidAmount(),
                        r.getBalance(),
                        r.getStatus()
                });
            }
            return model;
        });

        return panel;
    }

    public static GenericListPanel createPaymentsPanel() {
        GenericListPanel panel = new GenericListPanel("Historial de Pagos");

        panel.setDataLoader(() -> {
            List<Payment> payments = DaoFactory.payments().findAllOrderByDateDesc();
            String[] columns = {"Fecha", "ID Recibo", "ID Cliente", "Método", "Referencia", "Monto", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (Payment p : payments) {
                model.addRow(new Object[]{
                        p.getPaymentDate(),
                        p.getReceiptId(),
                        p.getCustomerId(),
                        p.getPaymentMethod(),
                        p.getReferenceNumber(),
                        p.getAmount(),
                        p.getStatus()
                });
            }
            return model;
        });

        return panel;
    }
}