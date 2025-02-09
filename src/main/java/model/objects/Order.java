package model.objects;

import java.sql.*;
import java.util.Date;

import model.DataContext;

public class Order {

    private String id;
    private String customerID;
    private Date orderDate;
    private Date predictedFinishDate;
    private String location;
    private double total;
    private String isWarrantyAccepted; 
    private String status; 

    private static String detailsFunction  = "SELECT * FROM demeterboots.Order_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.Order_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Order_Commit(?, ?, ?, ?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Order() {
    }

    public Order(String id) {
        Details(id);
    }

    public Order(String id, String customerID, Date orderDate, Date predictedFinishDate, String location, double total, String isWarrantyAccepted, String status) {
        this.id = id;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.predictedFinishDate = predictedFinishDate;
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
                    this.orderDate = rs.getDate("orderDate");
                    this.predictedFinishDate = rs.getDate("predictedFinishDate");
                    this.location = rs.getString("location");
                    this.total = rs.getDouble("total");
                    this.isWarrantyAccepted = rs.getString("isWarrantyAccepted");
                    this.status = rs.getString("status");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try (PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, this.id);
            stmt.setString(2, this.customerID);
            stmt.setObject(3, this.orderDate);
            stmt.setObject(4, this.predictedFinishDate);
            stmt.setString(5, this.location);
            stmt.setDouble(6, this.total);
            stmt.setString(7, this.isWarrantyAccepted);
            stmt.setString(8, this.status);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getter and Setter methods
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPredictedFinishDate() {
        return predictedFinishDate;
    }

    public void setPredictedFinishDate(Date predictedFinishDate) {
        this.predictedFinishDate = predictedFinishDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getIsWarrantyAccepted() {
        return isWarrantyAccepted;
    }

    public void setIsWarrantyAccepted(String isWarrantyAccepted) {
        this.isWarrantyAccepted = isWarrantyAccepted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}