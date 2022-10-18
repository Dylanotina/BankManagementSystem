package com.example.BankManagementSystem.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "select * from client where client.email = ?1", nativeQuery = true)
   Optional<Client> findByEmail(String email);
}
