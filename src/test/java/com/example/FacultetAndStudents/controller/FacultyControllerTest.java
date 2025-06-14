package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Faculty;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
