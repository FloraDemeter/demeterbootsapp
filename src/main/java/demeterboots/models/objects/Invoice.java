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
import demeterboots.models.util.exceptions.InvoiceException;

public class Invoice {

    @JsonProperty("id")
    private String id;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("paymentType")
    private Integer paymentType;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("invoiceDate")
    private Date invoiceDate;
    @JsonProperty("paymentDate")
    private Date paymentDate;
    @JsonProperty("isPaid")
    private Boolean isPaid;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.Invoice_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.Invoice_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.Invoice_Commit(?, ?, ?, ?, ?, ?, ?, ?)";

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


//region Contructors
//--------------------------------------------------------
    
    public Invoice() {
    }

    public Invoice(String value) throws InvoiceException{
        Invoice invoice;

        if (value == null) {
            return;
        }

        invoice = getInvoiceDetails(value, "");
        
        if (invoice != null) {
            this.id = invoice.id;
            this.customerId = invoice.customerId;
            this.status = invoice.status;
            this.paymentType = invoice.paymentType;
            this.total = invoice.total;
            this.invoiceDate = invoice.invoiceDate;
            this.paymentDate = invoice.paymentDate;
            this.isPaid = invoice.isPaid;
        }
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

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<Invoice> getAllInvoices() throws InvoiceException{
        return Details("","");
    }

    @JsonIgnore
    public static List<Invoice> getAllInvoicesByCustomerID(String customerID) throws InvoiceException{
        return Details("", customerID);
    }

    @JsonIgnore
    public static Invoice getInvoice(String invoiceID) throws InvoiceException{
        return new Invoice (invoiceID);
    }

    @JsonIgnore
    public final static Invoice getInvoiceDetails(String invoiceID, String customerID) throws InvoiceException{
        List<Invoice> invoices = Details(invoiceID, customerID);
        if (!invoices.isEmpty()) {
            return invoices.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteInvoice() throws InvoiceException {
        Delete();
    }

    @JsonIgnore
    public void commitInvoice() throws InvoiceException {
        Commit();
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

    private static List<Invoice> Details(String invoiceId, String customerId) throws InvoiceException{
        List<Invoice> invoices = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, invoiceId.isEmpty() ? null : invoiceId);
            stmt.setString(2, customerId.isEmpty() ? null : customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.id = rs.getString("id");
                    invoice.customerId = rs.getString("customerId");
                    invoice.status = rs.getInt("status");
                    invoice.paymentType = rs.getInt("paymentType");
                    invoice.total = rs.getDouble("total");
                    invoice.invoiceDate = rs.getDate("Date");
                    invoice.paymentDate = rs.getDate("paymentDate");
                    invoice.isPaid = rs.getBoolean("isPaid");
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            if (invoiceId != null) {
                throw new InvoiceException(String.format("Error getting invoice with ID %s", invoiceId), e);
            } 
            
            if  (customerId != null) {
                throw new InvoiceException(String.format("Error getting invoices for customer with ID %s", customerId), e);
            }

            throw new InvoiceException("Error getting all invoices", e);
        }

        return invoices;
    }

    private void Delete() throws InvoiceException{
        if (id == null) {
            return;
        }

        try(PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new InvoiceException(String.format("Error deleting invoice with ID %s", id), e);
        }
    }

    private void Commit() throws InvoiceException{
        try(PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, customerId);
            stmt.setInt(3, status);
            stmt.setInt(4, paymentType);
            stmt.setDouble(5, total);
            stmt.execute();
        } catch (SQLException e) {
            throw new InvoiceException(String.format("Error committing invoice with ID %s", id), e);
        }
    }

//--------------------------------------------------------
//endregion

//region Getters and Setter Methods
//--------------------------------------------------------

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

//--------------------------------------------------------
//endregion