package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerDao {

  Optional<Customer> findById(UUID id);

  List<Customer> findAllOrderByName();

  void insert(Customer customer);

  int update(Customer customer);

  boolean deleteById(UUID id);
}
