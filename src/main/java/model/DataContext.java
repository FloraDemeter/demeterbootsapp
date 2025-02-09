package model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DataContext {
    
    private static DataContext instance;
    private Connection connection;

    private DataContext() {
        Properties properties = GetProperties();

        try {
            connection = DriverManager.getConnection(
                properties.getProperty("spring.datasource.url"),
                properties.getProperty("spring.datasource.username"),
                properties.getProperty("spring.datasource.password"));
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Connection to the database failed!");
            e.printStackTrace();
        }
    }

    public static DataContext getInstance() {
        if (instance == null) {
            try {
                instance = new DataContext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public Properties GetProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return properties;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection to the database closed successfully!");
        } catch (SQLException e) {
            System.out.println("Connection to the database failed to close!");
            e.printStackTrace();
        }
    }
}
