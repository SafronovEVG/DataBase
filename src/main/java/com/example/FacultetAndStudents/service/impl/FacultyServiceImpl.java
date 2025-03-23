package com.example.FacultetAndStudents.service.impl;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.service.api.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public Faculty findByName(String facultyName) {
        return facultyRepository.findByNameIgnoreCase(facultyName);
    }

    @Override
    public Faculty findByColor(String facultyColor) {
        return facultyRepository.findByColorIgnoreCase(facultyColor);
    }

    @Override
    public Collection<Student> findAllStudentsInFaculty(Integer facultyId) {
        return facultyRepository.findById(facultyId).get().getStudents();
    }

    @Override
    public Faculty createFaculty(Faculty nameFaculty) {
        return facultyRepository.save(nameFaculty);
    }

    @Override
    public Faculty findFaculty(int id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty editFaculty(Faculty nameFaculty) {
        return facultyRepository.save(nameFaculty);
    }

    @Override
    public void deleteFaculty(int id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }
}
