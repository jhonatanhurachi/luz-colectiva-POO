package com.luzcolectiva.dao.jdbc;

import com.luzcolectiva.modelo.AppSettings;
import com.luzcolectiva.modelo.Customer;
import com.luzcolectiva.modelo.CustomerServiceEvent;
import com.luzcolectiva.modelo.ExtraCharge;
import com.luzcolectiva.modelo.ExtraChargeAssignment;
import com.luzcolectiva.modelo.ExtraChargePayment;
import com.luzcolectiva.modelo.MeterReading;
import com.luzcolectiva.modelo.Payment;
import com.luzcolectiva.modelo.Profile;
import com.luzcolectiva.modelo.ProviderBill;
import com.luzcolectiva.modelo.Receipt;
import com.luzcolectiva.modelo.ReceiptItem;
import com.luzcolectiva.modelo.User;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class RowMappers {

  private RowMappers() {}

  public static User mapUser(ResultSet rs) throws SQLException {
    User e = new User();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setFullName(rs.getString("full_name"));
    e.setEmail(rs.getString("email"));
    e.setPhone(rs.getString("phone"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    return e;
  }

  public static Profile mapProfile(ResultSet rs) throws SQLException {
    Profile e = new Profile();
    e.setUserId(JdbcSupport.uuidOrNull(rs, "user_id"));
    e.setRole(rs.getString("role"));
    e.setStatus(rs.getString("status"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    return e;
  }

  public static AppSettings mapAppSettings(ResultSet rs) throws SQLException {
    AppSettings e = new AppSettings();
    e.setId(rs.getInt("id"));
    e.setOrganizationName(rs.getString("organization_name"));
    e.setCurrencyCode(rs.getString("currency_code"));
    e.setEnergyUnitLabel(rs.getString("energy_unit_label"));
    e.setEnergyUnitPrice(rs.getBigDecimal("energy_unit_price"));
    e.setMinimumPayment(rs.getBigDecimal("minimum_payment"));
    e.setTechnicalPayment(rs.getBigDecimal("technical_payment"));
    e.setPrintingCost(rs.getBigDecimal("printing_cost"));
    e.setReconnectionFee(rs.getBigDecimal("reconnection_fee"));
    e.setDefaultDueDays(rs.getInt("default_due_days"));
    e.setRoundingEnabled(rs.getBoolean("rounding_enabled"));
    e.setRoundingIncrement(rs.getBigDecimal("rounding_increment"));
    e.setRoundingMode(rs.getString("rounding_mode"));
    e.setCollectiveContractNumber(rs.getString("collective_contract_number"));
    e.setSealKwhPrice(rs.getBigDecimal("seal_kwh_price"));
    e.setCollectiveKwhPrice(rs.getBigDecimal("collective_kwh_price"));
    e.setLocationLabel(rs.getString("location_label"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static Customer mapCustomer(ResultSet rs) throws SQLException {
    Customer e = new Customer();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setRegistrationNumber(rs.getString("registration_number"));
    e.setFirstName(rs.getString("first_name"));
    e.setLastName(rs.getString("last_name"));
    e.setDocumentNumber(rs.getString("document_number"));
    e.setPhone(rs.getString("phone"));
    e.setBlock(rs.getString("block"));
    e.setLot(rs.getString("lot"));
    e.setAddressReference(rs.getString("address_reference"));
    e.setStatus(rs.getString("status"));
    e.setNotes(rs.getString("notes"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static MeterReading mapMeterReading(ResultSet rs) throws SQLException {
    MeterReading e = new MeterReading();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setCustomerId(JdbcSupport.uuidOrNull(rs, "customer_id"));
    e.setPeriodYear(rs.getInt("period_year"));
    e.setPeriodMonth(rs.getInt("period_month"));
    e.setPreviousReading(rs.getBigDecimal("previous_reading"));
    e.setCurrentReading(rs.getBigDecimal("current_reading"));
    e.setConsumption(rs.getBigDecimal("consumption"));
    e.setReadingDate(JdbcSupport.getLocalDate(rs, "reading_date"));
    e.setNotes(rs.getString("notes"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static ProviderBill mapProviderBill(ResultSet rs) throws SQLException {
    ProviderBill e = new ProviderBill();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setPeriodYear(rs.getInt("period_year"));
    e.setPeriodMonth(rs.getInt("period_month"));
    e.setProviderName(rs.getString("provider_name"));
    e.setProviderReceiptNumber(rs.getString("provider_receipt_number"));
    e.setCollectiveContractNumber(rs.getString("collective_contract_number"));
    e.setIssueDate(JdbcSupport.getLocalDate(rs, "issue_date"));
    e.setDueDate(JdbcSupport.getLocalDate(rs, "due_date"));
    e.setPreviousReading(rs.getBigDecimal("previous_reading"));
    e.setCurrentReading(rs.getBigDecimal("current_reading"));
    e.setProviderConsumptionKwh(rs.getBigDecimal("provider_consumption_kwh"));
    e.setProviderTotalAmount(rs.getBigDecimal("provider_total_amount"));
    e.setSealKwhReferencePrice(rs.getBigDecimal("seal_kwh_reference_price"));
    e.setCollectiveKwhPrice(rs.getBigDecimal("collective_kwh_price"));
    e.setStatus(rs.getString("status"));
    e.setNotes(rs.getString("notes"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static Receipt mapReceipt(ResultSet rs) throws SQLException {
    Receipt e = new Receipt();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setCustomerId(JdbcSupport.uuidOrNull(rs, "customer_id"));
    e.setMeterReadingId(JdbcSupport.uuidOrNull(rs, "meter_reading_id"));
    e.setProviderBillId(JdbcSupport.uuidOrNull(rs, "provider_bill_id"));
    e.setReceiptNumber(rs.getString("receipt_number"));
    e.setPeriodYear(rs.getInt("period_year"));
    e.setPeriodMonth(rs.getInt("period_month"));
    e.setIssueDate(JdbcSupport.getLocalDate(rs, "issue_date"));
    e.setDueDate(JdbcSupport.getLocalDate(rs, "due_date"));
    e.setSubtotal(rs.getBigDecimal("subtotal"));
    e.setTotal(rs.getBigDecimal("total"));
    e.setPaidAmount(rs.getBigDecimal("paid_amount"));
    e.setBalance(rs.getBigDecimal("balance"));
    e.setSealKwhReferencePrice(rs.getBigDecimal("seal_kwh_reference_price"));
    e.setCollectiveKwhPrice(rs.getBigDecimal("collective_kwh_price"));
    e.setStatus(rs.getString("status"));
    e.setNotes(rs.getString("notes"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static ReceiptItem mapReceiptItem(ResultSet rs) throws SQLException {
    ReceiptItem e = new ReceiptItem();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setReceiptId(JdbcSupport.uuidOrNull(rs, "receipt_id"));
    e.setConcept(rs.getString("concept"));
    e.setDescription(rs.getString("description"));
    e.setQuantity(rs.getBigDecimal("quantity"));
    e.setUnitPrice(rs.getBigDecimal("unit_price"));
    e.setAmount(rs.getBigDecimal("amount"));
    e.setItemType(rs.getString("item_type"));
    e.setSortOrder(rs.getInt("sort_order"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    return e;
  }

  public static Payment mapPayment(ResultSet rs) throws SQLException {
    Payment e = new Payment();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setReceiptId(JdbcSupport.uuidOrNull(rs, "receipt_id"));
    e.setCustomerId(JdbcSupport.uuidOrNull(rs, "customer_id"));
    e.setPaymentDate(JdbcSupport.getLocalDate(rs, "payment_date"));
    e.setAmount(rs.getBigDecimal("amount"));
    e.setPaymentMethod(rs.getString("payment_method"));
    e.setReferenceNumber(rs.getString("reference_number"));
    e.setNotes(rs.getString("notes"));
    e.setStatus(rs.getString("status"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static ExtraCharge mapExtraCharge(ResultSet rs) throws SQLException {
    ExtraCharge e = new ExtraCharge();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setName(rs.getString("name"));
    e.setDescription(rs.getString("description"));
    e.setAmount(rs.getBigDecimal("amount"));
    e.setStartDate(JdbcSupport.getLocalDate(rs, "start_date"));
    e.setDueDate(JdbcSupport.getLocalDate(rs, "due_date"));
    e.setStatus(rs.getString("status"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static ExtraChargeAssignment mapExtraChargeAssignment(ResultSet rs) throws SQLException {
    ExtraChargeAssignment e = new ExtraChargeAssignment();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setExtraChargeId(JdbcSupport.uuidOrNull(rs, "extra_charge_id"));
    e.setCustomerId(JdbcSupport.uuidOrNull(rs, "customer_id"));
    e.setAmount(rs.getBigDecimal("amount"));
    e.setPaidAmount(rs.getBigDecimal("paid_amount"));
    e.setBalance(rs.getBigDecimal("balance"));
    e.setStatus(rs.getString("status"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static ExtraChargePayment mapExtraChargePayment(ResultSet rs) throws SQLException {
    ExtraChargePayment e = new ExtraChargePayment();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setExtraChargeAssignmentId(JdbcSupport.uuidOrNull(rs, "extra_charge_assignment_id"));
    e.setExtraChargeId(JdbcSupport.uuidOrNull(rs, "extra_charge_id"));
    e.setCustomerId(JdbcSupport.uuidOrNull(rs, "customer_id"));
    e.setPaymentDate(JdbcSupport.getLocalDate(rs, "payment_date"));
    e.setAmount(rs.getBigDecimal("amount"));
    e.setPaymentMethod(rs.getString("payment_method"));
    e.setReferenceNumber(rs.getString("reference_number"));
    e.setNotes(rs.getString("notes"));
    e.setStatus(rs.getString("status"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }

  public static CustomerServiceEvent mapCustomerServiceEvent(ResultSet rs) throws SQLException {
    CustomerServiceEvent e = new CustomerServiceEvent();
    e.setId(JdbcSupport.uuidOrNull(rs, "id"));
    e.setCustomerId(JdbcSupport.uuidOrNull(rs, "customer_id"));
    e.setEventType(rs.getString("event_type"));
    e.setEventDate(JdbcSupport.getLocalDate(rs, "event_date"));
    e.setReason(rs.getString("reason"));
    e.setAmount(rs.getBigDecimal("amount"));
    e.setChargeStatus(rs.getString("charge_status"));
    e.setReceiptId(JdbcSupport.uuidOrNull(rs, "receipt_id"));
    e.setNotes(rs.getString("notes"));
    e.setCreatedAt(JdbcSupport.getOffsetDateTime(rs, "created_at"));
    e.setUpdatedAt(JdbcSupport.getOffsetDateTime(rs, "updated_at"));
    e.setCreatedBy(JdbcSupport.uuidOrNull(rs, "created_by"));
    e.setUpdatedBy(JdbcSupport.uuidOrNull(rs, "updated_by"));
    return e;
  }
}
