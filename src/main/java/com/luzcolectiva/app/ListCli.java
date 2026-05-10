package com.luzcolectiva.app;

import com.luzcolectiva.dao.DaoFactory;
import com.luzcolectiva.modelo.AppSettings;
import com.luzcolectiva.modelo.Customer;
import com.luzcolectiva.modelo.MeterReading;
import com.luzcolectiva.modelo.Receipt;
import com.luzcolectiva.modelo.User;
import java.util.Locale;
import java.util.UUID;

public final class ListCli {

  private ListCli() {}

  static void mostrarAyuda() {
    System.out.println("Luz Colectiva: comandos de listado");
    System.out.println();
    System.out.println("Uso: mvn exec:java -Dexec.args=\"<comando> [argumentos]\"");
    System.out.println();
    System.out.println("Comandos:");
    System.out.println("  ayuda              Muestra esta ayuda.");
    System.out.println("  clientes           Lista socios.");
    System.out.println("  usuarios           Lista usuarios del sistema.");
    System.out.println("  recibos            Lista todos los recibos (más recientes primero).");
    System.out.println("  lecturas <uuid>    Lecturas de medidor de un socio (ID del cliente).");
    System.out.println("  config             Muestra la configuración global.");
    System.out.println();
  }

  static void ejecutar(String[] args) {
    String[] a = normalizarArgumentos(args);
    String cmd = a[0].toLowerCase(Locale.ROOT);
    switch (cmd) {
      case "ayuda":
        mostrarAyuda();
        break;
      case "clientes":
        listarClientes();
        break;
      case "usuarios":
        listarUsuarios();
        break;
      case "recibos":
        listarRecibos();
        break;
      case "lecturas":
        if (a.length < 2) {
          System.err.println("Error: indica el ID del cliente (UUID). Ejemplo: lecturas aaaaaaaa-aaaa-aaaa-aaaa-000000000001");
          System.exit(2);
        }
        listarLecturas(a[1]);
        break;
      case "config":
        listarConfiguracion();
        break;
      default:
        System.err.println("Comando desconocido: " + a[0]);
        System.err.println("Escribe «ayuda» para ver los comandos disponibles.");
        System.exit(2);
    }
  }

  /**
   * Si Maven pasa {@code -Dexec.args} como una sola cadena con espacios, divide en tokens.
   */
  private static String[] normalizarArgumentos(String[] args) {
    if (args.length == 1 && args[0].contains(" ")) {
      return args[0].trim().split("\\s+");
    }
    return args;
  }

  private static void separador() {
    System.out.println("--------------------------------------------------------------------------------");
  }

  private static void listarClientes() {
    var dao = DaoFactory.customers();
    var lista = dao.findAllOrderByName();
    separador();
    System.out.println("SOCIOS (" + lista.size() + " registros)");
    separador();
    System.out.printf(
        "%-12s %-20s %-20s %-8s %-8s %-12s%n",
        "N° reg.", "Nombres", "Apellidos", "Bloque", "Lote", "Estado");
    separador();
    for (Customer c : lista) {
      String reg = c.getRegistrationNumber() != null ? c.getRegistrationNumber() : "—";
      System.out.printf(
          "%-12s %-20s %-20s %-8s %-8s %-12s%n",
          truncar(reg, 12),
          truncar(c.getFirstName(), 20),
          truncar(c.getLastName(), 20),
          truncar(c.getBlock(), 8),
          truncar(c.getLot(), 8),
          truncar(c.getStatus(), 12));
      System.out.println("  id: " + c.getId());
    }
    separador();
  }

  private static void listarUsuarios() {
    var lista = DaoFactory.users().findAllOrderByName();
    separador();
    System.out.println("USUARIOS (" + lista.size() + " registros)");
    separador();
    System.out.printf("%-36s %-28s %-32s %-14s%n", "ID", "Nombre completo", "Correo", "Teléfono");
    separador();
    for (User u : lista) {
      System.out.printf(
          "%-36s %-28s %-32s %-14s%n",
          u.getId(),
          truncar(u.getFullName(), 28),
          truncar(u.getEmail(), 32),
          u.getPhone() != null ? truncar(u.getPhone(), 14) : "—");
    }
    separador();
  }

  private static void listarRecibos() {
    var lista = DaoFactory.receipts().findAllOrderByPeriod();
    separador();
    System.out.println("RECIBOS (" + lista.size() + " registros)");
    separador();
    System.out.printf(
        "%-14s %-10s %-12s %-14s %-12s %-12s%n",
        "N° recibo", "Periodo", "Estado", "Total", "Pagado", "Saldo");
    separador();
    for (Receipt r : lista) {
      String num = r.getReceiptNumber() != null ? r.getReceiptNumber() : "—";
      String periodo = r.getPeriodYear() + "-" + String.format("%02d", r.getPeriodMonth());
      System.out.printf(
          "%-14s %-10s %-12s %-14s %-12s %-12s%n",
          truncar(num, 14),
          periodo,
          truncar(r.getStatus(), 12),
          r.getTotal(),
          r.getPaidAmount(),
          r.getBalance());
      System.out.println("  id recibo: " + r.getId() + " | id cliente: " + r.getCustomerId());
    }
    separador();
  }

  private static void listarLecturas(String idCliente) {
    UUID uuid;
    try {
      uuid = UUID.fromString(idCliente.trim());
    } catch (IllegalArgumentException e) {
      System.err.println("Error: el ID del cliente no es un UUID válido.");
      System.exit(2);
      return;
    }
    var lista = DaoFactory.meterReadings().findByCustomerId(uuid);
    separador();
    System.out.println("LECTURAS DE MEDIDOR — cliente " + uuid + " (" + lista.size() + " registros)");
    separador();
    System.out.printf(
        "%-10s %-14s %-14s %-14s %-14s %-12s%n",
        "Periodo", "Lect. anterior", "Lect. actual", "Consumo (kWh)", "Fecha lectura", "Notas");
    separador();
    for (MeterReading m : lista) {
      String periodo = m.getPeriodYear() + "-" + String.format("%02d", m.getPeriodMonth());
      String notas = m.getNotes() != null ? truncar(m.getNotes(), 12) : "—";
      System.out.printf(
          "%-10s %-14s %-14s %-14s %-14s %-12s%n",
          periodo,
          m.getPreviousReading(),
          m.getCurrentReading(),
          m.getConsumption() != null ? m.getConsumption().toString() : "—",
          m.getReadingDate(),
          notas);
      System.out.println("  id lectura: " + m.getId());
    }
    separador();
  }

  private static void listarConfiguracion() {
    var opt = DaoFactory.appSettings().findSingleton();
    separador();
    System.out.println("CONFIGURACIÓN GLOBAL (app_settings)");
    separador();
    if (opt.isEmpty()) {
      System.out.println("No hay fila con id = 1.");
      separador();
      return;
    }
    AppSettings s = opt.get();
    System.out.println("Organización:     " + s.getOrganizationName());
    System.out.println("Moneda:           " + s.getCurrencyCode());
    System.out.println("Unidad energía:   " + s.getEnergyUnitLabel());
    System.out.println("Precio kWh (cfg): " + s.getEnergyUnitPrice());
    System.out.println("Pago mínimo:      " + s.getMinimumPayment());
    System.out.println("Pago técnico:     " + s.getTechnicalPayment());
    System.out.println("Impresión:        " + s.getPrintingCost());
    System.out.println("Reconexión:       " + s.getReconnectionFee());
    System.out.println("Días vencimiento: " + s.getDefaultDueDays());
    System.out.println("Redondeo activo:  " + (s.isRoundingEnabled() ? "sí" : "no"));
    System.out.println("Incremento red.:    " + s.getRoundingIncrement());
    System.out.println("Modo redondeo:    " + s.getRoundingMode());
    System.out.println("N° contrato col.: " + nulo(s.getCollectiveContractNumber()));
    System.out.println("Precio SEAL kWh:  " + s.getSealKwhPrice());
    System.out.println("Precio comité:    " + s.getCollectiveKwhPrice());
    System.out.println("Ubicación (pie):  " + nulo(s.getLocationLabel()));
    System.out.println("Actualizado por:  " + (s.getUpdatedBy() != null ? s.getUpdatedBy().toString() : "—"));
    separador();
  }

  private static String nulo(String s) {
    return s != null ? s : "—";
  }

  private static String truncar(String s, int max) {
    if (s == null) {
      return "—";
    }
    return s.length() <= max ? s : s.substring(0, max - 1) + "…";
  }
}
