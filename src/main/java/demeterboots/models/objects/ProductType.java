package demeterboots.models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demeterboots.models.util.DataContext;
import demeterboots.models.util.exceptions.DatabaseException;
import demeterboots.models.util.exceptions.ProductTypeException;

public class ProductType {
    
    private Integer id;
    private String description;

    private static String detailsFunction  = "SELECT * FROM demeterboots.ProductType_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.ProductType_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.ProductType_Commit(?, ?)";

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
        }
    }

    public ProductType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    public List<ProductType> getAllProductTypes() throws ProductTypeException {
        return Details(null);
    }

    public final ProductType getProductTypeDetails(Integer id) throws ProductTypeException {
        List<ProductType> prodTypes = Details(id);
        if (!prodTypes.isEmpty()) {
            return prodTypes.get(0);
        }
        return null;
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    public List<ProductType> Details(Integer id) throws ProductTypeException {
        List<ProductType> prodTypes = new ArrayList<>();
        try { PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductType prodtype = new ProductType();
                    prodtype.id = resultSet.getInt("id");
                    prodtype.description = resultSet.getString("description");
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

    public void Delete() throws ProductTypeException {
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

    public void Commit() throws ProductTypeException {
        try { PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setInt(1, id);
            statement.setString(2, description);
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
//--------------------------------------------------------
//endregion
    
}