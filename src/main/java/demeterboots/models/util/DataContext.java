package demeterboots.models.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import demeterboots.models.objects.Employee;
import demeterboots.models.util.exceptions.DatabaseException;

public final class DataContext {
    
    private static DataContext instance;
    private Connection connection;

    public Employee currentUser;

    private DataContext() throws DatabaseException {
        Properties properties = GetProperties();

        try {
                connection = DriverManager.getConnection(
                properties.getProperty("spring.datasource.url"),
                properties.getProperty("spring.datasource.username"),
                properties.getProperty("spring.datasource.password"));
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            throw new DatabaseException("Connection to the database failed!", e);
        }
    }

    public static DataContext getInstance() throws DatabaseException {
        if (instance == null) {
            instance = new DataContext();
        }
        return instance;
    }

    public Properties GetProperties() throws DatabaseException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new DatabaseException("Failed to load application.properties!");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new DatabaseException("Failed to load application.properties!", ex);
        }
        return properties;
    }

    public Connection getConnection() throws DatabaseException {
        return connection;
    }

    public void closeConnection() throws DatabaseException {
        try {
            connection.close();
            System.out.println("Connection to the database closed successfully!");
        } catch (SQLException e) {
            throw new DatabaseException("Failed to close the connection to the database!", e);
        }
    }
}
