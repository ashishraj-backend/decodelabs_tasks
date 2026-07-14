package com.decodelabs.studentmanagement.util;

import com.decodelabs.studentmanagement.dto.StudentRequestDto;
import com.decodelabs.studentmanagement.dto.StudentResponseDto;
import com.decodelabs.studentmanagement.entity.Student;

/**
 * Utility methods for converting between entity and DTO objects.
 */
public final class StudentMapper {

    private StudentMapper() {
        // Utility class
    }

    public static StudentResponseDto toResponseDto(Student student) {
        if (student == null) {
            return null;
        }
        return StudentResponseDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .course(student.getCourse())
                .semester(student.getSemester())
                .cgpa(student.getCgpa())
                .build();
    }

    public static Student toEntity(StudentRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        return Student.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .course(requestDto.getCourse())
                .semester(requestDto.getSemester())
                .cgpa(requestDto.getCgpa())
                .build();
    }
}
