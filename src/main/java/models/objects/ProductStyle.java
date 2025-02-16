package models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.util.DataContext;
import models.util.exceptions.DatabaseException;
import models.util.exceptions.ProductStyleException;

public class ProductStyle {

    private Integer id;
    private Integer productStyleTypeID;
    private String description;

    private static String detailsFunction  = "SELECT * FROM demeterboots.ProductStyle_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.ProductStyle_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.ProductStyle_Commit(?, ?, ?)";

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

    public ProductStyle() {
    }

    public ProductStyle(Integer id) throws ProductStyleException {
        ProductStyle prodStyle;

        if (id == null) {
            return;
        }

        prodStyle = getProductStyleDetails(id);

        if (prodStyle != null) {
            this.id = prodStyle.id;
            this.productStyleTypeID = prodStyle.productStyleTypeID;
            this.description = prodStyle.description;
        }
    }

    public ProductStyle(Integer id, Integer productStyleTypeID, String description) {
        this.id = id;
        this.productStyleTypeID = productStyleTypeID;
        this.description = description;
    }

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    public List<ProductStyle> getAllProductStyles() throws ProductStyleException {
        return Details(null);
    }

    public final ProductStyle getProductStyleDetails(Integer id) throws ProductStyleException {
        List<ProductStyle> prodStyles = Details(id);
        if (!prodStyles.isEmpty()) {
            return prodStyles.get(0);
        }
        return null;
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    public List<ProductStyle> Details(Integer id) throws ProductStyleException {
        List<ProductStyle> prodStyles = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductStyle style = new ProductStyle();
                    this.id = resultSet.getInt("id");
                    this.productStyleTypeID = resultSet.getInt("productStyleTypeID");
                    this.description = resultSet.getString("description");
                    prodStyles.add(style);
                }
            }
        } catch (Exception e) {
            if (id != null) {
                throw new ProductStyleException(String.format("Error fetching product style details for ID %s", id), e);
            }

            throw new ProductStyleException("Error fetching product style details",e );
        }

        return prodStyles;
    }

    public void Delete() throws ProductStyleException{
        if (id == null) {
            return;
        }

        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            throw new ProductStyleException(String.format("Error deleting product style with ID %s", id), e);
        }
    }

    public void Commit() throws ProductStyleException {
        try(PreparedStatement statement = connection.prepareStatement(commitFunction)) {
            statement.setInt(1, id);
            statement.setInt(2, productStyleTypeID);
            statement.setString(3, description);
            statement.execute();
        } catch (Exception e) {
            if (id != null) {
                throw new ProductStyleException(String.format("Error updating product style with ID %s", id), e);
            }
                throw new ProductStyleException("Error commiting product style", e);
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

    public Integer getProductStyleTypeID() {
        return productStyleTypeID;
    }

    public void setProductStyleTypeID(Integer productStyleTypeID) {
        this.productStyleTypeID = productStyleTypeID;
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