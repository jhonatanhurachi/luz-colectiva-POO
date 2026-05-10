package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ExtraChargeAssignmentDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.ExtraChargeAssignment;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExtraChargeAssignmentDaoJdbc implements ExtraChargeAssignmentDao {

  @Override
  public Optional<ExtraChargeAssignment> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM extra_charge_assignments WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapExtraChargeAssignment(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeAssignmentDao.findById", e);
    }
  }

  @Override
  public List<ExtraChargeAssignment> findByExtraChargeId(UUID extraChargeId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM extra_charge_assignments WHERE extra_charge_id = ? ORDER BY id ASC")) {
      JdbcSupport.setUuid(ps, 1, extraChargeId);
      try (var rs = ps.executeQuery()) {
        List<ExtraChargeAssignment> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapExtraChargeAssignment(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeAssignmentDao.findByExtraChargeId", e);
    }
  }

  @Override
  public void insert(ExtraChargeAssignment a) {
    if (a.getId() == null) {
      a.setId(UUID.randomUUID());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO extra_charge_assignments (id, extra_charge_id, customer_id, amount, paid_amount, "
                    + "balance, status, created_by, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, a.getId());
      JdbcSupport.setUuid(ps, i++, a.getExtraChargeId());
      JdbcSupport.setUuid(ps, i++, a.getCustomerId());
      ps.setBigDecimal(i++, a.getAmount());
      ps.setBigDecimal(i++, a.getPaidAmount());
      ps.setBigDecimal(i++, a.getBalance());
      ps.setString(i++, a.getStatus());
      JdbcSupport.setUuid(ps, i++, a.getCreatedBy());
      JdbcSupport.setUuid(ps, i, a.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeAssignmentDao.insert", e);
    }
  }

  @Override
  public int update(ExtraChargeAssignment a) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE extra_charge_assignments SET extra_charge_id = ?, customer_id = ?, amount = ?, "
                    + "paid_amount = ?, balance = ?, status = ?, created_by = ?, updated_by = ? WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, a.getExtraChargeId());
      JdbcSupport.setUuid(ps, i++, a.getCustomerId());
      ps.setBigDecimal(i++, a.getAmount());
      ps.setBigDecimal(i++, a.getPaidAmount());
      ps.setBigDecimal(i++, a.getBalance());
      ps.setString(i++, a.getStatus());
      JdbcSupport.setUuid(ps, i++, a.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, a.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, a.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeAssignmentDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM extra_charge_assignments WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeAssignmentDao.deleteById", e);
    }
  }
}
