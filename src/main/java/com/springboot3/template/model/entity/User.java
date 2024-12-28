package com.springboot3.template.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer no;

    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String name;

    @Column
    private String role;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdt;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifydt;

}
