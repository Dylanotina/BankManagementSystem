package com.example.BankManagementSystem.transactions;

import com.example.BankManagementSystem.account.Account;
import com.example.BankManagementSystem.account.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionsController {

    private final TransactionsRepository transactionsRepository;
    private final AccountRepository accountRepository;

    public TransactionsController(TransactionsRepository transactionsRepository, AccountRepository accountRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping(path = "/all")
    public List<Transactions> getAllTransactions(){
        return transactionsRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Transactions getTransactionById(@PathVariable long id){
        try {
            Optional<Transactions> optionalTransactions = transactionsRepository.findById(id);
            if (optionalTransactions.isPresent()){
                return optionalTransactions.get();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @GetMapping(path = "/account/{id}")
    public List<Transactions> getAllTransactionsByAccountId(@PathVariable long id){
       try{
           return transactionsRepository.findAllByAccountId(id);
       }catch (Exception e){
           throw new RuntimeException(e);
       }
    }

    @PostMapping
    public String createNewTransaction(@RequestBody Transactions transactions){
        System.out.println(transactions.toString());

        try {
            Optional<Account> optionalAccount = accountRepository.findById(transactions.getAccount().getId());
            if (optionalAccount.isPresent()){
               Account account = optionalAccount.get();
               List<Transactions> list = account.getTransactions();
               list.add(transactions);
               account.setTransactions(list);
               if (Objects.equals(transactions.getType(), "deposit")){
                   account.deposit(transactions.getValue());
               }else{
                   account.withdraw(transactions.getValue());
               }
               //accountRepository.save(account);
               //transactionsRepository.save(transactions);
                System.out.println(account);
            }else {
                return "Error while adding the new Transaction";
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return "Saved.";
    }

    @DeleteMapping(path = "/{id}")
    public String deleteTransaction(@PathVariable long id){
        try {
            transactionsRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return "deleted.";
    }


}
