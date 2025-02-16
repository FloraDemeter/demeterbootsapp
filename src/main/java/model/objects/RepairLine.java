package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.util.DataContext;
import model.util.exceptions.RepairLineException;

public class RepairLine {
    private String id;
    private String repairID;
    private Integer productTypeID;
    private Integer repairCategoryID;
    private Double price;
    private String notes; 

    private static String detailsFunction  = "SELECT * FROM demeterboots.RepairLine_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.RepairLine_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.RepairLine_Commit(?, ?, ?, ?, ?, ?)";

    private final static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

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
            this.productTypeID = repairline.productTypeID;
            this.repairCategoryID = repairline.repairCategoryID;
            this.price = repairline.price;
            this.notes = repairline.notes;
        }
    }

    public RepairLine(String id, String repairID, Integer productTypeID, Integer repairCategoryID, Double price, String notes) {
        this.id = id;
        this.repairID = repairID;
        this.productTypeID = productTypeID;
        this.repairCategoryID = repairCategoryID;
        this.price = price;
        this.notes = notes;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    public List<RepairLine> getAllRepairLines() throws RepairLineException {
        return Details("", "");
    }

    public List<RepairLine> getAllRepairLinesForRepair(String repairId) throws RepairLineException {
        return Details("", repairID);
    }

    public final RepairLine getRepairLineDetails(String id) throws RepairLineException {
        List<RepairLine> repairLines = Details(id, "");
        if (!repairLines.isEmpty()) {
            return repairLines.get(0);
        }
        return null;
    }

//--------------------------------------------------------
//endregion

//region Database methods
//--------------------------------------------------------

    public List<RepairLine> Details(String id, String repairID) throws RepairLineException{
        List<RepairLine> repairLines = new ArrayList<>();
        try {PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setString(1, id);
            statement.setString(2, repairID);
            try(ResultSet reader = statement.executeQuery()) {
                while (reader.next()) {
                    RepairLine repairline = new RepairLine();
                    this.id = reader.getString("id");
                    this.repairID = reader.getString("repairID");
                    this.productTypeID = reader.getInt("productTypeID");
                    this.repairCategoryID = reader.getInt("repairCategoryID");
                    this.price = reader.getDouble("price");
                    this.notes = reader.getString("notes");
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

    public void Delete() throws RepairLineException {
        try {PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RepairLineException(String.format("Error deleting repair line with ID %s", id), e);
        }
    }

    public void Commit() throws RepairLineException{
        try {PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setString(1, id);
            statement.setString(2, repairID);
            statement.setInt(3, productTypeID);
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

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
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