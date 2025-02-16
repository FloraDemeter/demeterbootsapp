package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.util.DataContext;
import model.util.exceptions.JobNotificationException;

public class JobNotification {
    private String id;
    private String previousEmployeeID;
    private String newEmployeeID;
    private Boolean isNotified;
    private String taskID;
    private String taskType;

    private static String detailsFunction  = "SELECT * FROM demeterboots.JobNotification_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.JobNotification_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.JobNotification_Commit(?, ?, ?, ?, ?, ?)";
    private static String notNotifiedFunction  = "CALL demeterboots.JobNotification_NotNotified(?)";

    private final static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

//region Constructors
//--------------------------------------------------------

    public JobNotification() {
    }

    public JobNotification(String id) throws JobNotificationException {
        JobNotification jobNotification;

        if (id == null) {
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

    public JobNotification(String id, String previousEmployeeID, String newEmployeeID, Boolean isNotified, String taskID, String taskType) {
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

    public List<JobNotification> getAllJobNotifications() throws JobNotificationException {
        return Details("");
    }

    public List<JobNotification> getUnnotifiedJobs(String employeeID) throws JobNotificationException {
        return NotNotified(employeeID);
    }

    public final JobNotification getJobNotificationDetails(String id) throws JobNotificationException {
        List<JobNotification> jobNotifications = Details(id);
        if (!jobNotifications.isEmpty()) {
            return jobNotifications.get(0);
        }
        return null;
    }

//--------------------------------------------------------
//endregion


//region Database methods
//--------------------------------------------------------

    public final List<JobNotification> Details(String id) throws JobNotificationException{
        List<JobNotification> jobNotifs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JobNotification jobNotif = new JobNotification();
                    jobNotif.id = rs.getString("id");
                    jobNotif.previousEmployeeID = rs.getString("previousEmployeeID");
                    jobNotif.newEmployeeID = rs.getString("newEmployeeID");
                    jobNotif.isNotified = rs.getBoolean("isNotified");
                    jobNotif.taskID = rs.getString("taskID");
                    jobNotif.taskType = rs.getString("taskType");
                    jobNotifs.add(jobNotif);
                }
            }
        } catch (Exception e) {
            if (id != null) {
                throw new JobNotificationException(String.format("Error fetching Job Notification with ID %s", id), e);
            }
            throw new JobNotificationException("Error fetching Job Notifications", e);
        }
        return jobNotifs;
    }

    public void Commit () throws JobNotificationException{
        try (PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
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

    public void Delete () throws JobNotificationException{
        if (id == null) {
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, this.id);
            stmt.execute();
        }catch (SQLException e) {
           throw new JobNotificationException(String.format("Error deleting Job Notification with ID %s", id), e);
        }
    }

    public List<JobNotification> NotNotified(String employeeId) throws JobNotificationException {
        List<JobNotification> jobNotifications = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(notNotifiedFunction)) {
            stmt.setString(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JobNotification jobNotif = new JobNotification();
                    jobNotif.id = rs.getString("id");
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

    public String getID() {
        return id;
    }

    public void setID(String id) {
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
