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
import demeterboots.models.util.exceptions.MeasurementsException;

public class Measurements {

    @JsonProperty("id")
    private String id;
    @JsonProperty("customerID")
    private String customerID;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("feet")
    private Double feet;
    @JsonProperty("bunion")
    private Double bunion;
    @JsonProperty("highPoint")
    private Double highPoint;
    @JsonProperty("heel")
    private Double heel;
    @JsonProperty("ankle")
    private Double ankle;
    @JsonProperty("calf")
    private Double calf;
    @JsonProperty("underKnee")
    private Double underKnee;
    @JsonProperty("height")
    private Double height;
    @JsonProperty("calfHeight")
    private Double calfHeight;
    @JsonProperty("tMark")
    private Double tMark;
    @JsonProperty("imagePath")
    private String imagePath;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.Measurements_Details(?, ?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.Measurements_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.Measurements_Commit(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    public Measurements() {
    }

    public Measurements(String id) throws MeasurementsException{
        Measurements meas;

        if (id == null) {
            return;
        }
        
        meas = getMeasurementsDetails(id,"");

        if (meas != null) {
            this.id = meas.id;
            this.customerID = meas.customerID;
            this.date = meas.date;
            this.notes = meas.notes;
            this.feet = meas.feet;
            this.bunion = meas.bunion;
            this.highPoint = meas.highPoint;
            this.heel = meas.heel;
            this.ankle = meas.ankle;
            this.calf = meas.calf;
            this.underKnee = meas.underKnee;
            this.height = meas.height;
            this.calfHeight = meas.calfHeight;
            this.tMark = meas.tMark;
            this.imagePath = meas.imagePath;
        }
    }

    public Measurements(String id, String CustomerID, Date Date, String Notes, Double Feet, Double Bunion, Double HighPoint, Double Heel, Double Ankle, Double Calf, Double UnderKnee, Double Height, Double CalfHeight, Double TMark, String ImagePath) {
        this.id = id;
        this.customerID = CustomerID;
        this.date = Date;
        this.notes = Notes;
        this.feet = Feet;
        this.bunion = Bunion;
        this.highPoint = HighPoint;
        this.heel = Heel;
        this.ankle = Ankle;
        this.calf = Calf;
        this.underKnee = UnderKnee;
        this.height = Height;
        this.calfHeight = CalfHeight;
        this.tMark = TMark;
        this.imagePath = ImagePath;
    }

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<Measurements> getAllMeasurements() throws MeasurementsException {
        return Details("", "");
    }

    @JsonIgnore
    public static List<Measurements> getAllMeasurementsByCustomer(String id) throws MeasurementsException {
        return Details("", id);
    }

    @JsonIgnore
    public final static Measurements getMeasurementsDetails(String id, String customerID) throws MeasurementsException {
        List<Measurements> measList = Details(id, customerID);
        if (measList.isEmpty()) {
            return measList.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteMeasurement() throws MeasurementsException {
        Delete();
    }

    @JsonIgnore
    public void commitMeasurement() throws MeasurementsException {
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

//region Database Methods
//--------------------------------------------------------

    private static List<Measurements> Details(String id, String customerId) throws MeasurementsException{
        List<Measurements> measList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setString(1, id.isEmpty() ? null : id);
            statement.setString(2, customerId.isEmpty() ? null : customerId);
            try(ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Measurements meas = new Measurements();
                    meas.id = results.getString("id");
                    meas.customerID = results.getString("CustomerID");
                    meas.date = results.getDate("Date");
                    meas.notes = results.getString("Notes");
                    meas.feet = results.getDouble("Feet");
                    meas.bunion = results.getDouble("Bunion");
                    meas.highPoint = results.getDouble("HighPoint");
                    meas.heel = results.getDouble("Heel");
                    meas.ankle = results.getDouble("Ankle");
                    meas.calf = results.getDouble("Calf");
                    meas.underKnee = results.getDouble("UnderKnee");
                    meas.height = results.getDouble("Height");
                    meas.calfHeight = results.getDouble("CalfHeight");
                    meas.tMark = results.getDouble("TMark");
                    meas.imagePath = results.getString("ImagePath");
                    measList.add(meas);
                }
            }
        } catch (SQLException e) {
            if (id != null) {
               throw new MeasurementsException(String.format("Error getting measurements details for ID %s", id), e);
            }

            if (customerId != null) {
               throw new MeasurementsException(String.format("Error getting measurements details for Customer with the ID %s", customerId), e);
            }

            throw new MeasurementsException("Error getting measurements details", e);
        }

        return measList;
    }

    private void Delete() throws MeasurementsException{
        if (id == null) {
            return;
        }

        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new MeasurementsException(String.format("Error deleting measurements details with ID %s", id), e);
        }
    }

    private void Commit() throws MeasurementsException{
        try(PreparedStatement statement = connection.prepareStatement(commitFunction)) {
            statement.setString(1, id);
            statement.setString(2, customerID);
            statement.setDate(3, (java.sql.Date) date);
            statement.setString(4, notes);
            statement.setDouble(5, feet);
            statement.setDouble(6, bunion);
            statement.setDouble(7, highPoint);
            statement.setDouble(8, heel);
            statement.setDouble(9, ankle);
            statement.setDouble(10, calf);
            statement.setDouble(11, underKnee);
            statement.setDouble(12, height);
            statement.setDouble(13, calfHeight);
            statement.setDouble(14, tMark);
            statement.setString(15, imagePath);
            statement.execute();
        } catch (SQLException e) {
            throw new MeasurementsException(String.format("Error committing measurements details with ID %s", id), e);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getFeet() {
        return feet;
    }

    public void setFeet(Double feet) {
        this.feet = feet;
    }

    public Double getBunion() {
        return bunion;
    }

    public void setBunion(Double bunion) {
        this.bunion = bunion;
    }

    public Double getHighPoint() {
        return highPoint;
    }

    public void setHighPoint(Double highPoint) {
        this.highPoint = highPoint;
    }

    public Double getHeel() {
        return heel;
    }

    public void setHeel(Double heel) {
        this.heel = heel;
    }

    public Double getAnkle() {
        return ankle;
    }

    public void setAnkle(Double ankle) {
        this.ankle = ankle;
    }

    public Double getCalf() {
        return calf;
    }

    public void setCalf(Double calf) {
        this.calf = calf;
    }

    public Double getUnderKnee() {
        return underKnee;
    }

    public void setUnderKnee(Double underKnee) {
        this.underKnee = underKnee;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getCalfHeight() {
        return calfHeight;
    }

    public void setCalfHeight(Double calfHeight) {
        this.calfHeight = calfHeight;
    }

    public Double getTMark() {
        return tMark;
    }

    public void setTMark(Double TMark) {
        this.tMark = TMark;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
//--------------------------------------------------------
//endregion