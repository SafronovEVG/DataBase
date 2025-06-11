package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.service.impl.FacultyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable(required = false) Integer id,
                                                  @PathVariable(required = false) String facultyName,
                                                  @PathVariable(required = false) String facultyColor) {

        return ResponseEntity.ok(facultyService.findFaculty(id, facultyName, facultyColor));
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Integer id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    @GetMapping("/students/{id}")
    public Optional<Faculty> getStudentsOnFaculty(@PathVariable Integer id) {
        return facultyService.findAllStudentsInFaculty(id);
    }
}
