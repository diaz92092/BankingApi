package com.bankapi.dao;

import com.bankapi.exceptions.AccountCreationException;
import com.bankapi.exceptions.AccountNotFoundException;
import com.bankapi.exceptions.ClientNotFoundException;
import com.bankapi.exceptions.DatabaseException;
import com.bankapi.model.Account;
import com.bankapi.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    private Connection connection;
    private ClientRepository clientRepository;

    public AccountRepository() {
        super();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Account> getAllAccounts() throws DatabaseException {
        List<Account> listOfAccounts = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM accounts";

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int accountId = rs.getInt("id");
                String type = rs.getNString("account_type");
                int balance = rs.getInt("balance");
                int clientId = rs.getInt("client_id");

                Account account = new Account(accountId, type, balance, clientId);
                listOfAccounts.add(account);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect: " + e.getMessage());
        }
        return listOfAccounts;
    }

    public Account getAccountById(int id) throws AccountNotFoundException, DatabaseException {

        Account account = new Account();

        try (Connection connection = ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM accounts a WHERE a.id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int accountId = rs.getInt("id");
                String type = rs.getNString("account_type");
                int balance = rs.getInt("balance");
                int clientId = rs.getInt("client_id");

                account = new Account(accountId, type, balance, clientId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred with the database: " + e.getMessage());
        }

        return account;
    }

    public Account addAccount(Account account) throws DatabaseException, AccountCreationException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO accounts (account_type, balance, client_id) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, account.getType());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getClientId());

            int recordsModified = pstmt.executeUpdate();

            if (recordsModified !=1){
                throw new AccountCreationException("Unable to add account to database");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()){
                account.setId(generatedKeys.getInt(1));
            } else {
                throw new AccountCreationException("No id generated when trying to add account. Account addition failed.");
            }

            return account;


        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database: " + e.getMessage());
        }
    }

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

    public List<Account> getAllAccountsByClientId(int clientId) throws ClientNotFoundException,
            DatabaseException, SQLException {

        List<Account> listOfAccounts = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM accounts WHERE client_id=?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, clientId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("id");
                String type = rs.getNString("account_type");
                int balance = rs.getInt("balance");

                Account account = new Account(accountId, type, balance, clientId);
                listOfAccounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfAccounts;

    }

    public List<Account> getAllAccountsByClientIdWithBalance(int clientId, int X, int Y) throws ClientNotFoundException, DatabaseException,
            SQLException {

        List<Account> listOfAccounts = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE client_id = ? HAVING balance < ? AND balance > ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, X);
            pstmt.setInt(3, Y);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int accountId = rs.getInt("id");
                String type = rs.getNString("account_type");
                int balance = rs.getInt("balance");

                Account account = new Account(accountId, type, balance, clientId);
                listOfAccounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfAccounts;
        }

    public Account getAccountByIdForClientId(int acctId, int clientId) throws ClientNotFoundException,
            AccountNotFoundException, DatabaseException {

        Account account = new Account();

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM accounts WHERE id=? AND client_id=?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, acctId);
            pstmt.setInt(2, clientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getNString("account_type");
                int balance = rs.getInt("balance");


                account = new Account(acctId, type, balance, clientId);
            }

            logger.info("Account with id: " + acctId + " found for Client with id: " + clientId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }


    public Account updateAccountByIdForClientId(int acctId, int clientId, Account account) throws ClientNotFoundException,
            AccountNotFoundException, DatabaseException {


        try (Connection connection = ConnectionUtil.getConnection()){

            String sql = "UPDATE accounts SET account_type=?, balance=? WHERE id=? AND client_id=?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, account.getType());
            pstmt.setInt(2, account.getBalance());
            pstmt.setInt(3, acctId);
            pstmt.setInt(4, clientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getNString("type");
                int balance = rs.getInt("balance");

                account = new Account(acctId, type, balance, clientId);

            }

            logger.info("Account with id: " + acctId + " found for Client with id: " + clientId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;

    }

    public Account deleteAccountByIdForClientId(int acctId, int clientId) throws ClientNotFoundException,
            AccountNotFoundException, DatabaseException {

        Account account = new Account();

        try (Connection connection = ConnectionUtil.getConnection()) {

            String sql = "DELETE FROM accounts WHERE id=? and client_id=?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, acctId);
            pstmt.setInt(2, clientId);

            int deletedAccount = pstmt.executeUpdate();

            if (deletedAccount != 1){
                throw new DatabaseException("Unable to delete account from database");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
    public Account updateAccountByIdForClientIdDeposit(int acctId, int clientId, Account account) throws ClientNotFoundException,
            AccountNotFoundException, DatabaseException {


        try (Connection connection = ConnectionUtil.getConnection()){

            String sql = "UPDATE accounts SET account_type=?, balance=? WHERE id=? AND client_id=?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, account.getType());
            pstmt.setInt(2, account.getBalance());
            pstmt.setInt(3, acctId);
            pstmt.setInt(4, clientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getNString("type");
                int balance = rs.getInt("balance");

                account = new Account(acctId, type, balance, clientId);

            }

            logger.info("Account with id: " + acctId + " found for Client with id: " + clientId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;

    }

}
