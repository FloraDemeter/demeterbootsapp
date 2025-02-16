package models.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.util.DataContext;
import models.util.exceptions.CustomerException;
import models.util.exceptions.DatabaseException;

public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String street;
    private String postCode;
    private String city;
    private String email;
    private String phone;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Customer_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.Customer_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Customer_Commit(?, ?, ?, ?, ?, ?, ?, ?)";

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

    public Customer() {
    }

    public Customer(String id) throws CustomerException{
        Customer customer = getCustomerDetails(id);
        if (customer != null) {
            this.id = customer.id;
            this.firstName = customer.firstName;
            this.lastName = customer.lastName;
            this.street = customer.street;
            this.postCode = customer.postCode;
            this.city = customer.city;
            this.email = customer.email;
            this.phone = customer.phone;
        }
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
//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    public List<Customer> getAllCustomers() throws CustomerException {
        return Details("");
    }

    public Customer getCustomer(String id) throws CustomerException {
        return new Customer(id);
    }

    private Customer getCustomerDetails(String id) throws CustomerException {
        List<Customer> customers = Details(id);
        if (!customers.isEmpty()) {
            return customers.get(0);
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

//region Database methods
//--------------------------------------------------------

    public List<Customer> Details(String id) throws CustomerException {
        List<Customer> customers = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.id = rs.getString("p_id");
                    customer.firstName = rs.getString("firstname");
                    customer.lastName = rs.getString("lastname");
                    customer.street = rs.getString("street");
                    customer.postCode = rs.getString("postcode");
                    customer.city = rs.getString("city");
                    customer.email = rs.getString("email");
                    customer.phone = rs.getString("phonenumber");
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            if (id.isEmpty()) {
                throw new CustomerException("Error getting all customers", e);
            } else {
                throw new CustomerException(String.format("Error getting customer with ID %s", id), e);
            }
        }
        return customers;
    }

    public void Delete() throws CustomerException {
        if (id == null) {
            return;
        }

        try(PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new CustomerException(String.format("Error deleting customer with ID %s", id), e);
        }
    }

    public void Commit() throws CustomerException {
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
            throw new CustomerException(String.format("Error committing customer with ID %s", id), e);
        }
    }
//--------------------------------------------------------
//endregion
    

//region Getters and Setters Methods
//--------------------------------------------------------

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

//--------------------------------------------------------
//endregion
}