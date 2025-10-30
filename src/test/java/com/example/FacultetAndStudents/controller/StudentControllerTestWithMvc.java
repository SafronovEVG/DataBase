package com.example.FacultetAndStudents.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.FacultetAndStudents.exception.StudentBadRequestException;
import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.repository.StudentRepository;
import com.example.FacultetAndStudents.service.impl.StudentServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTestWithMvc {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentServiceImpl;

    private Student student;
    private Student student2;
    private Student student3;

    @BeforeEach
    void createStudentTest() {
        student = new Student();
        student2 = new Student();
        student3 = new Student();

        student.setName("georgy");
        student2.setName(null);
        student3.setName("Georgy");
        student.setAge(23);
        student2.setAge(null);
        student3.setAge(55);
        student.setId(1);
        student2.setId(null);
        student3.setId(23);
    }

    @Test
    void createStudent() throws Exception {

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", student.getName());
        studentObject.put("age", student.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    void createAndFindStudentTest() throws Exception {

        when(studentRepository.findAll()).thenReturn(List.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void deleteStudentTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + student.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void getStudentByIdTest() throws Exception {

        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId())
                        .content(student.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void editStudentTest() throws Exception {

        JSONObject editStudentObject = new JSONObject();
        editStudentObject.put("name", student3.getName());
        editStudentObject.put("age", student3.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(student3);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(editStudentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student3.getName()))
                .andExpect(jsonPath("$.age").value(student3.getAge()))
                .andExpect(jsonPath("$.id").value(student3.getId()));

    }

    @Test
    void getAllStudentsLetterIsOk() throws Exception {
        Character character = 'g';

        when(studentRepository.findAll()).thenReturn(List.of(student));

        mockMvc.perform(get("/student/find-all/{letter}", character)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].id").value(student.getId()));
        verify(studentServiceImpl, times(1)).getAllStudentsLetter(character);
    }


    @Test
    void getAllStudentLetterShowThrows() {
        assertThrows(StudentBadRequestException.class, () -> studentServiceImpl.getAllStudentsLetter(null));
        assertThrows(StudentBadRequestException.class, () -> studentServiceImpl.getAllStudentsLetter('2'));
    }

    @Test
    void getAVGYearForStreamTest() throws Exception {

        when(studentRepository.findAll()).thenReturn(List.of(student, student2, student3));

        mockMvc.perform(get("/student/avg-year")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}