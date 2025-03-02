package demeterboots.models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import demeterboots.models.util.DataContext;
import demeterboots.models.util.exceptions.DatabaseException;
import demeterboots.models.util.exceptions.InvoiceException;

public class Invoice {
    private String id;
    private String customerId;
    private Integer status;
    private Integer paymentType;
    private Double total;
    private Date invoiceDate;
    private Date paymentDate;
    private Boolean isPaid;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Invoice_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.Invoice_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Invoice_Commit(?, ?, ?, ?, ?, ?, ?, ?)";

    private final static DataContext dataContext;

    static {
        try {
            dataContext = getDataContext();
        } catch (DatabaseException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
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

public List<Invoice> getAllInvoices() throws InvoiceException{
    return Details("","");
}

public List<Invoice> getAllInvoicesByCustomerID(String customerID) throws InvoiceException{
    return Details("", customerID);
}

public Invoice getInvoice(String invoiceID) throws InvoiceException{
    return new Invoice (invoiceID);
}

public final Invoice getInvoiceDetails(String invoiceID, String customerID) throws InvoiceException{
    List<Invoice> invoices = Details(invoiceID, customerID);
    if (!invoices.isEmpty()) {
        return invoices.get(0);
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

    public List<Invoice> Details(String invoiceId, String customerId) throws InvoiceException{
        List<Invoice> invoices = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, invoiceId);
            stmt.setString(2, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    this.id = rs.getString("id");
                    this.customerId = rs.getString("customerId");
                    this.status = rs.getInt("status");
                    this.paymentType = rs.getInt("paymentType");
                    this.total = rs.getDouble("total");
                    this.invoiceDate = rs.getDate("invoiceDate");
                    this.paymentDate = rs.getDate("paymentDate");
                    this.isPaid = rs.getBoolean("isPaid");
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

    public void Delete() throws InvoiceException{
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

    public void Commit() throws InvoiceException{
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