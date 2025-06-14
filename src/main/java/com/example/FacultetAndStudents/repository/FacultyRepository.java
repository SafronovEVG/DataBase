package com.example.FacultetAndStudents.repository;

import com.example.FacultetAndStudents.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    Faculty findByNameIgnoreCase(String facultyName);

    Faculty findByColorIgnoreCase(String facultyColor);
}
