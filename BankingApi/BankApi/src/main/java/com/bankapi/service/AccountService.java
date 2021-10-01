package com.bankapi.service;

import com.bankapi.dao.ClientRepository;
import com.bankapi.exceptions.AccountCreationException;
import com.bankapi.exceptions.AccountNotFoundException;
import com.bankapi.exceptions.ClientNotFoundException;
import com.bankapi.exceptions.DatabaseException;
import com.bankapi.model.Account;
import com.bankapi.controller.ClientController;

import com.bankapi.dao.AccountRepository;

import java.sql.SQLException;
import java.util.List;


public class AccountService {

    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    public ClientService clientService;
    public ClientController clientController;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public List<Account> getAllAccounts() throws DatabaseException {
        return this.accountRepository.getAllAccounts();
    }

    public Account getAccountById(int id) throws AccountNotFoundException, DatabaseException {
        return accountRepository.getAccountById(id);
    }

    public Account addAccount(Account account) throws DatabaseException, AccountCreationException {
        return accountRepository.addAccount(account);
    }

    public Account addAccountByClientId(int clientId, Account account) throws DatabaseException, AccountCreationException,
            ClientNotFoundException {

        return  accountRepository.addAccountByClientId(clientId, account);
    }

    public List<Account> getAllAccountsByClientId(int clientId) throws DatabaseException, ClientNotFoundException,
            SQLException {

        return accountRepository.getAllAccountsByClientId(clientId);
    }

    public List<Account> getAllAccountsByClientIdWithBalance(int clientId, int X, int Y) throws DatabaseException,
            ClientNotFoundException,
            SQLException {

        return accountRepository.getAllAccountsByClientIdWithBalance(clientId, X, Y);
    }

    public Account getAccountByIdForClientId(int acctId, int clientId) throws DatabaseException,
            ClientNotFoundException,AccountNotFoundException {

        return accountRepository.getAccountByIdForClientId(acctId, clientId);
    }

    public Account updateAccountByIdForClientId(int acctId, int clientId, Account account) throws DatabaseException,
            ClientNotFoundException, AccountNotFoundException {

        return accountRepository.updateAccountByIdForClientId(acctId, clientId, account);
    }

    public Account deleteAccountByIdForClientId(int acctId, int clientId) throws Exception {

        return accountRepository.deleteAccountByIdForClientId(acctId, clientId);
    }

    public Account updateAccountByIdForClientIdDeposit(int acctId, int clientId, Account account) throws DatabaseException,
            ClientNotFoundException, AccountNotFoundException {

        return accountRepository.updateAccountByIdForClientIdDeposit(acctId, clientId, account);
    }



}

