package com.example.demo.controller;


import com.example.demo.Service.CustomerService;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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




    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<Customer>> getPageCustomers(@PathVariable Integer page, @PathVariable int size) {


//        long total = service.count();
        Integer SIZE = 5;
        List<Customer> customers = service.findAllbyPage(page,SIZE);

//        CustomersDTO customDTO=new CustomersDTO();
//        customDTO.setCustomer(customers);
//        customDTO.setCount(total);

//        log.debug("showing   customers page  "+page +"  and size  " + size);
        return new ResponseEntity<>(customers, HttpStatus.OK);

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
