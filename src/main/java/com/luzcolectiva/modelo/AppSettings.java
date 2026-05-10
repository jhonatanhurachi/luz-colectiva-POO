package com.luzcolectiva.modelo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AppSettings {

  private int id;
  private String organizationName;
  private String currencyCode;
  private String energyUnitLabel;
  private BigDecimal energyUnitPrice;
  private BigDecimal minimumPayment;
  private BigDecimal technicalPayment;
  private BigDecimal printingCost;
  private BigDecimal reconnectionFee;
  private int defaultDueDays;
  private boolean roundingEnabled;
  private BigDecimal roundingIncrement;
  private String roundingMode;
  private String collectiveContractNumber;
  private BigDecimal sealKwhPrice;
  private BigDecimal collectiveKwhPrice;
  private String locationLabel;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private UUID updatedBy;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getEnergyUnitLabel() {
    return energyUnitLabel;
  }

  public void setEnergyUnitLabel(String energyUnitLabel) {
    this.energyUnitLabel = energyUnitLabel;
  }

  public BigDecimal getEnergyUnitPrice() {
    return energyUnitPrice;
  }

  public void setEnergyUnitPrice(BigDecimal energyUnitPrice) {
    this.energyUnitPrice = energyUnitPrice;
  }

  public BigDecimal getMinimumPayment() {
    return minimumPayment;
  }

  public void setMinimumPayment(BigDecimal minimumPayment) {
    this.minimumPayment = minimumPayment;
  }

  public BigDecimal getTechnicalPayment() {
    return technicalPayment;
  }

  public void setTechnicalPayment(BigDecimal technicalPayment) {
    this.technicalPayment = technicalPayment;
  }

  public BigDecimal getPrintingCost() {
    return printingCost;
  }

  public void setPrintingCost(BigDecimal printingCost) {
    this.printingCost = printingCost;
  }

  public BigDecimal getReconnectionFee() {
    return reconnectionFee;
  }

  public void setReconnectionFee(BigDecimal reconnectionFee) {
    this.reconnectionFee = reconnectionFee;
  }

  public int getDefaultDueDays() {
    return defaultDueDays;
  }

  public void setDefaultDueDays(int defaultDueDays) {
    this.defaultDueDays = defaultDueDays;
  }

  public boolean isRoundingEnabled() {
    return roundingEnabled;
  }

  public void setRoundingEnabled(boolean roundingEnabled) {
    this.roundingEnabled = roundingEnabled;
  }

  public BigDecimal getRoundingIncrement() {
    return roundingIncrement;
  }

  public void setRoundingIncrement(BigDecimal roundingIncrement) {
    this.roundingIncrement = roundingIncrement;
  }

  public String getRoundingMode() {
    return roundingMode;
  }

  public void setRoundingMode(String roundingMode) {
    this.roundingMode = roundingMode;
  }

  public String getCollectiveContractNumber() {
    return collectiveContractNumber;
  }

  public void setCollectiveContractNumber(String collectiveContractNumber) {
    this.collectiveContractNumber = collectiveContractNumber;
  }

  public BigDecimal getSealKwhPrice() {
    return sealKwhPrice;
  }

  public void setSealKwhPrice(BigDecimal sealKwhPrice) {
    this.sealKwhPrice = sealKwhPrice;
  }

  public BigDecimal getCollectiveKwhPrice() {
    return collectiveKwhPrice;
  }

  public void setCollectiveKwhPrice(BigDecimal collectiveKwhPrice) {
    this.collectiveKwhPrice = collectiveKwhPrice;
  }

  public String getLocationLabel() {
    return locationLabel;
  }

  public void setLocationLabel(String locationLabel) {
    this.locationLabel = locationLabel;
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

  public UUID getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(UUID updatedBy) {
    this.updatedBy = updatedBy;
  }
}
