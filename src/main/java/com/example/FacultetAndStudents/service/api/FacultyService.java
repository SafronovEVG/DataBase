package com.example.FacultetAndStudents.service.api;

import com.example.FacultetAndStudents.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty nameFaculty);

    Faculty findFaculty(int id);

    Faculty editFaculty(Faculty nameFaculty);

    void deleteFaculty(int id);

    Collection<Faculty> getAllFaculty();
}
