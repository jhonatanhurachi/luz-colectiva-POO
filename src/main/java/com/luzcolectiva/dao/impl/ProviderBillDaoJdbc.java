package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ProviderBillDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.ProviderBill;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class ProviderBillDaoJdbc implements ProviderBillDao {

  @Override
  public Optional<ProviderBill> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM provider_bills WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapProviderBill(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ProviderBillDao.findById", e);
    }
  }

  @Override
  public Optional<ProviderBill> findByPeriod(int year, int month) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM provider_bills WHERE period_year = ? AND period_month = ?")) {
      ps.setInt(1, year);
      ps.setInt(2, month);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapProviderBill(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ProviderBillDao.findByPeriod", e);
    }
  }

  @Override
  public void insert(ProviderBill b) {
    if (b.getId() == null) {
      b.setId(UUID.randomUUID());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO provider_bills (id, period_year, period_month, provider_name, provider_receipt_number, "
                    + "collective_contract_number, issue_date, due_date, previous_reading, current_reading, "
                    + "provider_consumption_kwh, provider_total_amount, seal_kwh_reference_price, "
                    + "collective_kwh_price, status, notes, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, b.getId());
      ps.setInt(i++, b.getPeriodYear());
      ps.setInt(i++, b.getPeriodMonth());
      ps.setString(i++, b.getProviderName());
      ps.setString(i++, b.getProviderReceiptNumber());
      ps.setString(i++, b.getCollectiveContractNumber());
      JdbcSupport.setLocalDate(ps, i++, b.getIssueDate());
      JdbcSupport.setLocalDate(ps, i++, b.getDueDate());
      ps.setBigDecimal(i++, b.getPreviousReading());
      ps.setBigDecimal(i++, b.getCurrentReading());
      ps.setBigDecimal(i++, b.getProviderConsumptionKwh());
      ps.setBigDecimal(i++, b.getProviderTotalAmount());
      ps.setBigDecimal(i++, b.getSealKwhReferencePrice());
      ps.setBigDecimal(i++, b.getCollectiveKwhPrice());
      ps.setString(i++, b.getStatus());
      ps.setString(i++, b.getNotes());
      JdbcSupport.setUuid(ps, i++, b.getCreatedBy());
      JdbcSupport.setUuid(ps, i, b.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ProviderBillDao.insert", e);
    }
  }

  @Override
  public int update(ProviderBill b) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE provider_bills SET period_year = ?, period_month = ?, provider_name = ?, "
                    + "provider_receipt_number = ?, collective_contract_number = ?, issue_date = ?, due_date = ?, "
                    + "previous_reading = ?, current_reading = ?, provider_consumption_kwh = ?, "
                    + "provider_total_amount = ?, seal_kwh_reference_price = ?, collective_kwh_price = ?, "
                    + "status = ?, notes = ?, created_by = ?, updated_by = ? WHERE id = ?")) {
      int i = 1;
      ps.setInt(i++, b.getPeriodYear());
      ps.setInt(i++, b.getPeriodMonth());
      ps.setString(i++, b.getProviderName());
      ps.setString(i++, b.getProviderReceiptNumber());
      ps.setString(i++, b.getCollectiveContractNumber());
      JdbcSupport.setLocalDate(ps, i++, b.getIssueDate());
      JdbcSupport.setLocalDate(ps, i++, b.getDueDate());
      ps.setBigDecimal(i++, b.getPreviousReading());
      ps.setBigDecimal(i++, b.getCurrentReading());
      ps.setBigDecimal(i++, b.getProviderConsumptionKwh());
      ps.setBigDecimal(i++, b.getProviderTotalAmount());
      ps.setBigDecimal(i++, b.getSealKwhReferencePrice());
      ps.setBigDecimal(i++, b.getCollectiveKwhPrice());
      ps.setString(i++, b.getStatus());
      ps.setString(i++, b.getNotes());
      JdbcSupport.setUuid(ps, i++, b.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, b.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, b.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ProviderBillDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM provider_bills WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ProviderBillDao.deleteById", e);
    }
  }
}
