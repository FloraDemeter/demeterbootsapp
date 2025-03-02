package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Customer;
import demeterboots.models.objects.Measurements;
import demeterboots.models.util.exceptions.CustomerException;
import demeterboots.models.util.exceptions.MeasurementsException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CustomerController {
    
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers() {
        List<Customer> customers;
        try {
            customers = Customer.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (CustomerException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerByID(@PathVariable("id") String id) {
        Customer customer;
        try {
            customer = Customer.getCustomer(id);
            return ResponseEntity.ok(customer);
        } catch (CustomerException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/customers/measurements/{id}")
    public ResponseEntity<?> getMeasurementID(@PathVariable("id") String id) {
        List<Measurements> meas;
        try {
            meas = Measurements.getAllMeasurementsByCustomer(id);
            return ResponseEntity.ok(meas);
        } catch (MeasurementsException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}