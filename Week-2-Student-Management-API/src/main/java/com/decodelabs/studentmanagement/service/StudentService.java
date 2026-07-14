package com.decodelabs.studentmanagement.service;

import com.decodelabs.studentmanagement.dto.PagedResponse;
import com.decodelabs.studentmanagement.dto.StudentRequestDto;
import com.decodelabs.studentmanagement.dto.StudentResponseDto;

/**
 * Service contract for student operations.
 */
public interface StudentService {

    PagedResponse<StudentResponseDto> getStudents(String course, String email, Integer semester, Integer page, Integer size, Boolean sortByCgpa);

    StudentResponseDto getStudentById(Long id);

    StudentResponseDto createStudent(StudentRequestDto requestDto);

    StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto);

    void deleteStudent(Long id);
}
