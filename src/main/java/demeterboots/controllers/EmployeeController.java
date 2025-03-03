package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Employee;
import demeterboots.models.objects.Job;
import demeterboots.models.util.exceptions.EmployeeException;
import demeterboots.models.util.exceptions.JobException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class EmployeeController {
    
    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees() {
        List<Employee> employees;
        try {
            employees = Employee.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (EmployeeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") String id) {
        Employee employee;
        try {
            employee = Employee.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (EmployeeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/employees/jobs/{id}")
    public ResponseEntity<?> getJobsByEmployeeID(@PathVariable("id") String id) {
        List<Job> jobs;
        try {
            jobs = Job.getJobsByEmployeeID(id);
            return ResponseEntity.ok(jobs);
        } catch (JobException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}