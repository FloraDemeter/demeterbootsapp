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
import demeterboots.models.util.exceptions.PaymentTypeException;

public class PaymentType {
    
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("description")
    private String description;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.PaymentType_Details(?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.PaymentType_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.PaymentType_Commit(?, ?)";

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

    public PaymentType() {
    }

    public PaymentType(Integer id) throws PaymentTypeException{
        PaymentType paymentType;

        if (id == null) {
            return;
        }

        paymentType = getPaymentTypeDetails(id);

        if (paymentType != null) {
            this.id = paymentType.id;
            this.description = paymentType.description;
        }
    }

    public PaymentType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<PaymentType> getAllPaymentType() throws PaymentTypeException {
        return Details(0);
    }

    @JsonIgnore
    public final static PaymentType getPaymentTypeDetails(Integer id) throws PaymentTypeException {
        List<PaymentType> paymentTypes = Details(id);
        if (!paymentTypes.isEmpty()) {
            return paymentTypes.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deletePaymentType() throws PaymentTypeException {
        Delete();
    }

    @JsonIgnore
    public void commitPaymentType() throws PaymentTypeException {
        Commit();
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    private static List<PaymentType> Details(Integer id) throws PaymentTypeException {
        List<PaymentType> paymentTypes = new ArrayList<>();
        try { PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setObject(1, id == 0 ? null : id, java.sql.Types.INTEGER);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PaymentType paymentType = new PaymentType();
                    paymentType.id = resultSet.getInt("id");
                    paymentType.description = resultSet.getString("description");
                    paymentTypes.add(paymentType); 
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new PaymentTypeException(String.format("Error fetching payment type details for ID %s", id),e);
            }
            throw new PaymentTypeException("Error fetching payment type details", e);
        }

        return paymentTypes;
    }

    private void Delete() throws PaymentTypeException {
        if (id == null) {
            return;
        }
        try { PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new PaymentTypeException(String.format("Error deleting payment type with ID %s", id), e);
        }
    }

    private void Commit() throws PaymentTypeException {
        try { PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setInt(1, id);
            statement.setString(2, description);
            statement.execute();
        } catch (SQLException e) {
            if (id != null) {
                throw new PaymentTypeException(String.format("Error updating payment type with ID %s", id), e);
            }
            throw new PaymentTypeException("Error commiting payment type", e);
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