package com.example.BankManagementSystem.transactions;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    @Query(value = "select * from transactions where transactions.account_id = ?1", nativeQuery = true)
    List<Transactions> findAllByAccountId(long client_id);
}
