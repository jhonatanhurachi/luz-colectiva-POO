package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.AppSettingsDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.AppSettings;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.Optional;

public class AppSettingsDaoJdbc implements AppSettingsDao {

  @Override
  public Optional<AppSettings> findSingleton() {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM app_settings WHERE id = 1")) {
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapAppSettings(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("AppSettingsDao.findSingleton", e);
    }
  }

  @Override
  public int update(AppSettings s) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE app_settings SET organization_name = ?, currency_code = ?, energy_unit_label = ?, "
                    + "energy_unit_price = ?, minimum_payment = ?, technical_payment = ?, printing_cost = ?, "
                    + "reconnection_fee = ?, default_due_days = ?, rounding_enabled = ?, rounding_increment = ?, "
                    + "rounding_mode = ?, collective_contract_number = ?, seal_kwh_price = ?, "
                    + "collective_kwh_price = ?, location_label = ?, updated_by = ? WHERE id = ?")) {
      ps.setString(1, s.getOrganizationName());
      ps.setString(2, s.getCurrencyCode());
      ps.setString(3, s.getEnergyUnitLabel());
      ps.setBigDecimal(4, s.getEnergyUnitPrice());
      ps.setBigDecimal(5, s.getMinimumPayment());
      ps.setBigDecimal(6, s.getTechnicalPayment());
      ps.setBigDecimal(7, s.getPrintingCost());
      ps.setBigDecimal(8, s.getReconnectionFee());
      ps.setInt(9, s.getDefaultDueDays());
      ps.setBoolean(10, s.isRoundingEnabled());
      ps.setBigDecimal(11, s.getRoundingIncrement());
      ps.setString(12, s.getRoundingMode());
      ps.setString(13, s.getCollectiveContractNumber());
      ps.setBigDecimal(14, s.getSealKwhPrice());
      ps.setBigDecimal(15, s.getCollectiveKwhPrice());
      ps.setString(16, s.getLocationLabel());
      JdbcSupport.setUuid(ps, 17, s.getUpdatedBy());
      ps.setInt(18, s.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("AppSettingsDao.update", e);
    }
  }
}
