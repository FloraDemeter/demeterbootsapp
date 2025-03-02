package demeterboots.models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import demeterboots.models.util.DataContext;
import demeterboots.models.util.exceptions.DatabaseException;
import demeterboots.models.util.exceptions.OrderException;

public class Order {

    @JsonProperty("id")
    private String id;
    @JsonProperty("customerID")
    private String customerID;
    @JsonProperty("orderDate")
    private Date orderDate;
    @JsonProperty("predictedFinishDate")
    private Date predictedFinishDate;
    @JsonProperty("location")
    private String location;
    @JsonProperty("total")
    private double total;
    @JsonProperty("isWarrantyAccepted")
    private String isWarrantyAccepted; 
    @JsonProperty("status")
    private String status; 

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.Order_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.Order_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.Order_Commit(?, ?, ?, ?, ?, ?, ?, ?)";

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

    public Order() {
    }

    public Order(String id) throws OrderException {
        Order order;

        if (id == null) {
            return;
    }

        order = getOrderDetails(id);

        if (order != null) {
            this.id = order.id;
            this.customerID = order.id;
            this.orderDate = order.orderDate;
            this.predictedFinishDate = order.predictedFinishDate;
            this.location = order.location;
            this.total = order.total;
            this.isWarrantyAccepted = order.isWarrantyAccepted;
            this.status = order.status;
        }
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

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public List<Order> getAllOrders() throws OrderException {
        return Details("","");
    }

    @JsonIgnore
    public List<Order> getAllOrdersForCustomer(String customerID) throws OrderException {
        return Details("", customerID);
    }

    @JsonIgnore
    public final Order getOrderDetails(String id) throws OrderException {
        List<Order> orders = Details(id,"");
        if (!orders.isEmpty()) {
            return orders.get(0);
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

//region Database methods
//--------------------------------------------------------

    public List<Order> Details(String id, String customerID) throws OrderException{
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    this.id = rs.getString("id");
                    this.customerID = rs.getString("customerID");
                    this.orderDate = rs.getDate("orderDate");
                    this.predictedFinishDate = rs.getDate("predictedFinishDate");
                    this.location = rs.getString("location");
                    this.total = rs.getDouble("total");
                    this.isWarrantyAccepted = rs.getString("isWarrantyAccepted");
                    this.status = rs.getString("status");
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new OrderException(String.format("Error fetching order details for ID %s", id), e);
            }
            if (customerID != null) {
                throw new OrderException(String.format("Error fetching order details for customer %s", customerID), e);
            }
            throw new OrderException("Error fetching order details", e);
        }
        return orders;
    }

    public void Delete() throws OrderException {
        if (id == null) {
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (Exception e) {
            throw new OrderException(String.format("Error deleted order with ID %s", id), e);
        }
    }

    public void Commit() throws OrderException{
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
            throw new OrderException(String.format("Error updating order with ID %s", id), e);
        }
    }
//--------------------------------------------------------
//endregion

//region Getters and setters
//--------------------------------------------------------

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
//--------------------------------------------------------
//endregion