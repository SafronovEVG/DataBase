package com.example.FacultetAndStudents.service.api;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface StudentService {
    Collection<Student> findStudentsByAgeBetween(Integer min, Integer max);

    Student createStudent(String name, Integer age);

    ResponseEntity<Faculty> getFacultyByStudent(Integer studentId);

    Student createStudent(Student student);

    Student findByIdStudent(int id);

    Student editStudent(Student student);

    void deleteStudent(int id);

    Collection<Student> getAllStudents(Integer minAge, Integer maxAge);
}
