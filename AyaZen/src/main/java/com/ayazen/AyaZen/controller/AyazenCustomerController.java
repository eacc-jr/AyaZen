package com.ayazen.AyaZen.controller;

import java.util.List;

import com.ayazen.AyaZen.entity.AyazenCustomer;

import com.ayazen.AyaZen.entity.AyazenProject;
import com.ayazen.AyaZen.service.AyazenCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customers")
public class AyazenCustomerController {

    @Autowired
    private AyazenCustomerService customerService;

    @GetMapping
    public ResponseEntity<List<AyazenCustomer>> getAllCustomers() {
        List<AyazenCustomer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AyazenCustomer> getCustomerById(@PathVariable Long id) {
        AyazenCustomer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<AyazenCustomer> createCustomer(@RequestBody AyazenCustomer customer) {
        AyazenCustomer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AyazenCustomer> updateCustomer(@PathVariable Long id, @RequestBody AyazenCustomer customer) {
        AyazenCustomer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/projects")
    public ResponseEntity<List<AyazenProject>> getProjectsByCustomerId(@PathVariable Long customerId) {
        List<AyazenProject> projects = customerService.getProjectsByCustomerId(customerId);
        return ResponseEntity.ok(projects);
    }

}