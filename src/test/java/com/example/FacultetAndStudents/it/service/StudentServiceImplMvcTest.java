package com.example.FacultetAndStudents.it.service;

import com.example.FacultetAndStudents.exception.StudentBadRequestException;
import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.repository.StudentRepository;
import com.example.FacultetAndStudents.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private Student student2;
    private Student student3;

    @BeforeEach
    void createStudentTest() {
        student = new Student();
        student2 = new Student();
        student3 = new Student();

        student.setName("georgy");
        student2.setName("null");
        student3.setName("Georgy");
        student.setAge(23);
        student2.setAge(1);
        student3.setAge(55);
        student.setId(1);
        student2.setId(22);
        student3.setId(23);
    }

    @Test
    void findStudentsByAgeBetweenTest() {
        List<Student> students = List.of(student, student3);

        when(studentRepository.findByAgeBetween(1, 2)).thenReturn(students);

        assertEquals(students, studentService.findStudentsByAgeBetween(1, 2));
    }

    @Test
    void getFacultyByStudentTest() {
        when(studentRepository.findById(1)).thenReturn(Optional.ofNullable(student));

        assertEquals(HttpStatus.valueOf(200), studentService.getFacultyByStudent(1).getStatusCode());
    }

    @Test
    void findByIdStudentTest() {
        when(studentRepository.findById(2)).thenReturn(Optional.ofNullable(student3));

        assertEquals(student3, studentService.findByIdStudent(2));
    }

    @Test
    void getAllStudentsLetter() {
        List<Student> students = List.of(student3, student);

        when(studentRepository.findAll()).thenReturn(students);

        assertEquals(students, studentService.getAllStudentsLetter('g'));
    }

    @Test
    void shouldExceptionGetAllStudentsLetter() {
        assertThrows(StudentBadRequestException.class, () -> studentService.getAllStudentsLetter(null));
    }

    @Test
    void getAvgYearForStreamTest() {
        List<Student> students = List.of(student3);

        when(studentRepository.findAll()).thenReturn((students));

        assertEquals(student3.getAge(), studentService.getAvgYearForStream());
    }
}
