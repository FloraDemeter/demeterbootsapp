package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.DataContext;

public class Invoice {
    private String id;
    private String customerId;
    private Integer status;
    private Integer paymentType;
    private Double total;
    private Date invoiceDate;
    private Date paymentDate;
    private Boolean isPaid;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Invoice_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.Invoice_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Invoice_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Invoice() {
    }

    public Invoice(String id) {
        Details(id);
    }

    public Invoice(String id, String customerId, Integer status, Integer paymentType, Double total, Date invoiceDate, Date paymentDate, Boolean isPaid) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.paymentType = paymentType;
        this.total = total;
        this.invoiceDate = invoiceDate;
        this.paymentDate = paymentDate;
        this.isPaid = isPaid;
    }

    public void Details(String id) {
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.customerId = rs.getString("customerId");
                    this.status = rs.getInt("status");
                    this.paymentType = rs.getInt("paymentType");
                    this.total = rs.getDouble("total");
                    this.invoiceDate = rs.getDate("invoiceDate");
                    this.paymentDate = rs.getDate("paymentDate");
                    this.isPaid = rs.getBoolean("isPaid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try(PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try(PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, customerId);
            stmt.setInt(3, status);
            stmt.setInt(4, paymentType);
            stmt.setDouble(5, total);
            stmt.execute();
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}