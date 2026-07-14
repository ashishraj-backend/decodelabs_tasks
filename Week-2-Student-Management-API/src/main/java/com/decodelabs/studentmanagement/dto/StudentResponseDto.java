package com.decodelabs.studentmanagement.dto;

import lombok.*;

/**
 * DTO returned by the API to represent student data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String course;
    private Integer semester;
    private Double cgpa;
}
