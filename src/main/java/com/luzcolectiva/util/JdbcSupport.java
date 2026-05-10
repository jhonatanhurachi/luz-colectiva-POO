package com.luzcolectiva.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.sql.Timestamp;
import java.util.UUID;

public final class JdbcSupport {

  private JdbcSupport() {}

  public static UUID uuidOrNull(ResultSet rs, String column) throws SQLException {
    String s = rs.getString(column);
    if (rs.wasNull() || s == null || s.isEmpty()) {
      return null;
    }
    return UUID.fromString(s);
  }

  public static void setUuid(PreparedStatement ps, int index, UUID value) throws SQLException {
    if (value == null) {
      ps.setNull(index, Types.CHAR);
    } else {
      ps.setString(index, value.toString());
    }
  }

  public static LocalDate getLocalDate(ResultSet rs, String column) throws SQLException {
    var d = rs.getDate(column);
    return d == null ? null : d.toLocalDate();
  }

  public static void setLocalDate(PreparedStatement ps, int index, LocalDate value) throws SQLException {
    if (value == null) {
      ps.setNull(index, Types.DATE);
    } else {
      ps.setObject(index, value);
    }
  }

  public static OffsetDateTime getOffsetDateTime(ResultSet rs, String column) throws SQLException {
    Timestamp t = rs.getTimestamp(column);
    return t == null ? null : t.toInstant().atOffset(ZoneOffset.UTC);
  }

  public static void setOffsetDateTime(PreparedStatement ps, int index, OffsetDateTime value)
      throws SQLException {
    if (value == null) {
      ps.setNull(index, Types.TIMESTAMP);
    } else {
      ps.setTimestamp(index, Timestamp.from(value.toInstant()));
    }
  }
}
