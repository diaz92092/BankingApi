package com.bankapi.model;

import java.util.Objects;

public class SecondAccount extends Account{
    public int id;
    private String type;
    private int balance;
    private int clientId;

    public SecondAccount() {
        super();
    }

    public SecondAccount(int id, String type, int balance, int clientId) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", clientId=" + clientId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, type, balance, clientId);
    }


}


