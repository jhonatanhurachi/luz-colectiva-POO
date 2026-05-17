package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.CustomerServiceEvent;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerServiceEventDao {

  Optional<CustomerServiceEvent> findById(UUID id);

  List<CustomerServiceEvent> findAllOrderByDateDesc();

  List<CustomerServiceEvent> findByCustomerId(UUID customerId);

  void insert(CustomerServiceEvent event);

  int update(CustomerServiceEvent event);

  boolean deleteById(UUID id);
}
