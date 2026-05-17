package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.ProviderBill;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProviderBillDao {

  Optional<ProviderBill> findById(UUID id);

  List<ProviderBill> findAllOrderByPeriodDesc();

  Optional<ProviderBill> findByPeriod(int year, int month);

  void insert(ProviderBill bill);

  int update(ProviderBill bill);

  boolean deleteById(UUID id);
}
