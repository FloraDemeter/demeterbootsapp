package models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.util.DataContext;
import models.util.exceptions.DatabaseException;
import models.util.exceptions.MeasurementsException;

public class Measurements {

    private String id;
    private String customerID;
    private Date date;
    private String notes;
    private Double feet;
    private Double bunion;
    private Double highPoint;
    private Double heel;
    private Double ankle;
    private Double calf;
    private Double underKnee;
    private Double height;
    private Double calfHeight;
    private Double tMark;
    private String imagePath;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Measurements_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.Measurements_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Measurements_Commit(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    public List<Measurements> getAllMeasurements() throws MeasurementsException {
        return Details("", "");
    }

    public List<Measurements> getAllMeasurementsByCustomer(String id) throws MeasurementsException {
        return Details("", id);
    }

    public final Measurements getMeasurementsDetails(String id, String customerID) throws MeasurementsException {
        List<Measurements> measList = Details(id, customerID);
        if (measList.isEmpty()) {
            return measList.get(0);
        }
        return null;
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

    public List<Measurements> Details(String id, String customerId) throws MeasurementsException{
        List<Measurements> measList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setString(1, id);
            statement.setString(2, customerId);
            try(ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Measurements meas = new Measurements();
                    this.id = results.getString("id");
                    this.customerID = results.getString("CustomerID");
                    this.date = results.getDate("Date");
                    this.notes = results.getString("Notes");
                    this.feet = results.getDouble("Feet");
                    this.bunion = results.getDouble("Bunion");
                    this.highPoint = results.getDouble("HighPoint");
                    this.heel = results.getDouble("Heel");
                    this.ankle = results.getDouble("Ankle");
                    this.calf = results.getDouble("Calf");
                    this.underKnee = results.getDouble("UnderKnee");
                    this.height = results.getDouble("Height");
                    this.calfHeight = results.getDouble("CalfHeight");
                    this.tMark = results.getDouble("TMark");
                    this.imagePath = results.getString("ImagePath");
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

    public void Delete() throws MeasurementsException{
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

    public void Commit() throws MeasurementsException{
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