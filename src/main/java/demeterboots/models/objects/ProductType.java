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
import demeterboots.models.util.exceptions.ProductTypeException;

public class ProductType {
    
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private Double price;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.ProductType_Details(?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.ProductType_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.ProductType_Commit(?, ?, ?)";

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

    public ProductType() {
    }

    public ProductType(Integer id) throws ProductTypeException{
        ProductType prodtype;

        if (id == null) {
            return;
        }

        prodtype = getProductTypeDetails(id);

        if (prodtype != null) {
            this.id = prodtype.id;
            this.description = prodtype.description;
            this.price = prodtype.price;
        }
    }

    public ProductType(Integer id, String description, Double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<ProductType> getAllProductTypes() throws ProductTypeException {
        return Details(0);
    }

    @JsonIgnore
    public final static ProductType getProductTypeDetails(Integer id) throws ProductTypeException {
        List<ProductType> prodTypes = Details(id);
        if (!prodTypes.isEmpty()) {
            return prodTypes.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteProductType() throws ProductTypeException {
        Delete();
    }

    @JsonIgnore
    public void commitProductType() throws ProductTypeException {
        Commit();
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    private static List<ProductType> Details(Integer id) throws ProductTypeException {
        List<ProductType> prodTypes = new ArrayList<>();
        try { PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setObject(1, id == 0 ? null : id, java.sql.Types.INTEGER);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductType prodtype = new ProductType();
                    prodtype.id = resultSet.getInt("id");
                    prodtype.description = resultSet.getString("description");
                    prodtype.price = resultSet.getDouble("price");
                    prodTypes.add(prodtype); 
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new ProductTypeException(String.format("Error fetching product type details for ID %s", id),e);
            }
            throw new ProductTypeException("Error fetching product type details", e);
        }

        return prodTypes;
    }

    private void Delete() throws ProductTypeException {
        if (id == null) {
            return;
        }
        try { PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new ProductTypeException(String.format("Error deleting product type with ID %s", id), e);
        }
    }

    private void Commit() throws ProductTypeException {
        try { PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setInt(1, id);
            statement.setString(2, description);
            statement.setDouble(3, price);
            statement.execute();
        } catch (SQLException e) {
            if (id != null) {
                throw new ProductTypeException(String.format("Error updating product type with ID %s", id), e);
            }
            throw new ProductTypeException("Error commiting product type", e);
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