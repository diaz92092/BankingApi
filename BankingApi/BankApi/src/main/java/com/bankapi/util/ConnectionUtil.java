package com.bankapi.util;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.mariadb.jdbc.Driver;

public class ConnectionUtil {
    private static Connection conn;
    private ConnectionUtil(){ }

    public static Connection getConnection() throws SQLException {

        Driver mariaDBDriver = new Driver();
        DriverManager.registerDriver(mariaDBDriver);

        try {
            Properties props = new Properties();
            FileReader connectionProperties = new FileReader("src/main/resources/connection.properties");
            props.load(connectionProperties);


            String connectionString = "jdbc:mariadb://" +
                    props.getProperty("endpoint") + ":" +
                    props.getProperty("port") + "/" +
                    props.getProperty("dbname") + "?user=" +
                    props.getProperty("username") + "&password=" +
                    props.getProperty("password");


           conn = DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO: MAKE ME BETTER!
        }
        return conn;
    }

}
