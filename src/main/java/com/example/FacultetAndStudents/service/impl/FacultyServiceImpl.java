package com.example.FacultetAndStudents.service.impl;

import com.example.FacultetAndStudents.exception.FacultyNotFoundException;
import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.service.api.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
    public Optional<Faculty> findAllStudentsInFaculty(Integer facultyId) {
        return facultyRepository.findById(facultyId);
    }

    @Override
    public Faculty createFaculty(Faculty nameFaculty) {
        return facultyRepository.save(nameFaculty);
    }

    @Override
    public Faculty findFaculty(Integer id, String facultyName, String facultyColor) {
        if (facultyName != null) {
            return findByName(facultyName);
        }
        if (facultyColor != null) {
            return findByColor(facultyColor);
        }
        if (id != null) {
            return facultyRepository.findById(id).get();
        }
        throw new FacultyNotFoundException();
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
