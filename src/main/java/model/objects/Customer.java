package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private String street;
    private String postCode;
    private String city;
    private String email;
    private String phone;

    private static String detailsFunction  = "SELECT * FROM demeterboots.JobNotification_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.JobNotification_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.JobNotification_Commit(?, ?, ?, ?, ?)";
    private static String notNotifiedFunction  = "CALL demeterboots.JobNotification_NotNotified(?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Customer() {
    }

    public Customer(String id) {
        Details(id);
    }

    public Customer(String id, String firstName, String lastName, String street, String postCode, String city, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.postCode = postCode;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public void Details(String id) {
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        try(PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Commit() {
        try(PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, street);
            stmt.setString(5, postCode);
            stmt.setString(6, city);
            stmt.setString(7, email);
            stmt.setString(8, phone);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //setter and getter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
