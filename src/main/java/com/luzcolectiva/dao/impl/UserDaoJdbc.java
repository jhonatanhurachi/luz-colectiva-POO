package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.UserDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.User;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDaoJdbc implements UserDao {

  @Override
  public List<User> findAllOrderByName() {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM users ORDER BY full_name ASC, email ASC");
        var rs = ps.executeQuery()) {
      List<User> list = new ArrayList<>();
      while (rs.next()) {
        list.add(RowMappers.mapUser(rs));
      }
      return list;
    } catch (SQLException e) {
      throw DaoException.wrap("UserDao.findAllOrderByName", e);
    }
  }

  @Override
  public Optional<User> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM users WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapUser(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("UserDao.findById", e);
    }
  }

  @Override
  public Optional<User> findByEmail(String email) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM users WHERE email = ?")) {
      ps.setString(1, email);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapUser(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("UserDao.findByEmail", e);
    }
  }

  @Override
  public void insert(User user) {
    if (user.getId() == null) {
      user.setId(UUID.randomUUID());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO users (id, full_name, email, phone) VALUES (?, ?, ?, ?)")) {
      JdbcSupport.setUuid(ps, 1, user.getId());
      ps.setString(2, user.getFullName());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getPhone());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("UserDao.insert", e);
    }
  }

  @Override
  public int update(User user) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE users SET full_name = ?, email = ?, phone = ? WHERE id = ?")) {
      ps.setString(1, user.getFullName());
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getPhone());
      JdbcSupport.setUuid(ps, 4, user.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("UserDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM users WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("UserDao.deleteById", e);
    }
  }
}
