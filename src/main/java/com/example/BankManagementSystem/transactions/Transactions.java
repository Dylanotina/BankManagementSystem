package com.example.BankManagementSystem.transactions;

import com.example.BankManagementSystem.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transactions {
    private @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;

    @JsonIgnoreProperties(value = {"transactions"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private String type;
    private float value;

    public Transactions(Account id_account1, String type, float value) {
        this.account = id_account1;
        this.type = type;
        this.value = value;
    }
    public Transactions(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account id_account1) {
        this.account = id_account1;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", account=" + account +
                ", type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}

