package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.ExtraChargeAssignment;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExtraChargeAssignmentDao {

  Optional<ExtraChargeAssignment> findById(UUID id);

  List<ExtraChargeAssignment> findAllOrderById();

  List<ExtraChargeAssignment> findByExtraChargeId(UUID extraChargeId);

  void insert(ExtraChargeAssignment assignment);

  int update(ExtraChargeAssignment assignment);

  boolean deleteById(UUID id);
}
