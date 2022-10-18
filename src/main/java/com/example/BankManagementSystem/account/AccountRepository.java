package com.example.BankManagementSystem.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query(value = "select * from account where account.client_id = ?1", nativeQuery = true)
    List<Account> findAllByClientId( long client_id);
}
