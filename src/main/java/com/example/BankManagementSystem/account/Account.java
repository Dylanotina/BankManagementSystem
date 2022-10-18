package com.example.BankManagementSystem.account;


import com.example.BankManagementSystem.client.Client;
import com.example.BankManagementSystem.transactions.Transactions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="account")
public class Account {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) long id;

    @JsonIgnoreProperties(value = {"accounts"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private float balance;
    private String type;
    @OneToMany(targetEntity = Transactions.class, mappedBy = "account", fetch = FetchType.EAGER)
    private List<Transactions> transactions;

    public Account(Client client, String type) {
        this.client = client;
        this.balance = 0;
        this.type = type;
        this.transactions = new ArrayList<>();

    }

    public Account() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void withdraw(float value){
        this.balance -= value;
    }
    public void deposit(float value){
        this.balance += value;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
