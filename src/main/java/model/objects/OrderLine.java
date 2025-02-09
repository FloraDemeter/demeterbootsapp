package model.objects;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class OrderLine {

    private String id;
    private String orderId;
    private Integer productTypeID;
    private Integer leatherID;
    private String productStyle; //this is a JSONB object
    private Double price;
    private String notes;

    private static String detailsFunction  = "SELECT * FROM demeterboots.OrderLine_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.OrderLine_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.OrderLine_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public OrderLine() {
    }

    public OrderLine(String id) {
        Details(id);
    }

    public OrderLine(String id, String orderId, Integer productTypeID, Integer leatherID, String productStyle, Double price, String notes) {
        this.id = id;
        this.orderId = orderId;
        this.productTypeID = productTypeID;
        this.leatherID = leatherID;
        this.productStyle = productStyle;
        this.price = price;
        this.notes = notes;
    }

    public void Details(String id) {
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.orderId = rs.getString("orderId");
                    this.productTypeID = rs.getInt("productTypeID");
                    this.leatherID = rs.getInt("leatherID");
                    this.productStyle = rs.getString("productStyle");
                    this.price = rs.getDouble("price");
                    this.notes = rs.getString("notes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try (CallableStatement stmt = connection.prepareCall(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try (CallableStatement stmt = connection.prepareCall(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, orderId);
            stmt.setInt(3, productTypeID);
            stmt.setInt(4, leatherID);
            stmt.setString(5, productStyle);
            stmt.setDouble(6, price);
            stmt.setString(7, notes);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //format the json object properly

    //setters and getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public Integer getLeatherID() {
        return leatherID;
    }

    public void setLeatherID(Integer leatherID) {
        this.leatherID = leatherID;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
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