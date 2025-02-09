package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class InvoiceLine {
    private String id;
    private String invoiceID;
    private String taskID;
    private String taskType;
    private Double total;

    private static String detailsFunction  = "SELECT * FROM demeterboots.InvoiceLine_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.InvoiceLine_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.InvoiceLine_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public InvoiceLine() {
    }

    public InvoiceLine(String id) {
        Details(id);
    }

    public InvoiceLine(String id, String invoiceID, String taskID, String taskType, Double total) {
        this.id = id;
        this.invoiceID = invoiceID;
        this.taskID = taskID;
        this.taskType = taskType;
        this.total = total;
    }

    public void Details(String id) {
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.invoiceID = rs.getString("invoiceID");
                    this.taskID = rs.getString("taskID");
                    this.taskType = rs.getString("taskType");
                    this.total = rs.getDouble("total");
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
            stmt.setString(2, invoiceID);
            stmt.setString(3, taskID);
            stmt.setString(4, taskType);
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