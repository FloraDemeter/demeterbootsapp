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
import demeterboots.models.util.exceptions.RepairLineException;

public class RepairLine {

    @JsonProperty("id")
    private String id;
    @JsonProperty("repairID")
    private String repairID;
    @JsonProperty("repairCategoryID")
    private Integer repairCategoryID;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("notes")
    private String notes; 

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.RepairLine_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.RepairLine_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.RepairLine_Commit(?, ?, ?, ?, ?)";

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
   
    public RepairLine() {
    }

    public RepairLine(String id) throws RepairLineException {
        RepairLine repairline;

        if (id == null) {
            return;
        }

        repairline = getRepairLineDetails(id);

        if (repairline != null) {
            this.id = repairline.id;
            this.repairID = repairline.repairID;
            this.repairCategoryID = repairline.repairCategoryID;
            this.price = repairline.price;
            this.notes = repairline.notes;
        }
    }

    public RepairLine(String id, String repairID, Integer repairCategoryID, Double price, String notes) {
        this.id = id;
        this.repairID = repairID;
        this.repairCategoryID = repairCategoryID;
        this.price = price;
        this.notes = notes;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<RepairLine> getAllRepairLines() throws RepairLineException {
        return Details("", "");
    }

    @JsonIgnore
    public static List<RepairLine> getAllRepairLinesForRepair(String repairId) throws RepairLineException {
        return Details("", repairId);
    }

    @JsonIgnore
    public final static RepairLine getRepairLineDetails(String id) throws RepairLineException {
        List<RepairLine> repairLines = Details(id, "");
        if (!repairLines.isEmpty()) {
            return repairLines.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteRepairLine() throws RepairLineException {
        Delete();
    }

    @JsonIgnore
    public void commitRepairLine() throws RepairLineException {
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

    private static List<RepairLine> Details(String id, String repairID) throws RepairLineException{
        List<RepairLine> repairLines = new ArrayList<>();
        try {PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setString(1, id.isEmpty() ? null : id);
            statement.setString(2, repairID.isEmpty() ? null : id);
            try(ResultSet reader = statement.executeQuery()) {
                while (reader.next()) {
                    RepairLine repairline = new RepairLine();
                    repairline.id = reader.getString("id");
                    repairline.repairID = reader.getString("repairID");
                    repairline.repairCategoryID = reader.getInt("repairCategoryID");
                    repairline.price = reader.getDouble("price");
                    repairline.notes = reader.getString("notes");
                    repairLines.add(repairline);
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new RepairLineException(String.format("Error fetching repair line details with ID %s", id), e);
            }

            if (repairID != null) {
                throw new RepairLineException(String.format("Error fetching repair line details with Repair ID %s",repairID), e);
            }
            throw new RepairLineException("Error fetching repair line details", e);
        }

        return repairLines;
    }

    private void Delete() throws RepairLineException {
        try {PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RepairLineException(String.format("Error deleting repair line with ID %s", id), e);
        }
    }

    private void Commit() throws RepairLineException{
        try {PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setString(1, id);
            statement.setString(2, repairID);
            statement.setInt(4, repairCategoryID);
            statement.setDouble(5, price);
            statement.setString(6, notes);
            statement.execute();
        } catch (SQLException e) {
            if (id != null ) {
                throw new RepairLineException(String.format("Error while updating repair line with ID %s", id), e);    
            }
            throw new RepairLineException("Error while committing repair line", e);
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

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public Integer getRepairCategoryID() {
        return repairCategoryID;
    }

    public void setRepairCategoryID(Integer repairCategoryID) {
        this.repairCategoryID = repairCategoryID;
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