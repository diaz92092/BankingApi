package com.bankapi.dao;

import com.bankapi.exceptions.ClientCreationException;
import com.bankapi.exceptions.ClientNotFoundException;
import com.bankapi.exceptions.DatabaseException;
import com.bankapi.model.Client;
import com.bankapi.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientRepository {

    private Connection connection;

    public ClientRepository(Connection mockConn) {
        super();
    }

    public ClientRepository() {

    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public List<Client> getAllClients() throws DatabaseException {
        List<Client> listOfClients = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM clients";

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String zip  = rs.getString("zip");

                Client client = new Client(id, firstName, lastName, email,
                        phoneNumber, address, city, state, zip);
                listOfClients.add(client);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect: " + e.getMessage());
        }
        return listOfClients;
    }

    public Client getClientById(int id) throws ClientNotFoundException, DatabaseException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM clients c WHERE c.id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int clientId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String zip  = rs.getString("zip");

                return new Client(id, firstName, lastName, email,
                        phoneNumber, address, city, state, zip);
            }

            throw new ClientNotFoundException("Client with id: " + id + " was not found.");

        } catch (SQLException e) {
            throw new DatabaseException(("Error occurred with the database: " + e.getMessage()));
        }

    }

    public Client addClient(Client client) throws DatabaseException, ClientCreationException {

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO clients (id, firstName, lastName, email," +
                    " phoneNumber, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, client.getId());
            pstmt.setString(2, client.getFirstName());
            pstmt.setString(3, client.getLastName());
            pstmt.setString(4, client.getEmail());
            pstmt.setString(5, client.getPhoneNumber());
            pstmt.setString(6, client.getAddress());
            pstmt.setString(7, client.getCity());
            pstmt.setString(8, client.getState());
            pstmt.setString(9, client.getZip());

            int recordsModified = pstmt.executeUpdate();

            if (recordsModified != 1) {
                throw new ClientCreationException("Unable to add account to database.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
            } else {
                throw new ClientCreationException("No id generated when trying to add client. " +
                        "Client addition failed.");
            }

            connection.commit();
            return client;

        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database: " + e.getMessage());
        }
        }

    public void deleteClientById(int id) throws DatabaseException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "DELETE FROM clients WHERE id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            pstmt.executeQuery();

        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database: " + e.getMessage());
        }
    }

    public Client updateClientById(int id, Client client)
            throws DatabaseException, ClientNotFoundException {

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE clients " +
                    "SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, address = ?, " +
                    "city = ?," +
                    " state = ?, zip = ?" +
                    "WHERE id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, client.getFirstName());
                pstmt.setString(2, client.getLastName());
                pstmt.setString(3, client.getEmail());
                pstmt.setString(4, client.getPhoneNumber());
                pstmt.setString(5, client.getAddress());
                pstmt.setString(6, client.getCity());
                pstmt.setString(7, client.getState());
                pstmt.setString(8, client.getZip());
                pstmt.setInt(9, client.getId());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getNString("type");
                int balance = rs.getInt("balance");


                client = new Client();
                //firstName, lastName, email, phoneNumber, address, city, state, zip

            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database: " + e.getMessage());
        }
        return client;
    }



}
