package com.example.FacultetAndStudents.service.impl;

import com.example.FacultetAndStudents.exception.FacultyNotFoundException;
import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.service.api.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public Faculty findByName(String facultyName) {
        log.debug("Get find faculty name");
        return facultyRepository.findByNameIgnoreCase(facultyName);
    }

    @Override
    public Faculty findByColor(String facultyColor) {
        log.debug("Get faculty color");
        return facultyRepository.findByColorIgnoreCase(facultyColor);
    }

    @Override
    public Optional<Faculty> findAllStudentsInFaculty(Integer facultyId) {
        log.debug("Get all students faculty");
        return facultyRepository.findById(facultyId);
    }

    @Override
    public Faculty createFaculty(Faculty nameFaculty) {
        log.debug("Creating faculty");
        return facultyRepository.save(nameFaculty);
    }

    @Override
    public Faculty findFaculty(Integer id, String facultyName, String facultyColor) {
        log.debug("Get faculty");
        if (facultyName != null) {
            return findByName(facultyName);
        }
        if (facultyColor != null) {
            return findByColor(facultyColor);
        }
        if (id != null) {
            return facultyRepository.findById(id).get();
        }
        log.error("Faculty not found");
        throw new FacultyNotFoundException();
    }

    @Override
    public Faculty editFaculty(Faculty nameFaculty) {
        log.debug("faculty edit =" + nameFaculty);
        return facultyRepository.save(nameFaculty);
    }

    @Override
    public void deleteFaculty(int id) {
        log.debug("Faculty deleted="+ id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        log.debug("All faculty find");
        return facultyRepository.findAll();
    }

    public String getLongFacultyName() {
        log.info("Get long name faculty");
        return facultyRepository.findAll()
                .stream().parallel()
                .map(Faculty::getName).max(Comparator.comparing(String::length)).orElse(toString());
    }

    public Integer getInt() {
        log.info("Get int");
        return  Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
    }
}
