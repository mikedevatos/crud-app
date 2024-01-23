package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    // Getters and setters...
}