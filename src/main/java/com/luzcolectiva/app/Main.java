package com.luzcolectiva.app;

import com.luzcolectiva.gui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class Main {

  public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
        System.err.println("No se pudo configurar el LookAndFeel: " + ex.getMessage());
    }

    SwingUtilities.invokeLater(() -> {
        new LoginFrame().setVisible(true);
    });
  }

  private Main() {}
}
