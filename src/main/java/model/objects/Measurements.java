package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.DataContext;

public class Measurements {

    private String id;
    private String CustomerID;
    private Date Date;
    private String Notes;
    private Double Feet;
    private Double Bunion;
    private Double HighPoint;
    private Double Heel;
    private Double Ankle;
    private Double Calf;
    private Double UnderKnee;
    private Double Height;
    private Double CalfHeight;
    private Double TMark;
    private String ImagePath;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Measurements_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.Measurements_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Measurements_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Measurements() {
    }

    public Measurements(String id) {
        Detail(id);
    }

    public Measurements(String id, String CustomerID, Date Date, String Notes, Double Feet, Double Bunion, Double HighPoint, Double Heel, Double Ankle, Double Calf, Double UnderKnee, Double Height, Double CalfHeight, Double TMark, String ImagePath) {
        this.id = id;
        this.CustomerID = CustomerID;
        this.Date = Date;
        this.Notes = Notes;
        this.Feet = Feet;
        this.Bunion = Bunion;
        this.HighPoint = HighPoint;
        this.Heel = Heel;
        this.Ankle = Ankle;
        this.Calf = Calf;
        this.UnderKnee = UnderKnee;
        this.Height = Height;
        this.CalfHeight = CalfHeight;
        this.TMark = TMark;
        this.ImagePath = ImagePath;
    }

    public void Detail(String id) {
        try(PreparedStatement statement = connection.prepareStatement(detailsFunction)) {
            statement.setString(1, id);
            try(ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    this.id = results.getString("id");
                    this.CustomerID = results.getString("CustomerID");
                    this.Date = results.getDate("Date");
                    this.Notes = results.getString("Notes");
                    this.Feet = results.getDouble("Feet");
                    this.Bunion = results.getDouble("Bunion");
                    this.HighPoint = results.getDouble("HighPoint");
                    this.Heel = results.getDouble("Heel");
                    this.Ankle = results.getDouble("Ankle");
                    this.Calf = results.getDouble("Calf");
                    this.UnderKnee = results.getDouble("UnderKnee");
                    this.Height = results.getDouble("Height");
                    this.CalfHeight = results.getDouble("CalfHeight");
                    this.TMark = results.getDouble("TMark");
                    this.ImagePath = results.getString("ImagePath");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try(PreparedStatement statement = connection.prepareStatement(deleteFunction)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try(PreparedStatement statement = connection.prepareStatement(commitFunction)) {
            statement.setString(1, id);
            statement.setString(2, CustomerID);
            statement.setDate(3, (java.sql.Date) Date);
            statement.setString(4, Notes);
            statement.setDouble(5, Feet);
            statement.setDouble(6, Bunion);
            statement.setDouble(7, HighPoint);
            statement.setDouble(8, Heel);
            statement.setDouble(9, Ankle);
            statement.setDouble(10, Calf);
            statement.setDouble(11, UnderKnee);
            statement.setDouble(12, Height);
            statement.setDouble(13, CalfHeight);
            statement.setDouble(14, TMark);
            statement.setString(15, ImagePath);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //setters and getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public Double getFeet() {
        return Feet;
    }

    public void setFeet(Double feet) {
        Feet = feet;
    }

    public Double getBunion() {
        return Bunion;
    }

    public void setBunion(Double bunion) {
        Bunion = bunion;
    }

    public Double getHighPoint() {
        return HighPoint;
    }

    public void setHighPoint(Double highPoint) {
        HighPoint = highPoint;
    }

    public Double getHeel() {
        return Heel;
    }

    public void setHeel(Double heel) {
        Heel = heel;
    }

    public Double getAnkle() {
        return Ankle;
    }

    public void setAnkle(Double ankle) {
        Ankle = ankle;
    }

    public Double getCalf() {
        return Calf;
    }

    public void setCalf(Double calf) {
        Calf = calf;
    }

    public Double getUnderKnee() {
        return UnderKnee;
    }

    public void setUnderKnee(Double underKnee) {
        UnderKnee = underKnee;
    }

    public Double getHeight() {
        return Height;
    }

    public void setHeight(Double height) {
        Height = height;
    }

    public Double getCalfHeight() {
        return CalfHeight;
    }

    public void setCalfHeight(Double calfHeight) {
        CalfHeight = calfHeight;
    }

    public Double getTMark() {
        return TMark;
    }

    public void setTMark(Double TMark) {
        this.TMark = TMark;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
