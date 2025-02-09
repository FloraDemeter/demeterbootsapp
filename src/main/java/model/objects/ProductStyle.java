package model.objects;

import java.sql.*;

import model.DataContext;

public class ProductStyle {

    private Integer id;
    private Integer productStyleTypeID;
    private String description;

    private static String detailsFunction  = "SELECT * FROM demeterboots.ProductStyle_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.ProductStyle_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.ProductStyle_Commit(?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public ProductStyle() {
    }

    public ProductStyle(Integer id){
        Details(id);
    }

    public ProductStyle(Integer id, Integer productStyleTypeID, String description) {
        this.id = id;
        this.productStyleTypeID = productStyleTypeID;
        this.description = description;
    }

    public void Details(Integer id) {
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.productStyleTypeID = resultSet.getInt("productStyleTypeID");
                    this.description = resultSet.getString("description");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try(PreparedStatement statement = connection.prepareStatement(commitFunction)) {
            statement.setInt(1, id);
            statement.setInt(2, productStyleTypeID);
            statement.setString(3, description);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductStyleTypeID() {
        return productStyleTypeID;
    }

    public void setProductStyleTypeID(Integer productStyleTypeID) {
        this.productStyleTypeID = productStyleTypeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
