package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.DataContext;

public class JobNotification {
    private String id;
    private String jobTitle;
    private String jobDescription;
    private LocalDateTime notificationTime;
    private boolean isRead;

    private static String detailsFunction  = "SELECT * FROM demeterboots.JobNotification_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.JobNotification_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.JobNotification_Commit(?, ?, ?, ?, ?)";
    private static String notNotifiedFunction  = "CALL demeterboots.JobNotification_NotNotified(?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public JobNotification() {
    }

    public JobNotification(String id) {
        Details(id);
    }

    public JobNotification(String id, String jobTitle, String jobDescription, LocalDateTime notificationTime, boolean isRead) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.notificationTime = notificationTime;
        this.isRead = isRead;
    }

    public void Details(String id) {
        
        try (PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.jobTitle = rs.getString("jobTitle");
                    this.jobDescription = rs.getString("jobDescription");
                    this.notificationTime = rs.getTimestamp("notificationTime").toLocalDateTime();
                    this.isRead = rs.getBoolean("isRead");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Commit () {
        try (PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, this.id);
            stmt.setString(2, this.jobTitle);
            stmt.setString(3, this.jobDescription);
            stmt.setObject(4, this.notificationTime);
            stmt.setBoolean(5, this.isRead);
            stmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete () {
        try (PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, this.id);
            stmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<JobNotification> NotNotified(int employeeId) {
        List<JobNotification> jobNotifications = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(notNotifiedFunction)) {
            stmt.setInt(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    jobNotifications.add(new JobNotification(
                        rs.getString("id"),
                        rs.getString("jobTitle"),
                        rs.getString("jobDescription"),
                        rs.getTimestamp("notificationTime").toLocalDateTime(),
                        rs.getBoolean("isRead")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobNotifications;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
