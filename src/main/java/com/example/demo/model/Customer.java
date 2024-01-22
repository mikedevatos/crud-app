package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id", nullable = false)
    private Long customer_id;

    @Column(name="firstName", unique = true  ,nullable = false)
    private String firstName;

    @Column(name="firstName", unique = true  ,nullable = false)
    private String lastName;


}
