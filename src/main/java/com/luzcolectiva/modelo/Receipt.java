package com.luzcolectiva.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Receipt {

  private UUID id;
  private UUID customerId;
  private UUID meterReadingId;
  private UUID providerBillId;
  private String receiptNumber;
  private int periodYear;
  private int periodMonth;
  private LocalDate issueDate;
  private LocalDate dueDate;
  private BigDecimal subtotal;
  private BigDecimal total;
  private BigDecimal paidAmount;
  private BigDecimal balance;
  private BigDecimal sealKwhReferencePrice;
  private BigDecimal collectiveKwhPrice;
  private String status;
  private String notes;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private UUID createdBy;
  private UUID updatedBy;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }

  public UUID getMeterReadingId() {
    return meterReadingId;
  }

  public void setMeterReadingId(UUID meterReadingId) {
    this.meterReadingId = meterReadingId;
  }

  public UUID getProviderBillId() {
    return providerBillId;
  }

  public void setProviderBillId(UUID providerBillId) {
    this.providerBillId = providerBillId;
  }

  public String getReceiptNumber() {
    return receiptNumber;
  }

  public void setReceiptNumber(String receiptNumber) {
    this.receiptNumber = receiptNumber;
  }

  public int getPeriodYear() {
    return periodYear;
  }

  public void setPeriodYear(int periodYear) {
    this.periodYear = periodYear;
  }

  public int getPeriodMonth() {
    return periodMonth;
  }

  public void setPeriodMonth(int periodMonth) {
    this.periodMonth = periodMonth;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getPaidAmount() {
    return paidAmount;
  }

  public void setPaidAmount(BigDecimal paidAmount) {
    this.paidAmount = paidAmount;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public BigDecimal getSealKwhReferencePrice() {
    return sealKwhReferencePrice;
  }

  public void setSealKwhReferencePrice(BigDecimal sealKwhReferencePrice) {
    this.sealKwhReferencePrice = sealKwhReferencePrice;
  }

  public BigDecimal getCollectiveKwhPrice() {
    return collectiveKwhPrice;
  }

  public void setCollectiveKwhPrice(BigDecimal collectiveKwhPrice) {
    this.collectiveKwhPrice = collectiveKwhPrice;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UUID createdBy) {
    this.createdBy = createdBy;
  }

  public UUID getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(UUID updatedBy) {
    this.updatedBy = updatedBy;
  }
}
