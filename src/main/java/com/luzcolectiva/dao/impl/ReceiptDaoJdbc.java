package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ReceiptDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.Receipt;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReceiptDaoJdbc implements ReceiptDao {

  @Override
  public Optional<Receipt> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM receipts WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapReceipt(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptDao.findById", e);
    }
  }

  @Override
  public List<Receipt> findAllOrderByPeriod() {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM receipts ORDER BY period_year DESC, period_month DESC, customer_id ASC");
        var rs = ps.executeQuery()) {
      List<Receipt> list = new ArrayList<>();
      while (rs.next()) {
        list.add(RowMappers.mapReceipt(rs));
      }
      return list;
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptDao.findAllOrderByPeriod", e);
    }
  }

  @Override
  public List<Receipt> findByCustomerId(UUID customerId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM receipts WHERE customer_id = ? "
                    + "ORDER BY period_year DESC, period_month DESC")) {
      JdbcSupport.setUuid(ps, 1, customerId);
      try (var rs = ps.executeQuery()) {
        List<Receipt> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapReceipt(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptDao.findByCustomerId", e);
    }
  }

  @Override
  public void insert(Receipt r) {
    if (r.getId() == null) {
      r.setId(UUID.randomUUID());
    }
    if (r.getIssueDate() == null) {
      r.setIssueDate(LocalDate.now());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO receipts (id, customer_id, meter_reading_id, provider_bill_id, receipt_number, "
                    + "period_year, period_month, issue_date, due_date, subtotal, total, paid_amount, balance, "
                    + "seal_kwh_reference_price, collective_kwh_price, status, notes, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, r.getId());
      JdbcSupport.setUuid(ps, i++, r.getCustomerId());
      JdbcSupport.setUuid(ps, i++, r.getMeterReadingId());
      JdbcSupport.setUuid(ps, i++, r.getProviderBillId());
      ps.setString(i++, r.getReceiptNumber());
      ps.setInt(i++, r.getPeriodYear());
      ps.setInt(i++, r.getPeriodMonth());
      JdbcSupport.setLocalDate(ps, i++, r.getIssueDate());
      JdbcSupport.setLocalDate(ps, i++, r.getDueDate());
      ps.setBigDecimal(i++, r.getSubtotal());
      ps.setBigDecimal(i++, r.getTotal());
      ps.setBigDecimal(i++, r.getPaidAmount());
      ps.setBigDecimal(i++, r.getBalance());
      ps.setBigDecimal(i++, r.getSealKwhReferencePrice());
      ps.setBigDecimal(i++, r.getCollectiveKwhPrice());
      ps.setString(i++, r.getStatus());
      ps.setString(i++, r.getNotes());
      JdbcSupport.setUuid(ps, i++, r.getCreatedBy());
      JdbcSupport.setUuid(ps, i, r.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptDao.insert", e);
    }
  }

  @Override
  public int update(Receipt r) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE receipts SET customer_id = ?, meter_reading_id = ?, provider_bill_id = ?, "
                    + "receipt_number = ?, period_year = ?, period_month = ?, issue_date = ?, due_date = ?, "
                    + "subtotal = ?, total = ?, paid_amount = ?, balance = ?, seal_kwh_reference_price = ?, "
                    + "collective_kwh_price = ?, status = ?, notes = ?, created_by = ?, updated_by = ? "
                    + "WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, r.getCustomerId());
      JdbcSupport.setUuid(ps, i++, r.getMeterReadingId());
      JdbcSupport.setUuid(ps, i++, r.getProviderBillId());
      ps.setString(i++, r.getReceiptNumber());
      ps.setInt(i++, r.getPeriodYear());
      ps.setInt(i++, r.getPeriodMonth());
      JdbcSupport.setLocalDate(ps, i++, r.getIssueDate());
      JdbcSupport.setLocalDate(ps, i++, r.getDueDate());
      ps.setBigDecimal(i++, r.getSubtotal());
      ps.setBigDecimal(i++, r.getTotal());
      ps.setBigDecimal(i++, r.getPaidAmount());
      ps.setBigDecimal(i++, r.getBalance());
      ps.setBigDecimal(i++, r.getSealKwhReferencePrice());
      ps.setBigDecimal(i++, r.getCollectiveKwhPrice());
      ps.setString(i++, r.getStatus());
      ps.setString(i++, r.getNotes());
      JdbcSupport.setUuid(ps, i++, r.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, r.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, r.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM receipts WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptDao.deleteById", e);
    }
  }
}
