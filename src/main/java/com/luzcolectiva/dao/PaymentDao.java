package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.Payment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentDao {

  Optional<Payment> findById(UUID id);

  List<Payment> findAllOrderByDateDesc();

  List<Payment> findByReceiptId(UUID receiptId);

  void insert(Payment payment);

  int update(Payment payment);

  boolean deleteById(UUID id);
}
