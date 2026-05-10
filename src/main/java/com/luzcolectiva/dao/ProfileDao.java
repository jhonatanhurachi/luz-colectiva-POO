package com.luzcolectiva.dao;

import com.luzcolectiva.modelo.Profile;
import java.util.Optional;
import java.util.UUID;

public interface ProfileDao {

  Optional<Profile> findByUserId(UUID userId);

  void insert(Profile profile);

  int update(Profile profile);

  boolean deleteByUserId(UUID userId);
}
