package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoadStudentController() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void contextLoadGetAllStudents() throws Exception {
        Assertions.assertThat(studentController.getAllStudents(1, 100)).isNotNull();
    }

    @Test
    void testCreateStudentTest() {
        Student testStudent = new Student();
        testStudent.setName("UserName");
        testStudent.setAge(10);
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:"
                + port + "/student", testStudent, String.class)).isNotNull();
    }

    @Test
    void createAndDelete() {
        Student studentTest = createStudentTest();
        deleteStudentTest(studentTest.getId());
    }


    private Student createStudentTest() {
        String url = "http://localhost:" + port + "/student";
        Student studentRequest = new Student();
        studentRequest.setName("test");
        studentRequest.setAge(2);
        HttpEntity<Student> reqEntity = new HttpEntity<>(studentRequest);

        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.POST, reqEntity, Student.class);
        assertEquals(HttpStatusCode.valueOf(200),response.getStatusCode());
        assertNotNull(response.getBody());

        return response.getBody();
    }


    private void deleteStudentTest(int id) {
        String url = "http://localhost:" + port + "/student/{id}";

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, id);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

}
