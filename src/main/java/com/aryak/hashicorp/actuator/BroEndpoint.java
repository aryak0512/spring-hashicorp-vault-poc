package com.aryak.hashicorp.actuator;


import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RECOMMENDED APPROACH for multiple distinct path mappings
 * Using @RestControllerEndpoint gives you full Spring MVC capabilities
 * with proper multiple path mappings:
 * - GET /actuator/errors -> summary
 * - GET /actuator/errors/all -> all errors
 * - GET /actuator/errors/{id} -> specific item by id
 * This is cleaner and more intuitive than handling routing logic manually.
 */
@Component
@RestControllerEndpoint(id = "bro")
public class BroEndpoint {
    /**
     * GET /actuator/errors
     * Returns summary information
     */
    @GetMapping
    public ResponseEntity<String> getSummary() {
        return ResponseEntity.ok("bro got executed");
    }

    /**
     * GET /actuator/errors/all
     * Returns all errors
     */
    @GetMapping("/error")
    public ResponseEntity<String> getErrors() {
        return ResponseEntity.ok("bro got executed for error");
    }

    /**
     * GET /actuator/errors/{id}
     * Returns a specific item by ID
     * <p>
     * NOTE: This mapping must come AFTER the /all mapping
     * to avoid /all being captured as an {id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable String id) {
        return ResponseEntity.ok("bro got executed with p.v. : %s".formatted(id));
    }

    /**
     * GET /actuator/errors/status/{status}
     * Get errors by status (Active, Inactive, Pending)
     * Additional example of nested paths
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<String> getErrorsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(status);
    }

}
