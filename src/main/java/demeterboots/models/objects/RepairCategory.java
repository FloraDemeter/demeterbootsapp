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
import demeterboots.models.util.exceptions.RepairCategoryException;

public class RepairCategory {
    
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("productTypeId")
    private Integer productTypeId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private Double price;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.RepairCategory_Details(?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.RepairCategory_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.RepairCategory_Commit(?, ?, ? ,?)";

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

    public RepairCategory() {
    }

    public RepairCategory(Integer id) throws RepairCategoryException{
        RepairCategory repairCat;

        if (id == null) {
            return;
        }

        repairCat = getRepairCategoryDetails(id);

        if (repairCat != null) {
            this.id = repairCat.id;
            this.productTypeId = repairCat.productTypeId;
            this.description = repairCat.description;
            this.price = repairCat.price;
        }
    }

    public RepairCategory(Integer id, Integer productTypeId, String description, Double price) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.description = description;
        this.price = price;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<RepairCategory> getAllRepairCategory() throws RepairCategoryException {
        return Details(null);
    }

    @JsonIgnore
    public final static RepairCategory getRepairCategoryDetails(Integer id) throws RepairCategoryException {
        List<RepairCategory> repairCats = Details(id);
        if (!repairCats.isEmpty()) {
            return repairCats.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteRepairCategory() throws RepairCategoryException {
        Delete();
    }

    @JsonIgnore
    public void commitRepairCategory() throws RepairCategoryException {
        Commit();
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    private static List<RepairCategory> Details(Integer id) throws RepairCategoryException {
        List<RepairCategory> repairCats = new ArrayList<>();
        try { PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setInt(1, id == 0 ? null : id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    RepairCategory repairCat = new RepairCategory();
                    repairCat.id = resultSet.getInt("id");
                    repairCat.productTypeId = resultSet.getInt("productTypeId");
                    repairCat.description = resultSet.getString("description");
                    repairCat.price = resultSet.getDouble("price");
                    repairCats.add(repairCat); 
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new RepairCategoryException(String.format("Error fetching repair category details for ID %s", id),e);
            }
            throw new RepairCategoryException("Error fetching repair category details", e);
        }

        return repairCats;
    }

    private void Delete() throws RepairCategoryException {
        if (id == null) {
            return;
        }
        try { PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RepairCategoryException(String.format("Error deleting repair category with ID %s", id), e);
        }
    }

    private void Commit() throws RepairCategoryException {
        try { PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setInt(1, id);
            statement.setInt(2, productTypeId);
            statement.setString(3, description);
            statement.setDouble(4, price);
            statement.execute();
        } catch (SQLException e) {
            if (id != null) {
                throw new RepairCategoryException(String.format("Error updating repair category with ID %s", id), e);
            }
            throw new RepairCategoryException("Error commiting repair category", e);
        }
    }
//--------------------------------------------------------
//endregion


//region Getters and Setters
//--------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
//--------------------------------------------------------
//endregion
    
}