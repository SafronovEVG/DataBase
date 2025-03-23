package com.example.FacultetAndStudents.service.api;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty findByName(String facultyName);

    Faculty findByColor(String facultyColor);

    Collection<Student> findAllStudentsInFaculty(Integer facultyId);

    Faculty createFaculty(Faculty nameFaculty);

    Faculty findFaculty(int id);

    Faculty editFaculty(Faculty nameFaculty);

    void deleteFaculty(int id);

    Collection<Faculty> getAllFaculty();
}
