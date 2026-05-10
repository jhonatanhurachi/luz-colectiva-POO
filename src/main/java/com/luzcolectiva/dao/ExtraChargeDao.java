package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.ExtraCharge;
import java.util.Optional;
import java.util.UUID;

public interface ExtraChargeDao {

  Optional<ExtraCharge> findById(UUID id);

  void insert(ExtraCharge charge);

  int update(ExtraCharge charge);

  boolean deleteById(UUID id);
}
