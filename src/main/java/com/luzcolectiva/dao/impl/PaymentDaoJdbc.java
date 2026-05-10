package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.PaymentDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.Payment;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PaymentDaoJdbc implements PaymentDao {

  @Override
  public Optional<Payment> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM payments WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapPayment(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("PaymentDao.findById", e);
    }
  }

  @Override
  public List<Payment> findByReceiptId(UUID receiptId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM payments WHERE receipt_id = ? ORDER BY payment_date DESC, id DESC")) {
      JdbcSupport.setUuid(ps, 1, receiptId);
      try (var rs = ps.executeQuery()) {
        List<Payment> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapPayment(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("PaymentDao.findByReceiptId", e);
    }
  }

  @Override
  public void insert(Payment p) {
    if (p.getId() == null) {
      p.setId(UUID.randomUUID());
    }
    if (p.getPaymentDate() == null) {
      p.setPaymentDate(LocalDate.now());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO payments (id, receipt_id, customer_id, payment_date, amount, payment_method, "
                    + "reference_number, notes, status, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, p.getId());
      JdbcSupport.setUuid(ps, i++, p.getReceiptId());
      JdbcSupport.setUuid(ps, i++, p.getCustomerId());
      JdbcSupport.setLocalDate(ps, i++, p.getPaymentDate());
      ps.setBigDecimal(i++, p.getAmount());
      ps.setString(i++, p.getPaymentMethod());
      ps.setString(i++, p.getReferenceNumber());
      ps.setString(i++, p.getNotes());
      ps.setString(i++, p.getStatus());
      JdbcSupport.setUuid(ps, i++, p.getCreatedBy());
      JdbcSupport.setUuid(ps, i, p.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("PaymentDao.insert", e);
    }
  }

  @Override
  public int update(Payment p) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE payments SET receipt_id = ?, customer_id = ?, payment_date = ?, amount = ?, "
                    + "payment_method = ?, reference_number = ?, notes = ?, status = ?, created_by = ?, "
                    + "updated_by = ? WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, p.getReceiptId());
      JdbcSupport.setUuid(ps, i++, p.getCustomerId());
      JdbcSupport.setLocalDate(ps, i++, p.getPaymentDate());
      ps.setBigDecimal(i++, p.getAmount());
      ps.setString(i++, p.getPaymentMethod());
      ps.setString(i++, p.getReferenceNumber());
      ps.setString(i++, p.getNotes());
      ps.setString(i++, p.getStatus());
      JdbcSupport.setUuid(ps, i++, p.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, p.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, p.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("PaymentDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM payments WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("PaymentDao.deleteById", e);
    }
  }
}
