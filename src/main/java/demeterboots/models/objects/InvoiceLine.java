package demeterboots.models.objects;

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
import demeterboots.models.util.exceptions.InvoiceLineException;

public class InvoiceLine {

    @JsonProperty("id")
    private String id;
    @JsonProperty("invoiceID")
    private String invoiceID;
    @JsonProperty("taskID")
    private String taskID;
    @JsonProperty("taskType")
    private String taskType;
    @JsonProperty("total")
    private Double total;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.InvoiceLine_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.InvoiceLine_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.InvoiceLine_Commit(?, ?, ?, ?, ?)";

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

    public InvoiceLine() {
    }

    public InvoiceLine(String id) throws InvoiceLineException {
        InvoiceLine invoiceline;

        if (id == null) {
            return;
        }

        invoiceline = getInvoiceLineDetails(id);

        if (invoiceline != null) {
            this.id = invoiceline.id;
            this.invoiceID = invoiceline.invoiceID;
            this.taskID = invoiceline.taskID;
            this.taskType = invoiceline.taskType;
            this.total = invoiceline.total;
        }
    }

    public InvoiceLine(String id, String invoiceID, String taskID, String taskType, Double total) {
        this.id = id;
        this.invoiceID = invoiceID;
        this.taskID = taskID;
        this.taskType = taskType;
        this.total = total;
    }

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public List<InvoiceLine> getAllInvoiceLines() throws InvoiceLineException {
        return Details("", "");
    }

    @JsonIgnore
    public List<InvoiceLine> getAllInvoiceLinesByInvoiceID(String invoiceID) throws InvoiceLineException {
        return Details("", invoiceID);
    }

    @JsonIgnore
    public final InvoiceLine getInvoiceLineDetails(String id) throws InvoiceLineException {
        List<InvoiceLine> invoiceLines = Details(id, "");
        if (!invoiceLines.isEmpty()) {
            return invoiceLines.get(0);
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


//region Database Methods
//--------------------------------------------------------

    public List<InvoiceLine> Details(String invoiceLineId, String invoiceId) throws InvoiceLineException {
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, invoiceLineId);
            stmt.setString(2, invoiceId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InvoiceLine invoiceLine = new InvoiceLine();
                    invoiceLine.id = rs.getString("id");
                    invoiceLine.invoiceID = rs.getString("invoiceID");
                    invoiceLine.taskID = rs.getString("taskID");
                    invoiceLine.taskType = rs.getString("taskType");
                    invoiceLine.total = rs.getDouble("total");
                    invoiceLines.add(invoiceLine);
                }
            }
        } catch (SQLException e) {
            if (invoiceLineId != null) {
                throw new InvoiceLineException(String.format("Error getting invoice line details by Invoice Line ID %s", invoiceLineId), e);
            } if ( invoiceId != null) {
                throw new InvoiceLineException(String.format("Error getting invoice line details by Invoice ID %s", invoiceId), e);
            }

            throw new InvoiceLineException("Error getting all invoice lines", e);
        }

        return invoiceLines;
    }

    public void Delete() throws InvoiceLineException {
        try(PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new InvoiceLineException(String.format("Error deleting invoice line with ID %s", id), e);
        }
    }

    public void Commit() throws InvoiceLineException {
        try(PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, invoiceID);
            stmt.setString(3, taskID);
            stmt.setString(4, taskType);
            stmt.setDouble(5, total);
            stmt.execute();
        } catch (SQLException e) {
            throw new InvoiceLineException(String.format("Error committing invoice line with ID %s", id), e);
        }
    }

//--------------------------------------------------------
//endregion

//region Getters and Setters
//--------------------------------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
//--------------------------------------------------------
//endregion