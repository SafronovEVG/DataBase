package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.repository.StudentRepository;
import com.example.FacultetAndStudents.service.impl.StudentServiceImpl;
import org.json.JSONObject;
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
import static org.mockito.Mockito.when;
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

    Student createStudent() throws Exception {
        Integer id = 1;
        Integer age = 1;
        String name = "Vasya";

        Student studentTest = new Student();
        studentTest.setId(id);
        studentTest.setAge(age);
        studentTest.setName(name);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        when(studentRepository.save(any(Student.class))).thenReturn(studentTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        return studentTest;
    }

    @Test
    void createAndFindStudentTest() throws Exception {

        Student student = createStudent();

        when(studentRepository.findAll()).thenReturn(List.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }

    @Test
    void deleteStudentTest() throws Exception {

        Student student = createStudent();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + student.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void getFacultyTest() throws Exception {

        Student student = createStudent();

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
        Student editStudent = new Student();
        editStudent.setId(1);
        editStudent.setName("Grigory");
        editStudent.setAge(42);

        JSONObject editStudentObject = new JSONObject();
        editStudentObject.put("name", editStudent.getName());
        editStudentObject.put("age", editStudent.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(editStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(editStudentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(editStudent.getName()))
                .andExpect(jsonPath("$.age").value(editStudent.getAge()))
                .andExpect(jsonPath("$.id").value(editStudent.getId()));

    }

}
