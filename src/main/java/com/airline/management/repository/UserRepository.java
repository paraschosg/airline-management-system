package com.airline.management.repository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}