package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Order;
import demeterboots.models.objects.OrderLine;
import demeterboots.models.util.exceptions.OrderException;
import demeterboots.models.util.exceptions.OrderLineException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class OrderController {
    
    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        List<Order> orders;
        try {
            orders = Order.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (OrderException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderByID(@PathVariable("id") String id) {
        Order order;
        try {
            order = Order.getOrderDetails(id);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/orders/orderline/{id}")
    public ResponseEntity<?> getOrderLineByOrderID(@PathVariable("id") String id) {
        List<OrderLine> lines;
        try {
            lines = OrderLine.getAllOrderLinesPerOrder(id);
            return ResponseEntity.ok(lines);
        } catch (OrderLineException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}