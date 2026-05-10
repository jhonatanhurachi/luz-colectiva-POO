package com.luzcolectiva.app;

import com.luzcolectiva.DaoException;

public final class Main {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Luz Colectiva " + Runtime.version());
      System.out.println();
      ListCli.mostrarAyuda();
      System.out.println();
      System.out.println("Ejemplo: mvn exec:java -Dexec.args=clientes");
      return;
    }

    try {
      ListCli.ejecutar(args);
    } catch (DaoException e) {
      System.err.println("Error de base de datos");
      System.err.println(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
      System.exit(1);
    }
  }

  private Main() {}
}
