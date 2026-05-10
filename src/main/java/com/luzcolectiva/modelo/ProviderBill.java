package com.luzcolectiva.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ProviderBill {

  private UUID id;
  private int periodYear;
  private int periodMonth;
  private String providerName;
  private String providerReceiptNumber;
  private String collectiveContractNumber;
  private LocalDate issueDate;
  private LocalDate dueDate;
  private BigDecimal previousReading;
  private BigDecimal currentReading;
  private BigDecimal providerConsumptionKwh;
  private BigDecimal providerTotalAmount;
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

  public String getProviderName() {
    return providerName;
  }

  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }

  public String getProviderReceiptNumber() {
    return providerReceiptNumber;
  }

  public void setProviderReceiptNumber(String providerReceiptNumber) {
    this.providerReceiptNumber = providerReceiptNumber;
  }

  public String getCollectiveContractNumber() {
    return collectiveContractNumber;
  }

  public void setCollectiveContractNumber(String collectiveContractNumber) {
    this.collectiveContractNumber = collectiveContractNumber;
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

  public BigDecimal getPreviousReading() {
    return previousReading;
  }

  public void setPreviousReading(BigDecimal previousReading) {
    this.previousReading = previousReading;
  }

  public BigDecimal getCurrentReading() {
    return currentReading;
  }

  public void setCurrentReading(BigDecimal currentReading) {
    this.currentReading = currentReading;
  }

  public BigDecimal getProviderConsumptionKwh() {
    return providerConsumptionKwh;
  }

  public void setProviderConsumptionKwh(BigDecimal providerConsumptionKwh) {
    this.providerConsumptionKwh = providerConsumptionKwh;
  }

  public BigDecimal getProviderTotalAmount() {
    return providerTotalAmount;
  }

  public void setProviderTotalAmount(BigDecimal providerTotalAmount) {
    this.providerTotalAmount = providerTotalAmount;
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
