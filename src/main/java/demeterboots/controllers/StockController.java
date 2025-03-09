package demeterboots.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demeterboots.models.objects.Leather;
import demeterboots.models.objects.ProductStyle;
import demeterboots.models.objects.ProductStyleType;
import demeterboots.models.objects.ProductType;
import demeterboots.models.objects.RepairCategory;
import demeterboots.models.util.exceptions.LeatherException;
import demeterboots.models.util.exceptions.ProductStyleException;
import demeterboots.models.util.exceptions.ProductStyleTypeException;
import demeterboots.models.util.exceptions.ProductTypeException;
import demeterboots.models.util.exceptions.RepairCategoryException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class StockController {
    
    @GetMapping("/stock/leathers")
    public ResponseEntity<?> getLeathers() {
        List<Leather> leathers;
        try {
            leathers = Leather.getAllLeathers();
            return ResponseEntity.ok(leathers);
        } catch (LeatherException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stock/productstyles")
    public ResponseEntity<?> getProductStyles() {
        List<ProductStyle> styles;
        try {
            styles = ProductStyle.getAllProductStyles();
            return ResponseEntity.ok(styles);
        } catch (ProductStyleException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stock/productstyletypes")
    public ResponseEntity<?> getProductStyleTypes() {
        List<ProductStyleType> types;
        try {
            types = ProductStyleType.getAllProductTypes();
            return ResponseEntity.ok(types);
        } catch (ProductStyleTypeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stock/producttypes")
    public ResponseEntity<?> getProductTypes() {
        List<ProductType> types;
        try {
            types = ProductType.getAllProductTypes();
            return ResponseEntity.ok(types);
        } catch (ProductTypeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/stock/repaircategories")
    public ResponseEntity<?> getRepairCategories() {
        List<RepairCategory> cats;
        try {
            cats = RepairCategory.getAllRepairCategory();
            return ResponseEntity.ok(cats);
        } catch (RepairCategoryException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}