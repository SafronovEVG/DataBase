package com.example.FacultetAndStudents.service.impl;

import com.example.FacultetAndStudents.exception.StudentBadRequestException;
import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.repository.StudentRepository;
import com.example.FacultetAndStudents.service.api.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Collection<Student> findStudentsByAgeBetween(Integer min, Integer max) {
        log.info("get student by age between");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Student createStudent(String name, Integer age) {
        log.info("create student");
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        return studentRepository.save(student);
    }

    @Override
    public ResponseEntity<Faculty> getFacultyByStudent(Integer studentId) {
        log.info("get faculty student");
        return ResponseEntity.ok(studentRepository.findById(studentId).get().getFaculty());
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findByIdStudent(int id) {
        log.info("get student by id");
        return studentRepository.findById(id).get();
    }

    @Override
    public Student editStudent(Student student) {
        log.info("edit student");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int id) {
        log.info("delete student");
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents(Integer minAge, Integer maxAge) {
        log.info("get student by age");
        if (minAge != null && maxAge != null) {
            return findStudentsByAgeBetween(minAge, maxAge);
        }
        return studentRepository.findAll();
    }

    @Override
    public Integer getCountStudent() {
        log.info("get count student");
        return studentRepository.getCountStudent();
    }

    @Override
    public Double getAvgYear() {
        log.info("get middle age");
        return studentRepository.getAvgYear();
    }

    @Override
    public List<Student> getLastStudent(Integer number) {
        log.info("get last student");
        return studentRepository.getLastStudent(number);
    }

    @Override
    public List<Student> getAllStudentsLetter(Character character) {
        log.info("Get all student letter ");
        if (character == null || character.describeConstable().isEmpty() || Character.isDigit(character)) {
            log.info("Get all students throws");
            throw new StudentBadRequestException();
        }
        return studentRepository.findAll()
                .stream().parallel()
                .filter(student -> student.getName() != null)
                .filter(s -> s.getName().substring(0, 1).equalsIgnoreCase(String.valueOf(character)))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAvgYearForStream() {
        log.info("get avg year all students");
        return (int) studentRepository.findAll()
                .stream().parallel()
                .filter(student -> student.getAge() != null)
                .mapToInt(Student::getAge)
                .summaryStatistics()
                .getAverage();
    }
}
