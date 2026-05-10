package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.ProviderBill;
import java.util.Optional;
import java.util.UUID;

public interface ProviderBillDao {

  Optional<ProviderBill> findById(UUID id);

  Optional<ProviderBill> findByPeriod(int year, int month);

  void insert(ProviderBill bill);

  int update(ProviderBill bill);

  boolean deleteById(UUID id);
}
