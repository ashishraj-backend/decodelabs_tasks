package com.decodelabs.studentmanagement.config;

import com.decodelabs.studentmanagement.entity.Student;
import com.decodelabs.studentmanagement.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Loads sample student data into H2 database on application startup.
 */
@Component
public class DataLoader {

    private final StudentRepository studentRepository;

    public DataLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void loadInitialData() {
        if (studentRepository.count() == 0) {
            studentRepository.saveAll(List.of(
                    Student.builder()
                            .firstName("Aman")
                            .lastName("Sharma")
                            .email("aman.sharma@example.com")
                            .phone("9876543210")
                            .course("Computer Science")
                            .semester(4)
                            .cgpa(8.7)
                            .build(),
                    Student.builder()
                            .firstName("Riya")
                            .lastName("Verma")
                            .email("riya.verma@example.com")
                            .phone("9123456780")
                            .course("Information Technology")
                            .semester(3)
                            .cgpa(8.9)
                            .build(),
                    Student.builder()
                            .firstName("Rahul")
                            .lastName("Gupta")
                            .email("rahul.gupta@example.com")
                            .phone("9012345678")
                            .course("Electronics")
                            .semester(5)
                            .cgpa(8.3)
                            .build(),
                    Student.builder()
                            .firstName("Simran")
                            .lastName("Kaur")
                            .email("simran.kaur@example.com")
                            .phone("9988776655")
                            .course("Mechanical Engineering")
                            .semester(6)
                            .cgpa(8.1)
                            .build(),
                    Student.builder()
                            .firstName("Karan")
                            .lastName("Singh")
                            .email("karan.singh@example.com")
                            .phone("9162738450")
                            .course("Civil Engineering")
                            .semester(2)
                            .cgpa(7.9)
                            .build()
            ));
        }
    }
}
