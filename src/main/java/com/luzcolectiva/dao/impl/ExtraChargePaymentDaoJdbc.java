package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ExtraChargePaymentDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.ExtraChargePayment;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExtraChargePaymentDaoJdbc implements ExtraChargePaymentDao {

  @Override
  public Optional<ExtraChargePayment> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM extra_charge_payments WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapExtraChargePayment(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargePaymentDao.findById", e);
    }
  }

  @Override
  public List<ExtraChargePayment> findAllOrderByDateDesc() {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM extra_charge_payments ORDER BY payment_date DESC, id DESC");
        var rs = ps.executeQuery()) {
      List<ExtraChargePayment> list = new ArrayList<>();
      while (rs.next()) {
        list.add(RowMappers.mapExtraChargePayment(rs));
      }
      return list;
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargePaymentDao.findAllOrderByDateDesc", e);
    }
  }

  @Override
  public List<ExtraChargePayment> findByAssignmentId(UUID assignmentId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM extra_charge_payments WHERE extra_charge_assignment_id = ? "
                    + "ORDER BY payment_date DESC, id DESC")) {
      JdbcSupport.setUuid(ps, 1, assignmentId);
      try (var rs = ps.executeQuery()) {
        List<ExtraChargePayment> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapExtraChargePayment(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargePaymentDao.findByAssignmentId", e);
    }
  }

  @Override
  public void insert(ExtraChargePayment p) {
    if (p.getId() == null) {
      p.setId(UUID.randomUUID());
    }
    if (p.getPaymentDate() == null) {
      p.setPaymentDate(LocalDate.now());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO extra_charge_payments (id, extra_charge_assignment_id, extra_charge_id, customer_id, "
                    + "payment_date, amount, payment_method, reference_number, notes, status, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, p.getId());
      JdbcSupport.setUuid(ps, i++, p.getExtraChargeAssignmentId());
      JdbcSupport.setUuid(ps, i++, p.getExtraChargeId());
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
      throw DaoException.wrap("ExtraChargePaymentDao.insert", e);
    }
  }

  @Override
  public int update(ExtraChargePayment p) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE extra_charge_payments SET extra_charge_assignment_id = ?, extra_charge_id = ?, "
                    + "customer_id = ?, payment_date = ?, amount = ?, payment_method = ?, reference_number = ?, "
                    + "notes = ?, status = ?, created_by = ?, updated_by = ? WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, p.getExtraChargeAssignmentId());
      JdbcSupport.setUuid(ps, i++, p.getExtraChargeId());
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
      throw DaoException.wrap("ExtraChargePaymentDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM extra_charge_payments WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargePaymentDao.deleteById", e);
    }
  }
}
