package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.CustomerDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.Customer;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerDaoJdbc implements CustomerDao {

  @Override
  public Optional<Customer> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM customers WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapCustomer(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerDao.findById", e);
    }
  }

  @Override
  public List<Customer> findAllOrderByName() {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM customers ORDER BY last_name ASC, first_name ASC");
        var rs = ps.executeQuery()) {
      List<Customer> list = new ArrayList<>();
      while (rs.next()) {
        list.add(RowMappers.mapCustomer(rs));
      }
      return list;
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerDao.findAllOrderByName", e);
    }
  }

  @Override
  public void insert(Customer customer) {
    if (customer.getId() == null) {
      customer.setId(UUID.randomUUID());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO customers (id, registration_number, first_name, last_name, document_number, "
                    + "phone, block, lot, address_reference, status, notes, created_by, updated_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, customer.getId());
      ps.setString(i++, customer.getRegistrationNumber());
      ps.setString(i++, customer.getFirstName());
      ps.setString(i++, customer.getLastName());
      ps.setString(i++, customer.getDocumentNumber());
      ps.setString(i++, customer.getPhone());
      ps.setString(i++, customer.getBlock());
      ps.setString(i++, customer.getLot());
      ps.setString(i++, customer.getAddressReference());
      ps.setString(i++, customer.getStatus());
      ps.setString(i++, customer.getNotes());
      JdbcSupport.setUuid(ps, i++, customer.getCreatedBy());
      JdbcSupport.setUuid(ps, i, customer.getUpdatedBy());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerDao.insert", e);
    }
  }

  @Override
  public int update(Customer customer) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE customers SET registration_number = ?, first_name = ?, last_name = ?, "
                    + "document_number = ?, phone = ?, block = ?, lot = ?, address_reference = ?, "
                    + "status = ?, notes = ?, created_by = ?, updated_by = ? WHERE id = ?")) {
      int i = 1;
      ps.setString(i++, customer.getRegistrationNumber());
      ps.setString(i++, customer.getFirstName());
      ps.setString(i++, customer.getLastName());
      ps.setString(i++, customer.getDocumentNumber());
      ps.setString(i++, customer.getPhone());
      ps.setString(i++, customer.getBlock());
      ps.setString(i++, customer.getLot());
      ps.setString(i++, customer.getAddressReference());
      ps.setString(i++, customer.getStatus());
      ps.setString(i++, customer.getNotes());
      JdbcSupport.setUuid(ps, i++, customer.getCreatedBy());
      JdbcSupport.setUuid(ps, i++, customer.getUpdatedBy());
      JdbcSupport.setUuid(ps, i, customer.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM customers WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("CustomerDao.deleteById", e);
    }
  }
}
