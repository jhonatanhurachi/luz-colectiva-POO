package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ExtraChargeDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.ExtraCharge;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExtraChargeDaoJdbc implements ExtraChargeDao {

  @Override
  public Optional<ExtraCharge> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM extra_charges WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapExtraCharge(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeDao.findById", e);
    }
  }

  @Override
  public List<ExtraCharge> findAllOrderByName() {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM extra_charges ORDER BY name ASC");
        var rs = ps.executeQuery()) {
      List<ExtraCharge> list = new ArrayList<>();
      while (rs.next()) {
        list.add(RowMappers.mapExtraCharge(rs));
      }
      return list;
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeDao.findAllOrderByName", e);
    }
  }

  @Override
  public void insert(ExtraCharge charge) {
    if (charge.getId() == null) {
      charge.setId(UUID.randomUUID());
    }
    if (charge.getStartDate() == null) {
      charge.setStartDate(LocalDate.now());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO extra_charges (id, name, description, amount, start_date, due_date, status, "
                    + "created_by, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, charge.getId());
      ps.setString(i++, charge.getName());
      ps.setString(i++, charge.getDescription());
      ps.setBigDecimal(i++, charge.getAmount());
      JdbcSupport.setLocalDate(ps, i++, charge.getStartDate());
      JdbcSupport.setLocalDate(ps, i++, charge.getDueDate());
      ps.setString(i++, charge.getStatus());
      JdbcSupport.setUuid(ps, i++, charge.getCreatedBy());
      JdbcSupport.setUuid(ps, i, charge.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeDao.insert", e);
    }
  }

  @Override
  public int update(ExtraCharge charge) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE extra_charges SET name = ?, description = ?, amount = ?, start_date = ?, due_date = ?, "
                    + "status = ?, created_by = ?, updated_by = ? WHERE id = ?")) {
      int i = 1;
      ps.setString(i++, charge.getName());
      ps.setString(i++, charge.getDescription());
      ps.setBigDecimal(i++, charge.getAmount());
      JdbcSupport.setLocalDate(ps, i++, charge.getStartDate());
      JdbcSupport.setLocalDate(ps, i++, charge.getDueDate());
      ps.setString(i++, charge.getStatus());
      JdbcSupport.setUuid(ps, i++, charge.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, charge.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, charge.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM extra_charges WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ExtraChargeDao.deleteById", e);
    }
  }
}
