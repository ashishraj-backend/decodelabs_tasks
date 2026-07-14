package com.decodelabs.studentmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO used for student create and update requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDto {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s'-]+$", message = "First name contains invalid characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s'-]+$", message = "Last name contains invalid characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain 10 digits")
    private String phone;

    @NotBlank(message = "Course is required")
    @Size(max = 100, message = "Course name must not exceed 100 characters")
    private String course;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 12, message = "Semester must not exceed 12")
    private Integer semester;

    @NotNull(message = "CGPA is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "CGPA must be at least 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "CGPA must not exceed 10.0")
    private Double cgpa;
}
