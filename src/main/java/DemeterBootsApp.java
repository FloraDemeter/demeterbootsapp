import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DemeterBootsApp {
    public static void main(String[] args) {
        // Load application properties
        Properties properties = new Properties();
        try (InputStream input = DemeterBootsApp.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Database connection details
        String url = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");

        // Connect to the database
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database successfully!");
            // Perform database operations here
        } catch (SQLException e) {
            System.out.println("Connection to the database failed!");
            e.printStackTrace();
        }
    }
}