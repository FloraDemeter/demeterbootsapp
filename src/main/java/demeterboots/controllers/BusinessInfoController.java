package demeterboots.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.PaymentType;
import demeterboots.models.objects.Status;
import demeterboots.models.util.exceptions.PaymentTypeException;
import demeterboots.models.util.exceptions.StatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BusinessInfoController {
    
    @GetMapping("/businessinfo/status")
    public ResponseEntity<?> getStatuses() {
        List<Status> stat;
        try {
            stat = Status.getAllStatus();
            return ResponseEntity.ok(stat);
        } catch (StatusException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/businessinfo/paymenttypes")
    public ResponseEntity<?> getPaymentTypes() {
        List<PaymentType> paymentTypes;
        try {
            paymentTypes = PaymentType.getAllPaymentType();
            return ResponseEntity.ok(paymentTypes);
        } catch (PaymentTypeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}