package com.example.demo.controller;


import com.example.demo.Service.CustomerService;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/customer")



public class CustomerController {


    @Autowired
    private CustomerService service;

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customer) {
        return service.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO updatedCustomer) {
        return service.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        return service.deleteCustomer(id);
    }

}
