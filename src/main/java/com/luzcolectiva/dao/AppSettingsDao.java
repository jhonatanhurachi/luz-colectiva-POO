package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.AppSettings;
import java.util.Optional;

public interface AppSettingsDao {

  Optional<AppSettings> findSingleton();

  int update(AppSettings settings);
}
