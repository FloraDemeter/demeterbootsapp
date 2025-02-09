package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class ProductType {
    
    private Integer id;
    private String description;

    private static String detailsFunction  = "SELECT * FROM demeterboots.ProductType_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.ProductType_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.ProductType_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public ProductType() {
    }

    public ProductType(Integer id) {
        Details(id);
    }

    public ProductType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public void Details(Integer id) {
        try { PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.description = resultSet.getString("description");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try { PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try { PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setInt(1, id);
            statement.setString(2, description);
            statement.execute();
        } catch (SQLException e) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}