package com.luzcolectiva.dao.impl;

import com.luzcolectiva.DaoException;
import com.luzcolectiva.dao.ReceiptItemDao;
import com.luzcolectiva.dao.jdbc.RowMappers;
import com.luzcolectiva.modelo.ReceiptItem;
import com.luzcolectiva.util.JdbcConfig;
import com.luzcolectiva.util.JdbcSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReceiptItemDaoJdbc implements ReceiptItemDao {

  @Override
  public Optional<ReceiptItem> findById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("SELECT * FROM receipt_items WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      try (var rs = ps.executeQuery()) {
        return rs.next() ? Optional.of(RowMappers.mapReceiptItem(rs)) : Optional.empty();
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptItemDao.findById", e);
    }
  }

  @Override
  public List<ReceiptItem> findByReceiptId(UUID receiptId) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "SELECT * FROM receipt_items WHERE receipt_id = ? ORDER BY sort_order ASC, id ASC")) {
      JdbcSupport.setUuid(ps, 1, receiptId);
      try (var rs = ps.executeQuery()) {
        List<ReceiptItem> list = new ArrayList<>();
        while (rs.next()) {
          list.add(RowMappers.mapReceiptItem(rs));
        }
        return list;
      }
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptItemDao.findByReceiptId", e);
    }
  }

  @Override
  public void insert(ReceiptItem item) {
    if (item.getId() == null) {
      item.setId(UUID.randomUUID());
    }
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "INSERT INTO receipt_items (id, receipt_id, concept, description, quantity, unit_price, "
                    + "amount, item_type, sort_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, item.getId());
      JdbcSupport.setUuid(ps, i++, item.getReceiptId());
      ps.setString(i++, item.getConcept());
      ps.setString(i++, item.getDescription());
      ps.setBigDecimal(i++, item.getQuantity());
      ps.setBigDecimal(i++, item.getUnitPrice());
      ps.setBigDecimal(i++, item.getAmount());
      ps.setString(i++, item.getItemType());
      ps.setInt(i, item.getSortOrder());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptItemDao.insert", e);
    }
  }

  @Override
  public int update(ReceiptItem item) {
    try (var c = JdbcConfig.getConnection();
        var ps =
            c.prepareStatement(
                "UPDATE receipt_items SET receipt_id = ?, concept = ?, description = ?, quantity = ?, "
                    + "unit_price = ?, amount = ?, item_type = ?, sort_order = ? WHERE id = ?")) {
      int i = 1;
      JdbcSupport.setUuid(ps, i++, item.getReceiptId());
      ps.setString(i++, item.getConcept());
      ps.setString(i++, item.getDescription());
      ps.setBigDecimal(i++, item.getQuantity());
      ps.setBigDecimal(i++, item.getUnitPrice());
      ps.setBigDecimal(i++, item.getAmount());
      ps.setString(i++, item.getItemType());
      ps.setInt(i++, item.getSortOrder());
      JdbcSupport.setUuid(ps, i, item.getId());
      return ps.executeUpdate();
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptItemDao.update", e);
    }
  }

  @Override
  public boolean deleteById(UUID id) {
    try (var c = JdbcConfig.getConnection();
        var ps = c.prepareStatement("DELETE FROM receipt_items WHERE id = ?")) {
      JdbcSupport.setUuid(ps, 1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      throw DaoException.wrap("ReceiptItemDao.deleteById", e);
    }
  }
}
