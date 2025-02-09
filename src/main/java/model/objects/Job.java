package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DataContext;

public class Job {
    private String id;
    private String employeeID;
    private Integer status;
    private String taskID;
    private String TaskType;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Job_Details(?)";
    private static String deleteFunction  = "CALL demeterboots.Job_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Job_Commit(?, ?, ?, ?, ?)";

    private static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

    public Job() {
    }

    public Job(String id) {
        Details(id);
    }

    public Job(String id, String employeeID, Integer status, String taskID, String TaskType) {
        this.id = id;
        this.employeeID = employeeID;
        this.status = status;
        this.taskID = taskID;
        this.TaskType = TaskType;
    }

    public void Details(String id) {
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getString("id");
                    this.employeeID = rs.getString("employeeID");
                    this.status = rs.getInt("status");
                    this.taskID = rs.getString("taskID");
                    this.TaskType = rs.getString("TaskType");
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
            stmt.setString(2, employeeID);
            stmt.setInt(3, status);
            stmt.setString(4, taskID);
            stmt.setString(5, TaskType);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskType() {
        return TaskType;
    }

    public void setTaskType(String taskType) {
        TaskType = taskType;
    }
}