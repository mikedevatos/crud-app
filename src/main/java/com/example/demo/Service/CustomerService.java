package com.example.demo.Service;

import com.example.demo.model.Customer;
import com.example.demo.repo.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

 CustomerRepository customerRepository;
    public Customer createCustomer(Customer customer) {
        customerRepository.save(customer);

    }
}
