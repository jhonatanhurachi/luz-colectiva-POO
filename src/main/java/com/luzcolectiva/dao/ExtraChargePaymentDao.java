package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.ExtraChargePayment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExtraChargePaymentDao {

  Optional<ExtraChargePayment> findById(UUID id);

  List<ExtraChargePayment> findAllOrderByDateDesc();

  List<ExtraChargePayment> findByAssignmentId(UUID assignmentId);

  void insert(ExtraChargePayment payment);

  int update(ExtraChargePayment payment);

  boolean deleteById(UUID id);
}
