package com.luzcolectiva.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MeterReading {

  private UUID id;
  private UUID customerId;
  private int periodYear;
  private int periodMonth;
  private BigDecimal previousReading;
  private BigDecimal currentReading;
  private BigDecimal consumption;
  private LocalDate readingDate;
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

  public BigDecimal getConsumption() {
    return consumption;
  }

  public void setConsumption(BigDecimal consumption) {
    this.consumption = consumption;
  }

  public LocalDate getReadingDate() {
    return readingDate;
  }

  public void setReadingDate(LocalDate readingDate) {
    this.readingDate = readingDate;
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
