package com.example.FacultetAndStudents.service.api;

import com.example.FacultetAndStudents.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(String name, Integer age);

    Student createStudent(Student student);

    Student findByIdStudent(int id);

    Student editStudent(Student student);

    void deleteStudent(int id);

    Collection<Student> getAllStudents();
}
