package com.example.FacultetAndStudents.service.api;

import com.example.FacultetAndStudents.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {
    Faculty findByName(String facultyName);

    Faculty findByColor(String facultyColor);

    Optional<Faculty> findAllStudentsInFaculty(Integer facultyId);

    Faculty createFaculty(Faculty nameFaculty);

    Faculty findFaculty(Integer id, String facultyName, String facultyColor);

    Faculty editFaculty(Faculty nameFaculty);

    void deleteFaculty(int id);

    Collection<Faculty> getAllFaculty();
}
