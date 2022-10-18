package com.example.BankManagementSystem.account;

import com.example.BankManagementSystem.client.Client;
import com.example.BankManagementSystem.client.ClientRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/account")
public class AccountController {

    @Autowired
    private final AccountRepository accountRepository;
    private  final ClientRepository clientRepository;

    public AccountController(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping(path = "/all")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Account getAccountById(@PathVariable long id) {
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);
            if (accountOptional.isPresent()){
                return accountOptional.get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @GetMapping(path = "/client/{id}")
    public List<Account> getAccountsByClientId(@PathVariable long id){
        try{
            return accountRepository.findAllByClientId(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PostMapping
    public String createNewAccount(@RequestBody  Account account){
        System.out.println(account.toString());
        try {
            Optional<Client> optionalClient = clientRepository.findById(account.getClient().getId());
            if(optionalClient.isPresent()){
             Set<Account> accounts = optionalClient.get().getAccounts();
             accounts.add(account);
             optionalClient.get().setAccounts(accounts);
             accountRepository.save(account);
            }else{
                return "Error while creating the new Account.";
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return "saved.";
    }

    @PutMapping
    public Account updateAccount(@PathVariable long id, Account updatedAccount){
        try{
            Optional<Account> optionalAccount = accountRepository.findById(id);
            if (optionalAccount.isPresent()){
                optionalAccount.map(newAccount -> {
                    newAccount.setBalance(updatedAccount.getBalance());
                    newAccount.setTransactions(updatedAccount.getTransactions());
                    return accountRepository.save(newAccount);
                });
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }
    @DeleteMapping(path = "/{id}")
    public String deleteAccount(@PathVariable long id){
        try {
            accountRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return "deleted.";
    }
}


