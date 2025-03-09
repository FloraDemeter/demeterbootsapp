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
import demeterboots.models.util.exceptions.StatusException;

public class Status {
    
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("description")
    private String description;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.Status_Details(?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.Status_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.Status_Commit(?, ?)";

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

    public Status() {
    }

    public Status(Integer id) throws StatusException{
        Status stat;

        if (id == null) {
            return;
        }

        stat = getStatusDetails(id);

        if (stat != null) {
            this.id = stat.id;
            this.description = stat.description;
        }
    }

    public Status(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<Status> getAllStatus() throws StatusException {
        return Details(0);
    }

    @JsonIgnore
    public final static Status getStatusDetails(Integer id) throws StatusException {
        List<Status> stats = Details(id);
        if (!stats.isEmpty()) {
            return stats.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteStatus() throws StatusException {
        Delete();
    }

    @JsonIgnore
    public void commitStatus() throws StatusException {
        Commit();
    }

//--------------------------------------------------------
//endregion

//region Database Methods
//--------------------------------------------------------

    private static List<Status> Details(Integer id) throws StatusException {
        List<Status> stats = new ArrayList<>();
        try { PreparedStatement statement = connection.prepareStatement(detailsFunction);
            statement.setObject(1, id == 0 ? null : id, java.sql.Types.INTEGER);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Status stat = new Status();
                    stat.id = resultSet.getInt("id");
                    stat.description = resultSet.getString("description");
                    stats.add(stat); 
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new StatusException(String.format("Error fetching status details for ID %s", id),e);
            }
            throw new StatusException("Error fetching status details", e);
        }

        return stats;
    }

    private void Delete() throws StatusException {
        if (id == null) {
            return;
        }
        try { PreparedStatement statement = connection.prepareStatement(deleteFunction);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new StatusException(String.format("Error deleting status with ID %s", id), e);
        }
    }

    private void Commit() throws StatusException {
        try { PreparedStatement statement = connection.prepareStatement(commitFunction);
            statement.setInt(1, id);
            statement.setString(2, description);
            statement.execute();
        } catch (SQLException e) {
            if (id != null) {
                throw new StatusException(String.format("Error updating status with ID %s", id), e);
            }
            throw new StatusException("Error commiting status", e);
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