package com.semicolon.africa.Estore.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@Setter
@Getter
@Entity
@Table( name ="\"seller\"")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String username;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false,unique = true)
    private String email;
    @Column(nullable=false)
    private String phone;
    @OneToOne
    private Address address;
}
