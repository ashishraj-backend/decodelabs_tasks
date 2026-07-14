package com.decodelabs.studentmanagement.service.impl;

import com.decodelabs.studentmanagement.dto.PagedResponse;
import com.decodelabs.studentmanagement.dto.StudentRequestDto;
import com.decodelabs.studentmanagement.dto.StudentResponseDto;
import com.decodelabs.studentmanagement.entity.Student;
import com.decodelabs.studentmanagement.exception.ResourceNotFoundException;
import com.decodelabs.studentmanagement.repository.StudentRepository;
import com.decodelabs.studentmanagement.service.StudentService;
import com.decodelabs.studentmanagement.util.StudentMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Default implementation of the StudentService.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public PagedResponse<StudentResponseDto> getStudents(String course,
                                                         String email,
                                                         Integer semester,
                                                         Integer page,
                                                         Integer size,
                                                         Boolean sortByCgpa) {
        int pageIndex = page == null || page < 0 ? 0 : page;
        int pageSize = size == null || size <= 0 ? 10 : size;
        Sort sort = Sort.unsorted();
        if (Boolean.TRUE.equals(sortByCgpa)) {
            sort = Sort.by(Sort.Direction.DESC, "cgpa");
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Specification<Student> specification = buildSpecification(course, email, semester);
        Page<Student> studentPage = studentRepository.findAll(specification, pageable);
        return PagedResponse.<StudentResponseDto>builder()
                .content(studentPage.map(StudentMapper::toResponseDto).toList())
                .page(studentPage.getNumber())
                .size(studentPage.getSize())
                .totalElements(studentPage.getTotalElements())
                .totalPages(studentPage.getTotalPages())
                .last(studentPage.isLast())
                .build();
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        return StudentMapper.toResponseDto(student);
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        Student student = StudentMapper.toEntity(requestDto);
        return StudentMapper.toResponseDto(studentRepository.save(student));
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        existing.setFirstName(requestDto.getFirstName());
        existing.setLastName(requestDto.getLastName());
        existing.setEmail(requestDto.getEmail());
        existing.setPhone(requestDto.getPhone());
        existing.setCourse(requestDto.getCourse());
        existing.setSemester(requestDto.getSemester());
        existing.setCgpa(requestDto.getCgpa());
        return StudentMapper.toResponseDto(studentRepository.save(existing));
    }

    @Override
    public void deleteStudent(Long id) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        studentRepository.delete(existing);
    }

    private Specification<Student> buildSpecification(String course, String email, Integer semester) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();
            if (course != null && !course.isBlank()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("course")), "%" + course.toLowerCase() + "%"));
            }
            if (email != null && !email.isBlank()) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), email.toLowerCase()));
            }
            if (semester != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("semester"), semester));
            }
            return predicates;
        };
    }
}
