package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Faculty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.GET;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoadFacultyController() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void contextLoadGetAllFaculty() {
        Assertions.assertThat(facultyController.getAllFaculty()).isNotNull();
    }

    @Test
    void createAndDeleteFaculty() {
        Faculty facultyRequest = createFaculty();
        deleteFaculty(createFaculty().getId());
    }

    @Test
    void shouldGetAllFaculty() {
        String url = "http://localhost:" + port + "/faculty";

        assertThat(this.testRestTemplate.getForObject(url, Collection.class)).isNotNull();
    }

    @Test
    void shouldGetStudentOnFacultyTest() {
        String url = "http://localhost:" + port + "/faculty/students/1";

        assertThat(this.testRestTemplate.getForObject(url, Faculty.class)).isNotNull();
        ResponseEntity<Optional<Faculty>> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Optional<Faculty>>() {
                });
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(facultyController.getStudentsOnFaculty(1).toString(), response.getBody().toString());
    }

    @Test
    void shouldGetLongNameFaculty() {
        String url = "http://localhost:" + port + "/faculty/long-name-faculty";
        ResponseEntity<String> response = testRestTemplate.exchange(url, GET, null, String.class);

        assertThat(this.testRestTemplate.getForEntity(url, String.class)).isNotNull();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(facultyController.getLongNameFaculty().getBody(), response.getBody());
    }

    @Test
    void shouldGetIntTest() {
        String url = "http://localhost:" + port + "/faculty/get-int";

        ResponseEntity<Integer> response = testRestTemplate.exchange(url, GET, null, Integer.class);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    private Faculty createFaculty() {
        String url = "http://localhost:" + port + "/faculty";
        Faculty facultyRequest = new Faculty();
        facultyRequest.setName("TestName");
        facultyRequest.setColor("TestColor");
        HttpEntity<Faculty> reqEntity = new HttpEntity<>(facultyRequest);

        ResponseEntity<Faculty> response = testRestTemplate.exchange(url, HttpMethod.POST, reqEntity, Faculty.class);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());

        return response.getBody();
    }

    private void deleteFaculty(int id) {
        String url = "http://localhost:" + port + "/faculty/{id}";
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, id);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

}
