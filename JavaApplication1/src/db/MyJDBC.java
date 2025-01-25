package db;

import java.sql.*;

public class MyJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/javajdbc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "04hukka*^*Huwa@";

    // Register user
    public static boolean register(String username, String password) {
        if (checkUser(username)) {
            return false; // Username already exists
        }

        

        String insertQuery = "INSERT INTO new_table (username, password) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement insertUser = connection.prepareStatement(insertQuery)) {

            insertUser.setString(1, username);
            insertUser.setString(2, password);

            insertUser.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if username exists
    public static boolean checkUser(String username) {
        String checkQuery = "SELECT * FROM new_table WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement checkUserExists = connection.prepareStatement(checkQuery)) {

            checkUserExists.setString(1, username);

            try (ResultSet resultSet = checkUserExists.executeQuery()) {
                return resultSet.isBeforeFirst(); // True if user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Assume user exists if there's an error
        }
    }

    // Validate login credentials
    public static boolean validateLogin(String username, String password) {
    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
        System.out.println("Invalid input: Username or password is empty.");
        return false;
    }

    String validateQuery = "SELECT * FROM new_table WHERE username = ? AND password = ?";
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        System.out.println("Database connected successfully for login validation.");

        try (PreparedStatement validateUser = connection.prepareStatement(validateQuery)) {
            validateUser.setString(1, username);
            validateUser.setString(2, password); // Assuming plain-text password

            System.out.println("Executing query: " + validateUser.toString());

            try (ResultSet resultSet = validateUser.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    System.out.println("Login successful for user: " + username);
                    return true; // Credentials are valid
                } else {
                    System.out.println("Invalid login credentials for user: " + username);
                    return false; // Invalid credentials
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("SQL Exception during login validation: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

}
