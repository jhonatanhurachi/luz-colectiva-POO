package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.MeterReadingDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.MeterReading;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MeterReadingDaoJdbc implements MeterReadingDao {

  @Override
  public Optional<MeterReading> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM meter_readings WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapMeterReading(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("MeterReadingDao.findById", e);
    }
  }

  @Override
  public List<MeterReading> findByCustomerId(UUID customerId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM meter_readings WHERE customer_id = ? "
                    + "ORDER BY period_year DESC, period_month DESC")) {
      JdbcSupport.setUuid(ps, 1, customerId);
      try (var rs = ps.executeQuery()) {
        List<MeterReading> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapMeterReading(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("MeterReadingDao.findByCustomerId", e);
    }
  }

  @Override
  public void insert(MeterReading r) {
    if (r.getId() == null) {
      r.setId(UUID.randomUUID());
    }
    if (r.getReadingDate() == null) {
      r.setReadingDate(LocalDate.now());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO meter_readings (id, customer_id, period_year, period_month, previous_reading, "
                    + "current_reading, reading_date, notes, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, r.getId());
      JdbcSupport.setUuid(ps, i++, r.getCustomerId());
      ps.setInt(i++, r.getPeriodYear());
      ps.setInt(i++, r.getPeriodMonth());
      ps.setBigDecimal(i++, r.getPreviousReading());
      ps.setBigDecimal(i++, r.getCurrentReading());
      JdbcSupport.setLocalDate(ps, i++, r.getReadingDate());
      ps.setString(i++, r.getNotes());
      JdbcSupport.setUuid(ps, i++, r.getCreatedBy());
      JdbcSupport.setUuid(ps, i, r.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("MeterReadingDao.insert", e);
    }
  }

  @Override
  public int update(MeterReading r) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE meter_readings SET customer_id = ?, period_year = ?, period_month = ?, "
                    + "previous_reading = ?, current_reading = ?, reading_date = ?, notes = ?, "
                    + "created_by = ?, updated_by = ? WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, r.getCustomerId());
      ps.setInt(i++, r.getPeriodYear());
      ps.setInt(i++, r.getPeriodMonth());
      ps.setBigDecimal(i++, r.getPreviousReading());
      ps.setBigDecimal(i++, r.getCurrentReading());
      JdbcSupport.setLocalDate(ps, i++, r.getReadingDate());
      ps.setString(i++, r.getNotes());
      JdbcSupport.setUuid(ps, i++, r.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, r.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, r.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("MeterReadingDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM meter_readings WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("MeterReadingDao.deleteById", e);
    }
  }
}
