package demeterboots.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    // Handle requests to the root path '/'
    @GetMapping("/")
    public String root() {
        return "Welcome to the root path!";
    }
}
