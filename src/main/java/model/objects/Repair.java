package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.DataContext;

public class Repair {

    private String id;
    private String customerID;
    private Date repairDate;
    private Date predictedDate;
    private String location;
    private Double total;
    private Boolean isWarrantyAccepted;
    private Integer status;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Repair_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.Repair_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Repair_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Repair() {
    }

    public Repair(String id) {
        Details(id);
    }

    public Repair(String id, String customerID, Date repairDate, Date predictedDate, String location, Double total, Boolean isWarrantyAccepted, Integer status) {
        this.id = id;
        this.customerID = customerID;
        this.repairDate = repairDate;
        this.predictedDate = predictedDate;
        this.location = location;
        this.total = total;
        this.isWarrantyAccepted = isWarrantyAccepted;
        this.status = status;
    }

    public void Details(String id) {
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.customerID = rs.getString("customerID");
                    this.repairDate = rs.getDate("repairDate");
                    this.predictedDate = rs.getDate("predictedDate");
                    this.location = rs.getString("location");
                    this.total = rs.getDouble("total");
                    this.isWarrantyAccepted = rs.getBoolean("isWarrantyAccepted");
                    this.status = rs.getInt("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try (PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, customerID);
            stmt.setDate(3, new java.sql.Date(repairDate.getTime()));
            stmt.setDate(4, new java.sql.Date(predictedDate.getTime()));
            stmt.setString(5, location);
            stmt.setDouble(6, total);
            stmt.setBoolean(7, isWarrantyAccepted);
            stmt.setInt(8, status);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //setters and getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public Date getPredictedDate() {
        return predictedDate;
    }

    public void setPredictedDate(Date predictedDate) {
        this.predictedDate = predictedDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        total = total;
    }

    public Boolean getIsWarrantyAccepted() {
        return isWarrantyAccepted;
    }

    public void setIsWarrantyAccepted(Boolean isWarrantyAccepted) {
        this.isWarrantyAccepted = isWarrantyAccepted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        status = status;
    }
}