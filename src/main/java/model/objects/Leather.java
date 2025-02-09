package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class Leather {

    private Integer id;
    private String colour;
    private String description;
    private String imagePath;
    private Boolean isAvailable;

    private static String detailsFunction  = "SELECT * FROM demeterboots.JobNotification_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.JobNotification_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.JobNotification_Commit(?, ?, ?, ?, ?)";
    private static String notNotifiedFunction  = "CALL demeterboots.JobNotification_NotNotified(?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Leather() {
    }

    public Leather(Integer id) {
        Details(id);
    }

    public Leather(Integer id, String colour, String description, String imagePath, Boolean isAvailable) {
        this.id = id;
        this.colour = colour;
        this.description = description;
        this.imagePath = imagePath;
        this.isAvailable = isAvailable;
    }

    public void Details(Integer id) {
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.colour = resultSet.getString("colour");
                    this.description = resultSet.getString("description");
                    this.imagePath = resultSet.getString("imagePath");
                    this.isAvailable = resultSet.getBoolean("isAvailable");
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setInt(1, id);
            statement.execute();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try(PreparedStatement statement = connection.prepareStatement(commitFunction)) {
            statement.setInt(1, id);
            statement.setString(2, colour);
            statement.setString(3, description);
            statement.setString(4, imagePath);
            statement.setBoolean(5, isAvailable);
            statement.execute();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //setters and getters
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
