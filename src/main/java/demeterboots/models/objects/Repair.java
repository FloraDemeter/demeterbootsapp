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
import demeterboots.models.util.exceptions.RepairException;

public class Repair {

    @JsonProperty("id")
    private String id;
    @JsonProperty("customerID")
    private String customerID;
    @JsonProperty("repairDate")
    private Date repairDate;
    @JsonProperty("predictedDate")
    private Date predictedDate;
    @JsonProperty("location")
    private String location;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("isWarrantyAccepted")
    private Boolean isWarrantyAccepted;
    @JsonProperty("status")
    private Integer status;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.Repair_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.Repair_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.Repair_Commit(?, ?, ?, ?, ?, ?, ?, ?)";

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

    public Repair() {
    }

    public Repair(String id) throws RepairException {
        Repair repair;

        if (id == null) {
            return;
        }

        repair = getRepairDetails(id);

        if (repair != null) {
            this.id = repair.id;
            this.customerID = repair.customerID;
            this.repairDate = repair.repairDate;
            this.predictedDate = repair.predictedDate;
            this.location = repair.location;
            this.total = repair.total;
            this.isWarrantyAccepted = repair.isWarrantyAccepted;
            this.status = repair.status;
        }
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
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<Repair> getAllRepairs() throws RepairException {
        return Details("","");
    }

    @JsonIgnore
    public static List<Repair> getAllRepairsForCustomer(String customerID) throws RepairException {
        return Details("", customerID);
    }

    @JsonIgnore
    public final static Repair getRepairDetails(String id) throws RepairException {
        List<Repair> repairs = Details(id, "");
        if (!repairs.isEmpty()) {
            return repairs.get(0);
        }

        return null;
    }

    @JsonIgnore
    public void deleteRepair() throws RepairException {
        Delete();
    }

    @JsonIgnore
    public void commitRepair() throws RepairException {
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

    private static List<Repair> Details(String id, String customerID) throws RepairException {
        List<Repair> repairs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id.isEmpty() ? null : id);
            stmt.setString(2, customerID.isEmpty() ? null : customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Repair repair = new Repair();
                    repair.id = rs.getString("id");
                    repair.customerID = rs.getString("customerID");
                    repair.repairDate = rs.getDate("date");
                    repair.predictedDate = rs.getDate("predictedfinish");
                    repair.location = rs.getString("location");
                    repair.total = rs.getDouble("total");
                    repair.isWarrantyAccepted = rs.getBoolean("isWarrantyAccepted");
                    repair.status = rs.getInt("status");
                    repairs.add(repair);
                }
            }
        } catch (SQLException e) {
           if (id != null) {
                throw new RepairException(String.format("Error fetching repair details for order with ID %s", id), e);
           }

           if (customerID != null) {
                throw new RepairException(String.format("Error fetching repair details for customer %s", customerID), e);
           }

           throw new RepairException(String.format("Error fetching repair details"), e);
        }

        return repairs;
    }

    private void Delete() throws RepairException {
        if (id == null) {
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RepairException(String.format("Error deleting repair with ID %s", id), e);
        }
    }

    private void Commit() throws RepairException {
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
            throw new RepairException(String.format("Error updating repair with ID %s", id), e);
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

    public void setTotal(Double newTotal) {
        total = newTotal;
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

    public void setStatus(Integer newStatus) {
        status = newStatus;
    }
}

//--------------------------------------------------------
//endregion