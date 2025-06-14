package com.example.FacultetAndStudents.service.impl;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.repository.StudentRepository;
import com.example.FacultetAndStudents.service.api.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Collection<Student> findStudentsByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Student createStudent(String name, Integer age) {
        Student student = new Student();
        student.setAge(age);
        student.setName(name);
        return studentRepository.save(student);
    }

    @Override
    public ResponseEntity<Faculty> getFacultyByStudent(Integer studentId) {
        return ResponseEntity.ok(studentRepository.findById(studentId).get().getFaculty());
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findByIdStudent(int id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents(Integer minAge, Integer maxAge) {
        if (minAge != null && maxAge != null) {
            return findStudentsByAgeBetween(minAge, maxAge);
        }
        return studentRepository.findAll();
    }

    public Integer getCountStudent() {
        return studentRepository.getCountStudent();
    }

    public Double getAvgYear() {
        return studentRepository.getAvgYear();
    }

    public List<Student> getLastStudent(Integer number) {
        return studentRepository.getLastStudent(number);
    }

}
