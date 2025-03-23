package com.example.FacultetAndStudents.repository;

import com.example.FacultetAndStudents.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findByAgeBetween(Integer min, Integer max);
}
