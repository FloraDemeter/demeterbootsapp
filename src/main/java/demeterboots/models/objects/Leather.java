package demeterboots.models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import demeterboots.models.util.DataContext;
import demeterboots.models.util.exceptions.DatabaseException;
import demeterboots.models.util.exceptions.LeatherException;

public class Leather {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("colour")
    private String colour;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imagePath")
    private String imagePath;
    @JsonProperty("isAvailable")
    private Boolean isAvailable;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.Leather_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.Leather_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.Leather_Commit(?, ?, ?, ?, ?)";

    @JsonIgnore
    private final static DataContext dataContext;

    static {
        try {
            dataContext = getDataContext();
        } catch (DatabaseException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    @JsonIgnore
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

    @JsonIgnore
    public static List<Leather> getAllLeathers() throws LeatherException {
        return Details(0, null);
    }

    @JsonIgnore
    public static List<Leather> getAllAvailableLeathers() throws LeatherException {
        return Details(0, true);
    }

    @JsonIgnore
    public static final Leather getLeatherDetails(Integer id) throws LeatherException {
        List<Leather> leathers = Details(id, null);
        if (!leathers.isEmpty()) {
            return leathers.get(0);
        }

        return null;
    }

    @JsonIgnore
    public void deleteLeather() throws LeatherException {
        Delete();
    }

    @JsonIgnore
    public void commitLeather() throws LeatherException {
        Commit();
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
    
    private static List<Leather> Details(Integer id, Boolean isAvailable) throws LeatherException {
        List<Leather> leathers = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setObject(1, id == 0 ? null : id, java.sql.Types.INTEGER);
            statement.setObject(2, isAvailable, java.sql.Types.BOOLEAN);
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

    private void Delete() throws LeatherException{
        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setInt(1, id);
            statement.execute();
        }catch(SQLException e) {
            throw new LeatherException(String.format("Error deleting leather details with ID %s", id), e);
        }
    }

    private void Commit() throws LeatherException{
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