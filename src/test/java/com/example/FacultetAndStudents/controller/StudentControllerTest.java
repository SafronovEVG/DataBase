package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;

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

    @Test
    void getCountStudentTest() {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:"
                + port + "/count-students", String.class)).isNotNull();
    }

    @Test
    void getAvgYearStudentTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:" +
                port + "/student/avg-students", Double.class)).isNotNull();
        assertEquals(studentController.getAvgYear(), restTemplate.getForObject(("http://localhost:" +
                port + "/student/avg-students"), Double.class));
    }

    @Test
    void getAllStudentLetterShowTEst() {
        String url = "http://localhost:" + port + "/student/find-all/g";
        Character c = 'g';
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void shouldIntegerResponseEntity() {
        String url = "http://localhost:" + port + "/student/avg-year";

        ResponseEntity<Integer> response = restTemplate.getForEntity(url, Integer.class);


        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(studentController.integerResponseEntity().getBody(), restTemplate.getForObject(url, Integer.class));
    }

    private void deleteStudentTest(int id) {
        String url = "http://localhost:" + port + "/student/{id}";

        ResponseEntity<Void> response = restTemplate.exchange(url, DELETE, null, Void.class, id);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    private Student createStudentTest() {
        String url = "http://localhost:" + port + "/student";
        Student studentRequest = new Student();
        studentRequest.setName("test");
        studentRequest.setAge(2);
        HttpEntity<Student> reqEntity = new HttpEntity<>(studentRequest);

        ResponseEntity<Student> response = restTemplate.exchange(url, POST, reqEntity, Student.class);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

        return response.getBody();
    }


}
