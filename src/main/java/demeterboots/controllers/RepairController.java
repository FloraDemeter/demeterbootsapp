package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Repair;
import demeterboots.models.objects.RepairLine;
import demeterboots.models.util.exceptions.RepairException;
import demeterboots.models.util.exceptions.RepairLineException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RepairController {
    
    @GetMapping("/repairs")
    public ResponseEntity<?> getRepairs() {
        List<Repair> repairs;
        try {
            repairs = Repair.getAllRepairs();
            return ResponseEntity.ok(repairs);
        } catch (RepairException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/repairs/{id}")
    public ResponseEntity<?> getRepairByID(@PathVariable("id") String id) {
        Repair repair;
        try {
            repair = Repair.getRepairDetails(id);
            return ResponseEntity.ok(repair);
        } catch (RepairException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/repairs/repairline/{id}")
    public ResponseEntity<?> getRepairLineByRepairID(@PathVariable("id") String id) {
        List<RepairLine> lines;
        try {
            lines = RepairLine.getAllRepairLinesForRepair(id);
            return ResponseEntity.ok(lines);
        } catch (RepairLineException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}