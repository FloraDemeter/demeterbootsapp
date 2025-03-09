package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Invoice;
import demeterboots.models.objects.InvoiceLine;
import demeterboots.models.util.exceptions.InvoiceException;
import demeterboots.models.util.exceptions.InvoiceLineException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class InvoiceController {
    
    @GetMapping("/invoices")
    public ResponseEntity<?> getInvoices() {
        List<Invoice> invoices;
        try {
            invoices = Invoice.getAllInvoices();
            return ResponseEntity.ok(invoices);
        } catch (InvoiceException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<?> getInvoiceByID(@PathVariable("id") String id) {
        Invoice invoice;
        try {
            invoice = Invoice.getInvoice(id);
            return ResponseEntity.ok(invoice);
        } catch (InvoiceException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/invoices/invoiceline/{id}")
    public ResponseEntity<?> getInvoiceLineByInvoiceID(@PathVariable("id") String id) {
        List<InvoiceLine> lines;
        try {
            lines = InvoiceLine.getAllInvoiceLinesByInvoiceID(id);
            return ResponseEntity.ok(lines);
        } catch (InvoiceLineException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}