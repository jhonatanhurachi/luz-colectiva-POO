package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.MeterReading;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeterReadingDao {

  Optional<MeterReading> findById(UUID id);

  List<MeterReading> findAllOrderByPeriodDesc();

  List<MeterReading> findByCustomerId(UUID customerId);

  void insert(MeterReading reading);

  int update(MeterReading reading);

  boolean deleteById(UUID id);
}
