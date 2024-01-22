package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name="pass",nullable = false)
    private String password;

    @Column(name="username", unique = true  ,nullable = false)
    private String username;

    @OneToOne
    Customer customer;

    @OneToOne
    Admin admin;


}
