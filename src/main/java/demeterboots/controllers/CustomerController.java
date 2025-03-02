package demeterboots.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Customer;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CustomerController {
    
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("1L", "John", "Doe", "Anywhere Street 123", "OESA 1442", "Somewhere", "john@example.com", "2153215115"));
        return ResponseEntity.ok(customers);
    }

}