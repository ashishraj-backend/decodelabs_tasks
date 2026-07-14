package com.decodelabs.studentmanagement.controller;

import com.decodelabs.studentmanagement.dto.PagedResponse;
import com.decodelabs.studentmanagement.dto.StudentRequestDto;
import com.decodelabs.studentmanagement.dto.StudentResponseDto;
import com.decodelabs.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing student management endpoints.
 */
@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student API", description = "Manage student records")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a paginated list of students with optional filtering and sorting.")
    public ResponseEntity<PagedResponse<StudentResponseDto>> getStudents(
            @RequestParam(required = false) @Parameter(description = "Filter by course name") String course,
            @RequestParam(required = false) @Parameter(description = "Filter by exact email") String email,
            @RequestParam(required = false) @Parameter(description = "Filter by semester") Integer semester,
            @RequestParam(required = false, defaultValue = "0") @Parameter(description = "Page number, zero based") Integer page,
            @RequestParam(required = false, defaultValue = "10") @Parameter(description = "Page size") Integer size,
            @RequestParam(required = false, defaultValue = "false") @Parameter(description = "Sort by CGPA descending") Boolean sortByCgpa) {
        return ResponseEntity.ok(studentService.getStudents(course, email, semester, page, size, sortByCgpa));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a single student by their unique identifier.")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    @Operation(summary = "Create a student", description = "Create a new student record.")
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
        StudentResponseDto responseDto = studentService.createStudent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update an existing student record by ID.")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id,
                                                            @Valid @RequestBody StudentRequestDto requestDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Delete a student record by ID.")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
