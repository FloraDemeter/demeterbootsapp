package model.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.util.DataContext;
import model.util.exceptions.JobException;

public class Job {
    private String id;
    private String employeeID;
    private Integer status;
    private String taskID;
    private String TaskType;

    private static String detailsFunction  = "SELECT * FROM demeterboots.Job_Details(?, ?)";
    private static String deleteFunction  = "CALL demeterboots.Job_Delete(?)";
    private static String commitFunction  = "CALL demeterboots.Job_Commit(?, ?, ?, ?, ?)";

    private final static DataContext dataContext = DataContext.getInstance();
    private static Connection connection = dataContext.getConnection();

//region Constructors
//--------------------------------------------------------

    public Job() {
    }

    public Job(String id) throws JobException{ 
        Job job;

        if (id == null) {
            return;
        }

        job = getJobByID(id);

        if (job != null) {
            this.id = job.id;
            this.employeeID = job.employeeID;
            this.status = job.status;
            this.taskID = job.taskID;
            this.TaskType = job.TaskType;
        }
    }

    public Job(String id, String employeeID, Integer status, String taskID, String TaskType) {
        this.id = id;
        this.employeeID = employeeID;
        this.status = status;
        this.taskID = taskID;
        this.TaskType = TaskType;
    }

//--------------------------------------------------------
//endregion

//region Methods
//--------------------------------------------------------

    public List<Job> getAllJobs() throws JobException{
        return Details("","");
    }

    public List<Job> getJobsByEmployeeID(String employeeID) throws JobException{
        return Details( "", employeeID);
    }

    public final Job getJobByID(String id) throws JobException{
        List<Job> jobs = Details(id, "");
        if (!jobs.isEmpty()) {
            return jobs.get(0);
        }

        return null;
    }

//--------------------------------------------------------
//endregion

//region Database Mehods
//--------------------------------------------------------

    public List<Job> Details(String id, String employeeID) throws JobException{
        List<Job> jobs = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(detailsFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, employeeID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Job job = new Job();
                    job.id = rs.getString("id");
                    job.employeeID = rs.getString("employeeID");
                    job.status = rs.getInt("status");
                    job.taskID = rs.getString("taskID");
                    job.TaskType = rs.getString("TaskType");
                    jobs.add(job);
                }
            }
        } catch (SQLException e) {
            if (id != null) {
                throw new JobException(String.format("Error fetching Job details by Job ID %s", id), e);
            }
            
            if (employeeID != null) {
                throw new JobException(String.format("Error fetching Job details by Employee ID %s", employeeID), e);
            }

            throw new JobException("Error fetching Job details", e);
        }

        return jobs;
    }

    public void Delete() throws JobException{
        if (id == null) {
            return;
        }

        try(PreparedStatement stmt = connection.prepareStatement(deleteFunction)) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new JobException(String.format("Error deleting Job with ID %s", id), e);
        }
    }

    public void Commit() throws JobException{
        try(PreparedStatement stmt = connection.prepareStatement(commitFunction)) {
            stmt.setString(1, id);
            stmt.setString(2, employeeID);
            stmt.setInt(3, status);
            stmt.setString(4, taskID);
            stmt.setString(5, TaskType);
            stmt.execute();
        } catch (SQLException e) {
            throw new JobException(String.format("Error committing Job with ID %s", id), e);
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
//--------------------------------------------------------
//endregion
