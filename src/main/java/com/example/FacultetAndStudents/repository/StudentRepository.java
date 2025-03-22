package com.example.FacultetAndStudents.repository;

import com.example.FacultetAndStudents.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
