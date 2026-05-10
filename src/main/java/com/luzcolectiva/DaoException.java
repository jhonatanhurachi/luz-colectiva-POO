package com.luzcolectiva;

import java.sql.SQLException;

/** Error al acceder a la base de datos */
public class DaoException extends RuntimeException {

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoException(String message) {
    super(message);
  }

  public static DaoException wrap(String operation, SQLException e) {
    return new DaoException("Error en operación: " + operation, e);
  }
}
