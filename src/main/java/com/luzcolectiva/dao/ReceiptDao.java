package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.Receipt;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReceiptDao {

  Optional<Receipt> findById(UUID id);

  List<Receipt> findAllOrderByPeriod();

  List<Receipt> findByCustomerId(UUID customerId);

  void insert(Receipt receipt);

  int update(Receipt receipt);

  boolean deleteById(UUID id);
}
