package com.luzcolectiva.gui;

import com.luzcolectiva.dao.DaoFactory;
import com.luzcolectiva.gui.components.GenericListPanel;
import com.luzcolectiva.modelo.AppSettings;
import com.luzcolectiva.modelo.Customer;
import com.luzcolectiva.modelo.CustomerServiceEvent;
import com.luzcolectiva.modelo.ExtraCharge;
import com.luzcolectiva.modelo.ExtraChargeAssignment;
import com.luzcolectiva.modelo.ExtraChargePayment;
import com.luzcolectiva.modelo.MeterReading;
import com.luzcolectiva.modelo.Payment;
import com.luzcolectiva.modelo.ProviderBill;
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

    public static GenericListPanel createProviderBillsPanel() {
        GenericListPanel panel = new GenericListPanel("Recibos del Proveedor");
        panel.setDataLoader(() -> {
            List<ProviderBill> bills = DaoFactory.providerBills().findAllOrderByPeriodDesc();
            String[] columns = {"Periodo", "Proveedor", "N° Recibo", "Emisión", "Vencimiento", "Consumo (kWh)", "Monto", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (ProviderBill b : bills) {
                String periodo = String.format("%04d-%02d", b.getPeriodYear(), b.getPeriodMonth());
                model.addRow(new Object[]{
                        periodo,
                        b.getProviderName(),
                        b.getProviderReceiptNumber(),
                        b.getIssueDate(),
                        b.getDueDate(),
                        b.getProviderConsumptionKwh(),
                        b.getProviderTotalAmount(),
                        b.getStatus()
                });
            }
            return model;
        });
        return panel;
    }

    public static GenericListPanel createMeterReadingsPanel() {
        GenericListPanel panel = new GenericListPanel("Lecturas de Medidor");
        panel.setDataLoader(() -> {
            List<MeterReading> readings = DaoFactory.meterReadings().findAllOrderByPeriodDesc();
            String[] columns = {"Periodo", "Fecha Lectura", "ID Cliente", "Lec. Anterior", "Lec. Actual"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (MeterReading r : readings) {
                String periodo = String.format("%04d-%02d", r.getPeriodYear(), r.getPeriodMonth());
                model.addRow(new Object[]{
                        periodo,
                        r.getReadingDate(),
                        r.getCustomerId(),
                        r.getPreviousReading(),
                        r.getCurrentReading()
                });
            }
            return model;
        });
        return panel;
    }

    public static GenericListPanel createExtraChargesPanel() {
        GenericListPanel panel = new GenericListPanel("Tipos de Cargos Extra");
        panel.setDataLoader(() -> {
            List<ExtraCharge> charges = DaoFactory.extraCharges().findAllOrderByName();
            String[] columns = {"Nombre", "Descripción", "Monto", "Inicio", "Vencimiento", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (ExtraCharge c : charges) {
                model.addRow(new Object[]{
                        c.getName(),
                        c.getDescription(),
                        c.getAmount(),
                        c.getStartDate(),
                        c.getDueDate(),
                        c.getStatus()
                });
            }
            return model;
        });
        return panel;
    }

    public static GenericListPanel createExtraChargeAssignmentsPanel() {
        GenericListPanel panel = new GenericListPanel("Asignaciones de Cargos");
        panel.setDataLoader(() -> {
            List<ExtraChargeAssignment> assignments = DaoFactory.extraChargeAssignments().findAllOrderById();
            String[] columns = {"ID Cargo", "ID Cliente", "Monto", "Pagado", "Saldo", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (ExtraChargeAssignment a : assignments) {
                model.addRow(new Object[]{
                        a.getExtraChargeId(),
                        a.getCustomerId(),
                        a.getAmount(),
                        a.getPaidAmount(),
                        a.getBalance(),
                        a.getStatus()
                });
            }
            return model;
        });
        return panel;
    }

    public static GenericListPanel createExtraChargePaymentsPanel() {
        GenericListPanel panel = new GenericListPanel("Pagos de Cargos Extra");
        panel.setDataLoader(() -> {
            List<ExtraChargePayment> payments = DaoFactory.extraChargePayments().findAllOrderByDateDesc();
            String[] columns = {"Fecha", "ID Asignación", "ID Cargo", "ID Cliente", "Monto", "Método", "Estado"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (ExtraChargePayment p : payments) {
                model.addRow(new Object[]{
                        p.getPaymentDate(),
                        p.getExtraChargeAssignmentId(),
                        p.getExtraChargeId(),
                        p.getCustomerId(),
                        p.getAmount(),
                        p.getPaymentMethod(),
                        p.getStatus()
                });
            }
            return model;
        });
        return panel;
    }

    public static GenericListPanel createCustomerServiceEventsPanel() {
        GenericListPanel panel = new GenericListPanel("Eventos de Servicio al Cliente");
        panel.setDataLoader(() -> {
            List<CustomerServiceEvent> events = DaoFactory.customerServiceEvents().findAllOrderByDateDesc();
            String[] columns = {"Fecha", "ID Cliente", "Tipo", "Motivo", "Monto", "Estado Cobro"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            for (CustomerServiceEvent e : events) {
                model.addRow(new Object[]{
                        e.getEventDate(),
                        e.getCustomerId(),
                        e.getEventType(),
                        e.getReason(),
                        e.getAmount(),
                        e.getChargeStatus()
                });
            }
            return model;
        });
        return panel;
    }
}