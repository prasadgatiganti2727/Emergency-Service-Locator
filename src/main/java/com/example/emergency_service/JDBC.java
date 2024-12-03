package com.example.emergency_service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {

    public Connection getConnection() {
        Connection databaseLink = null;

        try {
            databaseLink = DriverManager.getConnection(
                    "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false",
                    "fp510",
                    "510"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return databaseLink;
    }

    public static void main(String[] args) {
        JDBC connectNow = new JDBC();
        Connection connectDB = connectNow.getConnection();

        if (connectDB != null) {
            try {
                Statement statement = connectDB.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
                while (resultSet.next()) {
                    System.out.println("Username: " + resultSet.getString("username"));
                    System.out.println("Password: " + resultSet.getString("password"));

                }

                String siUsername = "suma";
                String siPassword = "suma";

                ResultSet resultSet1 = statement.executeQuery(
                        "SELECT count(1) FROM USERS WHERE username = '" + siUsername + "' AND password = '" + siPassword + "'"
                );

                if (resultSet1.next() && resultSet1.getInt(1) > 0) {
                    System.out.println("User authenticatedd successfully.");
                } else {
                    System.out.println("Invalid username or password.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connectDB.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Failed to establish a database connection.");
        }
    }
}

