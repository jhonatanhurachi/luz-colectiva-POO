package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

  List<User> findAllOrderByName();

  Optional<User> findById(UUID id);

  Optional<User> findByEmail(String email);

  void insert(User user);

  int update(User user);

  boolean deleteById(UUID id);
}
