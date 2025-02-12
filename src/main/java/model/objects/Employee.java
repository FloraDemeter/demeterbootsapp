package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.util.DataContext;
import model.util.exceptions.EmployeeException;
import model.util.passwordHashing;

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
    private Integer accessLevel;
    private Date startDate;
    private Date endDate;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Employee_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.Employee_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Employee_Commit(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

//region Contructors
//--------------------------------------------------------
    
    public Employee() {
    }

    public Employee(String value, boolean isUsername) throws EmployeeException {
        Employee employee;

        if (value == null) {
            return;
        }
        
        if (isUsername) {
            employee = getEmployeeDetails(value, "");
        } else {
            employee = getEmployeeDetails("", value);
        }
        if (employee != null) {
            this.id = employee.id;
            this.firstName = employee.firstName;
            this.lastName = employee.lastName;
            this.street = employee.street;
            this.postCode = employee.postCode;
            this.city = employee.city;
            this.email = employee.email;
            this.phone = employee.phone;
            this.username = employee.username;
            this.password = employee.password;
            this.isActive = employee.isActive;
            this.accessLevel = employee.accessLevel;
            this.startDate = employee.startDate;
            this.endDate = employee.endDate;
        }
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
        this.accessLevel = accessLevel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------
    
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    public List<Employee> getAllEmployees() throws EmployeeException {
        return Details("","");
    }

    public Employee getEmployeeById(String id) throws EmployeeException {
        return new Employee(id, false);
    }

    public Employee getEmployeeByUserName(String username) throws EmployeeException {
        return new Employee(username, true);
    }

    public final Employee getEmployeeDetails(String id, String username) throws EmployeeException {
        List<Employee> employees = Details(id, username);
        if (!employees.isEmpty()) {
            return employees.get(0);
        }
        return null;
    }

    public Boolean tryLoginUser(String username, String password) throws EmployeeException {
        dataContext.currentUser = getEmployeeByUserName(username);
        if (dataContext.currentUser == null) {
            return false;
        }
        String encryptedPassword = passwordHashing.encryptPassword(password);

        return dataContext.currentUser.password.equals(encryptedPassword);
    }

//--------------------------------------------------------
//endregion
        
//region Database methods
//--------------------------------------------------------
    
    public List<Employee> Details(String id, String username) throws EmployeeException {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.id = rs.getString("id");
                    employee.firstName = rs.getString("firstName");
                    employee.lastName = rs.getString("lastName");
                    employee.street = rs.getString("street");
                    employee.postCode = rs.getString("postCode");
                    employee.city = rs.getString("city");
                    employee.email = rs.getString("email");
                    employee.phone = rs.getString("phone");
                    employee.username = rs.getString("username");
                    employee.password = rs.getString("password");
                    employee.isActive = rs.getBoolean("isActive");
                    employee.accessLevel = rs.getInt("AccessLevel");
                    employee.startDate = rs.getDate("startDate");
                    employee.endDate = rs.getDate("endDate");
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new EmployeeException(String.format("Error fetching employee details with Employee ID %s", id ), e);
            }

            if (username != null) {
                throw new EmployeeException(String.format("Error fetching employee details with Employee Username %s", username ), e);
            }

            throw new EmployeeException("Error fetching employee details", e);
        }

        return employees;
    }

    public void Delete() throws EmployeeException {
        if (id == null) {
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new EmployeeException(String.format("Error deleting employee with ID %s", id), e);
        }
    }

    public void Commit() throws EmployeeException {
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
            stmt.setInt(12, accessLevel);
            stmt.setDate(13, (java.sql.Date) startDate);
            stmt.setDate(14, (java.sql.Date) endDate);
            stmt.execute();
        } catch (SQLException e) {
            throw new EmployeeException(String.format("Error committing employee details for user %s", id), e);
        }
    }
//--------------------------------------------------------
//endregion
    

//region Getters and Setters Methods
//--------------------------------------------------------

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
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLvl) {
        accessLevel = accessLvl;
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
//--------------------------------------------------------
//endregion
