package com.decodelabs.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a student record stored in the database.
 */
@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 100)
    private String course;

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private Double cgpa;
}
