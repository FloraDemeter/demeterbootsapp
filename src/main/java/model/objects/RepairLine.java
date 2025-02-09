package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class RepairLine {
    private String id;
    private String repairID;
    private Integer productTypeID;
    private Integer repairCategoryID;
    private Double price;
    private String notes; 

    private static String detailsFunction  = "SELECT * FROM demeterboots.RepairLine_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.RepairLine_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.RepairLine_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public RepairLine() {
    }

    public RepairLine(String id) {
        Details(id);
    }

    public RepairLine(String id, String repairID, Integer productTypeID, Integer repairCategoryID, Double price, String notes) {
        this.id = id;
        this.repairID = repairID;
        this.productTypeID = productTypeID;
        this.repairCategoryID = repairCategoryID;
        this.price = price;
        this.notes = notes;
    }

    public void Details(String id) {
        try {PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setString(1, id);
            try(ResultSet reader = statement.executeQuery()) {
                if (reader.next()) {
                    this.id = reader.getString("id");
                    this.repairID = reader.getString("repairID");
                    this.productTypeID = reader.getInt("productTypeID");
                    this.repairCategoryID = reader.getInt("repairCategoryID");
                    this.price = reader.getDouble("price");
                    this.notes = reader.getString("notes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try {PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try {PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setString(1, id);
            statement.setString(2, repairID);
            statement.setInt(3, productTypeID);
            statement.setInt(4, repairCategoryID);
            statement.setDouble(5, price);
            statement.setString(6, notes);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public Integer getRepairCategoryID() {
        return repairCategoryID;
    }

    public void setRepairCategoryID(Integer repairCategoryID) {
        this.repairCategoryID = repairCategoryID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}