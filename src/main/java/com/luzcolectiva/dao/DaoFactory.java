package com.luzcolectiva.dao;

import com.luzcolectiva.dao.impl.AppSettingsDaoJdbc;
import com.luzcolectiva.dao.impl.CustomerDaoJdbc;
import com.luzcolectiva.dao.impl.CustomerServiceEventDaoJdbc;
import com.luzcolectiva.dao.impl.ExtraChargeAssignmentDaoJdbc;
import com.luzcolectiva.dao.impl.ExtraChargeDaoJdbc;
import com.luzcolectiva.dao.impl.ExtraChargePaymentDaoJdbc;
import com.luzcolectiva.dao.impl.MeterReadingDaoJdbc;
import com.luzcolectiva.dao.impl.PaymentDaoJdbc;
import com.luzcolectiva.dao.impl.ProfileDaoJdbc;
import com.luzcolectiva.dao.impl.ProviderBillDaoJdbc;
import com.luzcolectiva.dao.impl.ReceiptDaoJdbc;
import com.luzcolectiva.dao.impl.ReceiptItemDaoJdbc;
import com.luzcolectiva.dao.impl.UserDaoJdbc;

public final class DaoFactory {

  private static final UserDao USER_DAO = new UserDaoJdbc();
  private static final ProfileDao PROFILE_DAO = new ProfileDaoJdbc();
  private static final AppSettingsDao APP_SETTINGS_DAO = new AppSettingsDaoJdbc();
  private static final CustomerDao CUSTOMER_DAO = new CustomerDaoJdbc();
  private static final MeterReadingDao METER_READING_DAO = new MeterReadingDaoJdbc();
  private static final ProviderBillDao PROVIDER_BILL_DAO = new ProviderBillDaoJdbc();
  private static final ReceiptDao RECEIPT_DAO = new ReceiptDaoJdbc();
  private static final ReceiptItemDao RECEIPT_ITEM_DAO = new ReceiptItemDaoJdbc();
  private static final PaymentDao PAYMENT_DAO = new PaymentDaoJdbc();
  private static final ExtraChargeDao EXTRA_CHARGE_DAO = new ExtraChargeDaoJdbc();
  private static final ExtraChargeAssignmentDao EXTRA_CHARGE_ASSIGNMENT_DAO =
      new ExtraChargeAssignmentDaoJdbc();
  private static final ExtraChargePaymentDao EXTRA_CHARGE_PAYMENT_DAO = new ExtraChargePaymentDaoJdbc();
  private static final CustomerServiceEventDao CUSTOMER_SERVICE_EVENT_DAO =
      new CustomerServiceEventDaoJdbc();

  private DaoFactory() {}

  public static UserDao users() {
    return USER_DAO;
  }

  public static ProfileDao profiles() {
    return PROFILE_DAO;
  }

  public static AppSettingsDao appSettings() {
    return APP_SETTINGS_DAO;
  }

  public static CustomerDao customers() {
    return CUSTOMER_DAO;
  }

  public static MeterReadingDao meterReadings() {
    return METER_READING_DAO;
  }

  public static ProviderBillDao providerBills() {
    return PROVIDER_BILL_DAO;
  }

  public static ReceiptDao receipts() {
    return RECEIPT_DAO;
  }

  public static ReceiptItemDao receiptItems() {
    return RECEIPT_ITEM_DAO;
  }

  public static PaymentDao payments() {
    return PAYMENT_DAO;
  }

  public static ExtraChargeDao extraCharges() {
    return EXTRA_CHARGE_DAO;
  }

  public static ExtraChargeAssignmentDao extraChargeAssignments() {
    return EXTRA_CHARGE_ASSIGNMENT_DAO;
  }

  public static ExtraChargePaymentDao extraChargePayments() {
    return EXTRA_CHARGE_PAYMENT_DAO;
  }

  public static CustomerServiceEventDao customerServiceEvents() {
    return CUSTOMER_SERVICE_EVENT_DAO;
  }
}
