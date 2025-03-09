package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Job;
import demeterboots.models.util.exceptions.JobException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class JobController {
    
    @GetMapping("/jobs")
    public ResponseEntity<?> getJobs() {
        List<Job> jobs;
        try {
            jobs = Job.getAllJobs();
            return ResponseEntity.ok(jobs);
        } catch (JobException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/jobs/{id}")
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