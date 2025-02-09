package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.DataContext;

public class Employee {

    private String id;
    private String firstName;
    private String lastName;
    private String street;
    private String postCode;
    private String city;
    private String email;
    private String phone;
    private String username;
    private String password;
    private Boolean isActive;
    private Integer AccessLevel;
    private Date startDate;
    private Date endDate;


    private static String detailsFunction  = "SELECT * FROM demeterboots.JobNotification_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.JobNotification_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.JobNotification_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Employee() {
    }

    public Employee(String id) {
        Details(id);
    }

    public Employee(String id, String firstName, String lastName, String street, String postCode, String city, String email, String phone, String username, String password, Boolean isActive, Integer accessLevel, Date startDate, Date endDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.postCode = postCode;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        AccessLevel = accessLevel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void Details(String id) {
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.firstName = rs.getString("firstName");
                    this.lastName = rs.getString("lastName");
                    this.street = rs.getString("street");
                    this.postCode = rs.getString("postCode");
                    this.city = rs.getString("city");
                    this.email = rs.getString("email");
                    this.phone = rs.getString("phone");
                    this.username = rs.getString("username");
                    this.password = rs.getString("password");
                    this.isActive = rs.getBoolean("isActive");
                    AccessLevel = rs.getInt("AccessLevel");
                    this.startDate = rs.getDate("startDate");
                    this.endDate = rs.getDate("endDate");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete(String id) {
        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try (PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, street);
            stmt.setString(5, postCode);
            stmt.setString(6, city);
            stmt.setString(7, email);
            stmt.setString(8, phone);
            stmt.setString(9, username);
            stmt.setString(10, password);
            stmt.setBoolean(11, isActive);
            stmt.setInt(12, AccessLevel);
            stmt.setDate(13, (java.sql.Date) startDate);
            stmt.setDate(14, (java.sql.Date) endDate);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getter and Setter methods
    public String getEmployeeID() {
        return id;
    }

    public void setEmployeeID(String employeeID) {
        this.id = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getAccessLevel() {
        return AccessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        AccessLevel = accessLevel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
