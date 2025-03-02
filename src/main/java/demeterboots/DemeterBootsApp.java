package demeterboots;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import demeterboots.models.util.DataContext;
import demeterboots.models.util.exceptions.DatabaseException;

@SpringBootApplication
@ComponentScan(basePackages = "demeterboots")
public class DemeterBootsApp {

    private static final Logger logger = LoggerFactory.getLogger(DemeterBootsApp.class);

    public static void main(String[] args) throws DatabaseException {
        try {
            logger.info("Starting application...");
            DataContext dataContext = DataContext.getInstance();
            logger.info("DataContext initialized.");
            SpringApplication.run(DemeterBootsApp.class, args);
            logger.info("Application started.");
        } catch (DatabaseException e) {
            throw new DatabaseException("Error connecting to the database", e);
        }

        //start the application
        
    }
}