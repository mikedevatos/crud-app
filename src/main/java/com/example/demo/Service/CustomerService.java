package com.example.demo.Service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.repo.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service
public class CustomerService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CustomerRepository repository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.map(customerDTO, Customer.class);
        Customer savedCustomer = repository.save(customer);
        return mapper.map(savedCustomer, CustomerDTO.class);
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.map(customer, CustomerDTO.class);

    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO updatedCustomerDTO) {
        Customer existingCustomer = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Customer updatedCustomer = mapper.map(existingCustomer,Customer.class);
        BeanUtils.copyProperties(updatedCustomer, existingCustomer, "id");
        return mapper.map(repository.save(existingCustomer), CustomerDTO.class);
    }

    public ResponseEntity<?> deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    }

