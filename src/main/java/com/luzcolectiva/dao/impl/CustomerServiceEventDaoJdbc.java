package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.CustomerServiceEventDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.CustomerServiceEvent;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import com.luzcolectiva.validation.CustomerServiceEventRules;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerServiceEventDaoJdbc implements CustomerServiceEventDao {

  @Override
  public Optional<CustomerServiceEvent> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM customer_service_events WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapCustomerServiceEvent(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerServiceEventDao.findById", e);
    }
  }

  @Override
  public List<CustomerServiceEvent> findByCustomerId(UUID customerId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM customer_service_events WHERE customer_id = ? "
                    + "ORDER BY event_date DESC, id DESC")) {
      JdbcSupport.setUuid(ps, 1, customerId);
      try (var rs = ps.executeQuery()) {
        List<CustomerServiceEvent> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapCustomerServiceEvent(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerServiceEventDao.findByCustomerId", e);
    }
  }

  @Override
  public void insert(CustomerServiceEvent e) {
    if (e.getId() == null) {
      e.setId(UUID.randomUUID());
    }
    if (e.getEventDate() == null) {
      e.setEventDate(LocalDate.now());
    }
    CustomerServiceEventRules.validate(e);
    if (e.getAmount() == null) {
      e.setAmount(BigDecimal.ZERO);
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO customer_service_events (id, customer_id, event_type, event_date, reason, amount, "
                    + "charge_status, receipt_id, notes, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, e.getId());
      JdbcSupport.setUuid(ps, i++, e.getCustomerId());
      ps.setString(i++, e.getEventType());
      JdbcSupport.setLocalDate(ps, i++, e.getEventDate());
      ps.setString(i++, e.getReason());
      ps.setBigDecimal(i++, e.getAmount());
      ps.setString(i++, e.getChargeStatus());
      JdbcSupport.setUuid(ps, i++, e.getReceiptId());
      ps.setString(i++, e.getNotes());
      JdbcSupport.setUuid(ps, i++, e.getCreatedBy());
      JdbcSupport.setUuid(ps, i, e.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException ex) {
      throw DaoException.wrap("CustomerServiceEventDao.insert", ex);
    }
  }

  @Override
  public int update(CustomerServiceEvent e) {
    CustomerServiceEventRules.validate(e);
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE customer_service_events SET customer_id = ?, event_type = ?, event_date = ?, reason = ?, "
                    + "amount = ?, charge_status = ?, receipt_id = ?, notes = ?, created_by = ?, updated_by = ? "
                    + "WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, e.getCustomerId());
      ps.setString(i++, e.getEventType());
      JdbcSupport.setLocalDate(ps, i++, e.getEventDate());
      ps.setString(i++, e.getReason());
      ps.setBigDecimal(i++, e.getAmount());
      ps.setString(i++, e.getChargeStatus());
      JdbcSupport.setUuid(ps, i++, e.getReceiptId());
      ps.setString(i++, e.getNotes());
      JdbcSupport.setUuid(ps, i++, e.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, e.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, e.getId());
      return ps.executeUpdate();
    } catch (SQLException ex) {
      throw DaoException.wrap("CustomerServiceEventDao.update", ex);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM customer_service_events WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerServiceEventDao.deleteById", e);
    }
  }
}
