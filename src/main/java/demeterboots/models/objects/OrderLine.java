package demeterboots.models.objects;

import java.sql.CallableStatement;
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
import demeterboots.models.util.exceptions.OrderLineException;;

public class OrderLine {

    @JsonProperty("id")
    private String id;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("productTypeID")
    private Integer productTypeID;
    @JsonProperty("leatherID")
    private Integer leatherID;
    @JsonProperty("productStyle")
    private String productStyle; //this is a JSONB object
    @JsonProperty("price")
    private Double price;
    @JsonProperty("notes")
    private String notes;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.OrderLine_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.OrderLine_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.OrderLine_Commit(?, ?, ?, ?, ?, ?)";

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

    private static DataContext getDataContext() throws DatabaseException {
        return DataContext.getInstance();
    }

    private static Connection getConnection () throws DatabaseException {
        return dataContext.getConnection();
    }

//region Constructors
//--------------------------------------------------------

    public OrderLine() {
    }

    public OrderLine(String id) throws OrderLineException{
        OrderLine line;

        if (id == null) {
            return;
        }

        line = getOrderLineDetails(id);
        
        if (line != null) {
            this.id = line.id;
            this.orderId = line.orderId;
            this.productTypeID = line.productTypeID;
            this.leatherID = line.leatherID;
            this.productStyle = line.productStyle;
            this.price = line.price;
            this.notes = line.notes;
        }
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

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public List<OrderLine> getAllOrderLines() throws OrderLineException {
        return Details("","");
    }

    @JsonIgnore
    public List<OrderLine> getAllOrderLinesPerOrder(String orderId) throws OrderLineException {
        return Details("", orderId);
    }

    @JsonIgnore
    public final OrderLine getOrderLineDetails(String id) throws OrderLineException {
        List<OrderLine> lines = Details(id, "");
        if (!lines.isEmpty()) {
            return lines.get(0);
        }
        return null;
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    public List<OrderLine> Details(String id, String orderID) throws OrderLineException {
        List<OrderLine> lines = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderLine line = new OrderLine();
                    line.id = rs.getString("id");
                    line.orderId = rs.getString("orderId");
                    line.productTypeID = rs.getInt("productTypeID");
                    line.leatherID = rs.getInt("leatherID");
                    line.productStyle = rs.getString("productStyle");
                    line.price = rs.getDouble("price");
                    line.notes = rs.getString("notes");
                    lines.add(line);
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new OrderLineException(String.format("Error fetching order line details with ID %s", id), e);
            }
            if (orderID != null) {
                throw new OrderLineException(String.format("Error fetching order line details with Order ID %s", orderID), e);
            }
            throw new OrderLineException("Error fetching order line details", e);
        }

        return lines;
    }

    public void Delete() throws OrderLineException {
        if (id == null) {
            return;
        }

        try (CallableStatement stmt = connection.prepareCall(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new OrderLineException(String.format("Error deleting order line with ID %s", id), e);
        }
    }

    public void Commit() throws OrderLineException {
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
            if (id != null) {
                throw new OrderLineException(String.format("Error updating order line with ID %s", id), e);
            }
            throw new OrderLineException("Error commiting order line", e);
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
//--------------------------------------------------------
//endregion

}