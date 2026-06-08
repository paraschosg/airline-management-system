package com.airline.management.repository;

import com.airline.management.model.User;
import com.airline.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByAfm(String afm);

  Optional<User> findByIdentityNumber(String identityNumber);

  Optional<User> findByUsernameAndPassword(
          String username,
          String password
  );

  boolean existsByUsername(String username);

  List<User> findByRole(Role role);

  List<User> findByLastNameContainingIgnoreCase(String lastName);

  List<User> findByUsernameContainingIgnoreCase(String username);

  List<User>findAll();

}