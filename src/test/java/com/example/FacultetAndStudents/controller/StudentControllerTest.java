package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.exception.StudentBadRequestException;
import com.example.FacultetAndStudents.it.AbstractIntegrationTest;
import com.example.FacultetAndStudents.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;

public class StudentControllerTest extends AbstractIntegrationTest {

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
        Student student = getStudentTest1();
        HttpEntity<Student> reqEntity = new HttpEntity<>(student);

        ResponseEntity<Student> response = testRestTemplate.exchange(LOCALHOST + port + "/student",
                HttpMethod.POST, reqEntity, Student.class);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        Student studentSave = studentRepository.findAll().get(0);
        assertEquals(student.getAge(), studentSave.getAge());
        assertEquals(student.getName(), studentSave.getName());
    }

    @Test
    void createAndDelete() {
        Student studentTest = createStudentTest();
        deleteStudentTest(studentTest.getId());
    }

    @Test
    void getCountStudentTest() {
        Student student = getStudentTest1();
        Student student2 = getStudentTest2();
        Student student3 = getStudentTest3();
        studentRepository.save(student);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ResponseEntity<Integer> response = testRestTemplate.exchange(LOCALHOST + port + "/student/count-students",
                GET, null, Integer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Integer countStudent = studentRepository.getCountStudent();
        assertEquals(countStudent, response.getBody());
    }

    @Test
    void getAvgYearStudentTest() {
        Student student = getStudentTest1();
        Student student2 = getStudentTest2();
        Student student3 = getStudentTest3();
        studentRepository.save(student);
        studentRepository.save(student2);
        studentRepository.save(student3);
        double expectedYear = studentRepository.getAvgYear();

        ResponseEntity<Double> response = testRestTemplate.exchange(LOCALHOST + port + "/student/avg-students",
                HttpMethod.GET, null, Double.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedYear,response.getBody());
    }

    @Test
    void getAllStudentLetterShowTEst() {
        Student student = getStudentTest1();
        Student student2 = getStudentTest2();
        Student student3 = getStudentTest3();
        studentRepository.save(student);
        studentRepository.save(student2);
        studentRepository.save(student3);
        String url = LOCALHOST + port + "/student/find-all/d";

        ResponseEntity<List<Student>> response = testRestTemplate.exchange(url, GET, null,
                new ParameterizedTypeReference<List<Student>>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student2.getName(), response.getBody().get(0).getName());
        assertEquals(student2.getAge(), response.getBody().get(0).getAge());
    }

    @Test
    void shouldIntegerResponseEntity() {
        Student student = getStudentTest1();
        Student student2 = getStudentTest2();
        Student student3 = getStudentTest3();
        studentRepository.save(student);
        studentRepository.save(student2);
        studentRepository.save(student3);
        String url = LOCALHOST + port + "/student/avg-year";

        ResponseEntity<Integer> response = testRestTemplate.getForEntity(url, Integer.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(18, testRestTemplate.getForObject(url, Integer.class));
    }

    private Student getStudentTest1() {
        Student student = new Student();
        student.setName("Test_name1");
        student.setAge(22);
        return student;
    }

    private Student getStudentTest2() {
        Student student = new Student();
        student.setName("DTest_name2");
        student.setAge(11);
        return student;
    }

    private Student getStudentTest3() {
        Student student = new Student();
        student.setName("Test_name3");
        student.setAge(22);
        return student;
    }

    private void deleteStudentTest(int id) {
        String url = "http://localhost:" + port + "/student/{id}";

        ResponseEntity<Void> response = testRestTemplate.exchange(url, DELETE, null, Void.class, id);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    private Student createStudentTest() {
        String url = "http://localhost:" + port + "/student";
        Student studentRequest = new Student();
        studentRequest.setName("test");
        studentRequest.setAge(2);
        HttpEntity<Student> reqEntity = new HttpEntity<>(studentRequest);

        ResponseEntity<Student> response = testRestTemplate.exchange(url, POST, reqEntity, Student.class);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

        return response.getBody();
    }


}
