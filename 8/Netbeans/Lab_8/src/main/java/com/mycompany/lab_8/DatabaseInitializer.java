/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab_8;

/**
 *
 * @author rje24
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void initializeDatabase() {
        String databaseName = "ConferenceDB";
        String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String useDatabaseQuery = "USE " + databaseName;
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Attendees (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "full_name VARCHAR(255), " +
                "email VARCHAR(255) UNIQUE, " +
                "contact_number VARCHAR(15), " +
                "country VARCHAR(100))";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createDatabaseQuery);
            statement.executeUpdate(useDatabaseQuery);
            statement.executeUpdate(createTableQuery);
            System.out.println("Database and table initialized.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

