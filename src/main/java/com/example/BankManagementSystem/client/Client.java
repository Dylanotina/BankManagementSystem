package com.example.BankManagementSystem.client;

import com.example.BankManagementSystem.account.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client implements Serializable {
    private @Id @GeneratedValue(strategy = GenerationType.AUTO) long id;
    private String name;
    private String adress;
    private String email;
    private String password;
    private Date dateOfBirth;

    @OneToMany(targetEntity = Account.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "client")

    private Set<Account> accounts = new HashSet<>();

    public Client() {
    }

    public Client(String name, String adress, String email, String password, Date dateOfBirth) {
        this.name = name;
        this.adress = adress;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", accounts=" + accounts +
                '}';
    }
}
