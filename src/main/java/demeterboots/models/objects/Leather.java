package demeterboots.models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demeterboots.models.util.DataContext;
import demeterboots.models.util.exceptions.DatabaseException;
import demeterboots.models.util.exceptions.LeatherException;

public class Leather {

    private Integer id;
    private String colour;
    private String description;
    private String imagePath;
    private Boolean isAvailable;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Leather_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.Leather_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Leather_Commit(?, ?, ?, ?, ?)";

    private final static DataContext dataContext;

    static {
        try {
            dataContext = getDataContext();
        } catch (DatabaseException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    private static Connection connection;

    static {
        try {
            connection = getConnection();
        } catch (DatabaseException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

//region Constructors
//--------------------------------------------------------
    
    public Leather() {
    }

    public Leather(Integer id) throws LeatherException{
        Leather leather;
        
        if (id <= 0) {
            return;
        }

        leather = getLeatherDetails(id);

        if (leather != null) {
            this.id = leather.id;
            this.colour = leather.colour;
            this.description = leather.description;
            this.imagePath = leather.imagePath;
            this.isAvailable = leather.isAvailable;
        }
    }

    public Leather(Integer id, String colour, String description, String imagePath, Boolean isAvailable) {
        this.id = id;
        this.colour = colour;
        this.description = description;
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
    }

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    public List<Leather> getAllLeathers() throws LeatherException {
        return Details(null);
    }

    public final Leather getLeatherDetails(Integer id) throws LeatherException {
        List<Leather> leathers = Details(id);
        if (!leathers.isEmpty()) {
            return leathers.get(0);
        }

        return null;
    }

    private static DataContext getDataContext() throws DatabaseException {
        return DataContext.getInstance();
    }

    private static Connection getConnection () throws DatabaseException {
        return dataContext.getConnection();
    }

//--------------------------------------------------------
//endregion


//region Database Methods
//--------------------------------------------------------
    
    public final List<Leather> Details(Integer id) throws LeatherException {
        List<Leather> leathers = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leather leather = new Leather();
                    leather.id = resultSet.getInt("id");
                    leather.colour = resultSet.getString("colour");
                    leather.description = resultSet.getString("description");
                    leather.imagePath = resultSet.getString("imagePath");
                    leather.isAvailable = resultSet.getBoolean("isAvailable");
                    leathers.add(leather);
                }
            }
        }catch(SQLException e) {
            
            if (id != null) {
                throw new LeatherException(String.format("Error getting leather details for ID %s", id), e);
            }
            throw new LeatherException("Error getting leather details", e);
        }

        return leathers;
    }

    public void Delete() throws LeatherException{
        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setInt(1, id);
            statement.execute();
        }catch(SQLException e) {
            throw new LeatherException(String.format("Error deleting leather details with ID %s", id), e);
        }
    }

    public void Commit() throws LeatherException{
        try(PreparedStatement statement = connection.prepareStatement(commitFunction)) {
            statement.setInt(1, id);
            statement.setString(2, colour);
            statement.setString(3, description);
            statement.setString(4, imagePath);
            statement.setBoolean(5, isAvailable);
            statement.execute();
        }catch(SQLException e) {
            throw new LeatherException(String.format("Error committing leather details with ID %s", id), e);
        }
    }

//--------------------------------------------------------
//endregion

//region Getters and Setters
//--------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
//--------------------------------------------------------
//endregion