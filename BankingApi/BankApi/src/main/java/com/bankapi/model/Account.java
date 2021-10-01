package com.bankapi.model;

import java.util.Objects;

public class Account {

    public int id;
    private String type;
    private int balance;
    private int clientId;

    public Account() {
        super();
    }

    public Account(int id, String type, int balance, int clientId) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Double.compare(account.balance, balance) == 0 && clientId == account.clientId && type.equals(account.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, balance, clientId);
    }


}
