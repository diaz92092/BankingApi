package com.bankapi.service;

import com.bankapi.exceptions.AccountCreationException;
import com.bankapi.exceptions.AccountNotFoundException;
import com.bankapi.exceptions.ClientNotFoundException;
import com.bankapi.exceptions.DatabaseException;
import com.bankapi.model.Account;
import com.bankapi.model.SecondAccount;


public class SecondAccountService {
    public SecondAccount addSecondAccountByClientId(int clientId, Account account) throws DatabaseException, AccountCreationException,
            ClientNotFoundException {

        SecondAccountService secondAccountRepository = new SecondAccountService();
        return  secondAccountRepository.addSecondAccountByClientId(clientId, account);
    }
    public SecondAccount updateAccountByIdForClientIdTransfer(int acctId, int clientId, int secondAcctId, SecondAccount secondAccount) throws DatabaseException,
            ClientNotFoundException, AccountNotFoundException {

        SecondAccountService secondAccountRepository = new SecondAccountService();
        return secondAccountRepository.updateAccountByIdForClientIdTransfer(acctId, clientId, secondAcctId, secondAccount);
    }
}
