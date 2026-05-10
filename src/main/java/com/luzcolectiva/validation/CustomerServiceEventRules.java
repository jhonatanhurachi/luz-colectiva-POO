package com.luzcolectiva.validation;

import com.luzcolectiva.modelo.CustomerServiceEvent;
import java.math.BigDecimal;
import java.util.Objects;

public final class CustomerServiceEventRules {

  private CustomerServiceEventRules() {}

  /**
   * @throws IllegalArgumentException si el evento no cumple las reglas de corte o reconexión
   */
  public static void validate(CustomerServiceEvent e) {
    Objects.requireNonNull(e, "event");
    String type = e.getEventType();
    if (type == null || type.isBlank()) {
      throw new IllegalArgumentException("event_type es obligatorio.");
    }

    if ("cut".equals(type)) {
      if (e.getAmount() != null && e.getAmount().compareTo(BigDecimal.ZERO) != 0) {
        throw new IllegalArgumentException(
            "Evento 'cut': amount debe ser 0 (sin cargo asociado al corte).");
      }
      if (!"none".equals(e.getChargeStatus())) {
        throw new IllegalArgumentException(
            "Evento 'cut': charge_status debe ser 'none'.");
      }
      if (e.getReceiptId() != null) {
        throw new IllegalArgumentException("Evento 'cut': receipt_id debe ser null.");
      }
      return;
    }

    if ("reconnection".equals(type)) {
      String status = e.getChargeStatus();
      if (status == null
          || (!"pending".equals(status)
              && !"billed".equals(status)
              && !"cancelled".equals(status))) {
        throw new IllegalArgumentException(
            "Evento 'reconnection': charge_status debe ser 'pending', 'billed' o 'cancelled'.");
      }
    }
  }
}
