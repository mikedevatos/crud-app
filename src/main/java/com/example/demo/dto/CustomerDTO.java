package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    // Getters and setters...
}