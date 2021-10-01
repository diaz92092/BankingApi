package com.bankapi.dao;

import com.bankapi.exceptions.AccountNotFoundException;
import com.bankapi.exceptions.ClientNotFoundException;
import com.bankapi.exceptions.DatabaseException;
import com.bankapi.model.SecondAccount;
import com.bankapi.util.ConnectionUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecondAccountRepository {

    public SecondAccount updateSecondAccountByIdForClientIdTransfer(int acctId, int clientId, int secondAcctId, SecondAccount secondAccount) throws ClientNotFoundException,
            AccountNotFoundException, DatabaseException {


        try (Connection connection = ConnectionUtil.getConnection()){

            String sql = "UPDATE accounts SET balance=balance-500, WHERE id=? AND client_id=?"+
                    "UPDATE accounts SET balance=balance+500 WHERE id=? AND client_id=?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, acctId);
            pstmt.setInt(2, clientId);
            pstmt.setInt(3, acctId);
            pstmt.setInt(4, clientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getNString("type");
                int balance = rs.getInt("balance");

                secondAccount = new SecondAccount(secondAcctId, type, balance, clientId);

            }

            Logger logger = null;
            logger.info("Account with id: " + acctId + " found for Client with id: " + clientId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return secondAccount;

    }

    public SecondAccount getSecondAccountById(int parseInt) {
    return null;}

/*
    public SecondAccount getSecondAccountById(int parseInt) {
        public Account addAccountByClientId(int clientId, Account account) throws ClientNotFoundException,
                DatabaseException, AccountCreationException {
            try (Connection connection = ConnectionUtil.getConnection()) {

                String sql = "INSERT INTO accounts (account_type, balance, client_id)" +
                        "VALUES (?, ?, ?)";


                PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, account.getType());
                pstmt.setDouble(2, account.getBalance());
                pstmt.setInt(3, account.getClientId());

                int recordsAdded = pstmt.executeUpdate();

                if (recordsAdded != 1) {
                    throw new AccountCreationException("No new account created.");
                }

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getInt(1));
                } else {
                    throw new AccountCreationException("No id generated when trying to add account. Account addition failed.");
                }

                logger.info("Account successfully added.");

            } catch (SQLException | AccountCreationException e) {
                e.printStackTrace();
            }
            return account;
        }
*/
}

