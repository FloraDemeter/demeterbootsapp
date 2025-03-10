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
import demeterboots.models.util.exceptions.JobNotificationException;

public class JobNotification {

    @JsonProperty("id")
    private int id;
    @JsonProperty("previousEmployeeID")
    private String previousEmployeeID;
    @JsonProperty("newEmployeeID")
    private String newEmployeeID;
    @JsonProperty("isNotified")
    private Boolean isNotified;
    @JsonProperty("taskID")
    private String taskID;
    @JsonProperty("taskType")
    private String taskType;

    @JsonIgnore
    private static String detailsFunction  = "SELECT * FROM demeterboots.JobNotification_Details(?)";
    @JsonIgnore
    private static String deleteFunction  = "CALL demeterboots.JobNotification_Delete(?)";
    @JsonIgnore
    private static String commitFunction  = "CALL demeterboots.JobNotification_Commit(?, ?, ?, ?, ?, ?)";
    @JsonIgnore
    private static String notNotifiedFunction  = "CALL demeterboots.JobNotification_NotNotified(?)";

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

    public JobNotification() {
    }

    public JobNotification(int id) throws JobNotificationException {
        JobNotification jobNotification;

        if (id == 0) {
            return;
        }

        jobNotification = getJobNotificationDetails(id);

        if (jobNotification != null) {
            this.id = jobNotification.id;
            this.previousEmployeeID = jobNotification.previousEmployeeID;
            this.newEmployeeID = jobNotification.newEmployeeID;
            this.isNotified = jobNotification.isNotified;
            this.taskID = jobNotification.taskID;
            this.taskType = jobNotification.taskType;
        }
    }

    public JobNotification(int id, String previousEmployeeID, String newEmployeeID, Boolean isNotified, String taskID, String taskType) {
        this.id = id;
        this.previousEmployeeID = previousEmployeeID;
        this.newEmployeeID = newEmployeeID;
        this.isNotified = isNotified;
        this.taskID = taskID;
        this.taskType = taskType;
    }

//--------------------------------------------------------
//endregion
    
//region Methods
//--------------------------------------------------------

    @JsonIgnore
    public static List<JobNotification> getAllJobNotifications() throws JobNotificationException {
            return Details(0);
        }

    @JsonIgnore
    public static List<JobNotification> getUnnotifiedJobs(String employeeID) throws JobNotificationException {
        return NotNotified(employeeID);
    }

    @JsonIgnore
    public final static JobNotification getJobNotificationDetails(int id) throws JobNotificationException {
        List<JobNotification> jobNotifications = Details(id);
        if (!jobNotifications.isEmpty()) {
            return jobNotifications.get(0);
        }
        return null;
    }

    @JsonIgnore
    public void deleteJobNotification() throws JobNotificationException {
        Delete();
    }

    @JsonIgnore
    public void commitJobNotification() throws JobNotificationException {
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


//region Database methods
//--------------------------------------------------------

    private static List<JobNotification> Details(int id) throws JobNotificationException{
        List<JobNotification> jobNotifs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setInt(1, id == 0 ? null : id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JobNotification jobNotif = new JobNotification();
                    jobNotif.id = rs.getInt("id");
                    jobNotif.previousEmployeeID = rs.getString("previousEmployeeID");
                    jobNotif.newEmployeeID = rs.getString("newEmployeeID");
                    jobNotif.isNotified = rs.getBoolean("isNotified");
                    jobNotif.taskID = rs.getString("taskID");
                    jobNotif.taskType = rs.getString("taskType");
                    jobNotifs.add(jobNotif);
                }
            }
        } catch (Exception e) {
            if (id > 0) {
                throw new JobNotificationException(String.format("Error fetching Job Notification with ID %s", id), e);
            }
            throw new JobNotificationException("Error fetching Job Notifications", e);
        }
        return jobNotifs;
    }

    private void Commit () throws JobNotificationException{
        try (PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setInt(1, id);
            stmt.setString(2, previousEmployeeID);
            stmt.setString(3, newEmployeeID);
            stmt.setBoolean(4, isNotified);
            stmt.setString(5, taskID);
            stmt.setString(6, taskType);
            stmt.execute();
        }catch (SQLException e) {
            throw new JobNotificationException(String.format("Error committing Job Notification with ID %s", id), e);
        }
    }

    private void Delete () throws JobNotificationException{
        if (id == 0) {
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setInt(1, this.id);
            stmt.execute();
        }catch (SQLException e) {
           throw new JobNotificationException(String.format("Error deleting Job Notification with ID %s", id), e);
        }
    }

    public static List<JobNotification> NotNotified(String employeeId) throws JobNotificationException {
        List<JobNotification> jobNotifications = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(notNotifiedFunction)) {
            stmt.setString(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JobNotification jobNotif = new JobNotification();
                    jobNotif.id = rs.getInt("id");
                    jobNotif.previousEmployeeID = rs.getString("previousEmployeeID");
                    jobNotif.newEmployeeID = rs.getString("newEmployeeID");
                    jobNotif.isNotified = rs.getBoolean("isNotified");
                    jobNotif.taskID = rs.getString("taskID");
                    jobNotif.taskType = rs.getString("taskType");
                    jobNotifications.add(jobNotif);
                }
            }
        } catch (SQLException e) {
           throw new JobNotificationException(String.format("Error fetching Job Notifications for Employee with ID %s", employeeId), e);
        }
        return jobNotifications;
    }

//--------------------------------------------------------
//endregion

//region Getters and Setters
//--------------------------------------------------------

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getPreviousEmployeeID() {
        return previousEmployeeID;
    }

    public void setPreviousEmployeeID(String previousEmployeID) {
        this.previousEmployeeID = previousEmployeID;
    }

    public String getNewEmployeeID() {
        return newEmployeeID;
    }

    public void setNewEmployeeID(String newEmployeeID) {
        this.newEmployeeID = newEmployeeID;
    }

    public Boolean getIsNotified() {
        return isNotified;
    }

    public void setIsNotified(Boolean isNotified) {
        this.isNotified = isNotified;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

}
//--------------------------------------------------------
//endregion
