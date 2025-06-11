package com.example.FacultetAndStudents.repository;

import com.example.FacultetAndStudents.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Collection<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT COUNT(id) AS Колличество FROM student", nativeQuery = true)
    Integer getCountStudent();

    @Query(value = "select ROUND(AVG(age),1) as \"Средний возраст\" from student", nativeQuery = true)
    Double getAvgYear();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastStudent();
}
