package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.service.impl.StudentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @PostMapping("/create")
    public Student createStudent(@RequestParam String name, @RequestParam Integer age) {
        return studentService.createStudent(name, age);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable Integer id) {
        Student student = studentService.findByIdStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Student> getAllStudents(@PathVariable(required = false) Integer minAge,
                                              @PathVariable(required = false) Integer maxAge) {
        return studentService.getAllStudents(minAge, maxAge);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Integer id) {
        return studentService.getFacultyByStudent(id);
    }

    @GetMapping("/count-students")
    public Integer getCountStudent() {
        return studentService.getCountStudent();
    }

    @GetMapping("/avg-students")
    public Double getAvgYear() {
        return studentService.getAvgYear();
    }

    @GetMapping("/last-student/{number}")
    public List<Student> getLastStudent(@PathVariable Integer number) {
        return studentService.getLastStudent(number);
    }

    @GetMapping("/find-all/{letter}")
    public ResponseEntity<List<Student>> getAllStudentsLetter(@PathVariable Character letter) {
        return ResponseEntity.ok(studentService.getAllStudentsLetter(letter));
    }

    @GetMapping("/avg-year")
    ResponseEntity<Integer> integerResponseEntity() {
        return ResponseEntity.ok(studentService.getAvgYearForStream());
    }

    @GetMapping("/print-parallel")
    public void returnConsole() {
        studentService.returnStudentInConsole();
    }

    @GetMapping("/print-synchronized")
    public void printConsoleSynchronized() {
        studentService.returnSynchronizedStudentInConsole();
    }
}

