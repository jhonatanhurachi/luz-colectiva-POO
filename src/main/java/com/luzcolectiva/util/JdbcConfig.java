package com.luzcolectiva.util;

import com.luzcolectiva.DaoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class JdbcConfig {

  private static final Properties PROPS = new Properties();

  static {
    var cl = JdbcConfig.class.getClassLoader();
    try (InputStream in =
        firstNonNull(cl.getResourceAsStream("application.properties"), cl.getResourceAsStream("application.properties.example"))) {
      if (in == null) {
        throw new IllegalStateException(
            "Falta application.properties (o application.properties.example) en src/main/resources.");
      }
      PROPS.load(in);
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private static InputStream firstNonNull(InputStream a, InputStream b) {
    return a != null ? a : b;
  }

  private JdbcConfig() {}

  public static Connection getConnection() throws SQLException {
    String url = PROPS.getProperty("db.url");
    String user = PROPS.getProperty("db.user");
    String password = PROPS.getProperty("db.password", "");
    return DriverManager.getConnection(url, user, password);
  }

  public static Connection connection() {
    try {
      return getConnection();
    } catch (SQLException e) {
      throw DaoException.wrap("obtener conexión JDBC", e);
    }
  }
}
