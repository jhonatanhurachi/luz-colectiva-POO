package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ProfileDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.Profile;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class ProfileDaoJdbc implements ProfileDao {

  @Override
  public Optional<Profile> findByUserId(UUID userId) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM profiles WHERE user_id = ?")) {
      JdbcSupport.setUuid(ps, 1, userId);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapProfile(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ProfileDao.findByUserId", e);
    }
  }

  @Override
  public void insert(Profile profile) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO profiles (user_id, role, status) VALUES (?, ?, ?)")) {
      JdbcSupport.setUuid(ps, 1, profile.getUserId());
      ps.setString(2, profile.getRole());
      ps.setString(3, profile.getStatus());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ProfileDao.insert", e);
    }
  }

  @Override
  public int update(Profile profile) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("UPDATE profiles SET role = ?, status = ? WHERE user_id = ?")) {
      ps.setString(1, profile.getRole());
      ps.setString(2, profile.getStatus());
      JdbcSupport.setUuid(ps, 3, profile.getUserId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ProfileDao.update", e);
    }
  }

  @Override
  public boolean deleteByUserId(UUID userId) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM profiles WHERE user_id = ?")) {
      JdbcSupport.setUuid(ps, 1, userId);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ProfileDao.deleteByUserId", e);
    }
  }
}
