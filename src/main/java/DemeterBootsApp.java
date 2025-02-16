
import models.util.DataContext;
import models.util.exceptions.DatabaseException;

public class DemeterBootsApp {
    public static void main(String[] args) throws DatabaseException {
        try {
            DataContext dataContext = DataContext.getInstance();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error connecting to the database", e);
        }

        //start the application
        
    }
}