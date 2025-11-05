package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.it.AbstractIntegrationTest;
import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;

public class FacultyControllerTest extends AbstractIntegrationTest {
    @AfterEach
    void cleanUp() {
        facultyRepository.deleteAll();
    }

    @Test
    void contextLoadFacultyController() {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createFacultyTest() {
        Faculty faculty = getFacultyTest1();
        HttpEntity<Faculty> reqEntity = new HttpEntity<>(getFacultyTest1());

        ResponseEntity<Faculty> response = testRestTemplate.exchange(LOCALHOST + port + "/faculty",
                HttpMethod.POST, reqEntity, Faculty.class);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(faculty.getName(), response.getBody().getName());
        assertEquals(faculty.getColor(), response.getBody().getColor());
        Faculty facultySave = facultyRepository.findAll().get(0);
        assertEquals(faculty.getName(), facultySave.getName());
        assertEquals(faculty.getColor(), facultySave.getColor());
    }

    @Test
    void shouldGetAllFaculty() {
        Faculty faculty1 = getFacultyTest1();
        Faculty faculty2 = getFacultyTest2();
        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
        int extendSize = facultyRepository.findAll().size();


        ResponseEntity<Collection<Faculty>> responseGet = testRestTemplate.exchange((LOCALHOST +
                port + "/faculty"), GET, null, new ParameterizedTypeReference<Collection<Faculty>>() {
        });

        assertEquals(HttpStatusCode.valueOf(200), responseGet.getStatusCode());
        assertEquals(extendSize, responseGet.getBody().size());
        assertEquals(1, responseGet.getBody().stream().iterator().next().getId());
        assertEquals(faculty1.getName(), responseGet.getBody().stream().iterator().next().getName());
        assertEquals(faculty1.getColor(), responseGet.getBody().stream().iterator().next().getColor());
    }

    @Test
    void shouldDeleteFacultyTest() {
        String url = LOCALHOST + port + "/faculty/{id}";
        Faculty faculty1 = getFacultyTest1();
        Faculty faculty2 = getFacultyTest2();
        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);

        ResponseEntity<Faculty> response = testRestTemplate.exchange(url, DELETE,
                null, Faculty.class, faculty1.getId());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void shouldGetLongNameFaculty() {
        Faculty faculty1 = getFacultyTest1();
        Faculty faculty2 = getFacultyTest2();
        Faculty faculty3 = getFacultyTest3();
        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);
        String extendName = faculty3.getName();

        ResponseEntity<String> response = testRestTemplate.exchange(LOCALHOST +
                port + "/faculty" + "/long-name-faculty", GET, null, String.class);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(extendName, response.getBody());
    }

    @Test
    void shouldGetIntTest() {
        String url = LOCALHOST + port + "/faculty/get-int";

        ResponseEntity<Integer> response = testRestTemplate.exchange(url, GET, null, Integer.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void shouldEditFacultyTest() throws JsonProcessingException {
        facultyRepository.save(getFacultyTest1());
        Faculty editFaculty = new Faculty();
        editFaculty.setName("editName");
        editFaculty.setColor("editColor");
        editFaculty.setId(1);


        ResponseEntity<Faculty> response = testRestTemplate.exchange(LOCALHOST + port + "/faculty",
                PUT, new HttpEntity<>(editFaculty, createHeaders()), Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(editFaculty.getName(), response.getBody().getName());
        assertEquals(editFaculty.getName(), facultyRepository.findAll().get(0).getName());
    }

    private Faculty getFacultyTest1() {
        Faculty faculty = new Faculty();
        faculty.setColor("Red");
        faculty.setName("Faculty_test_name");
        return faculty;
    }

    private Faculty getFacultyTest2() {
        Faculty faculty = new Faculty();
        faculty.setColor("Green");
        faculty.setName("Faculty_test_name_2");
        return faculty;
    }

    private Faculty getFacultyTest3() {
        Faculty faculty = new Faculty();
        faculty.setColor("Black");
        faculty.setName("Faculty_test_name_long");
        return faculty;
    }

    private Faculty facultyTestNull() {
        Faculty faculty = new Faculty();
        faculty.setColor(null);
        faculty.setName(null);
        return faculty;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
//
//    @Test
//    void createAndDeleteFaculty() {
//        Faculty facultyRequest = createFaculty();
//        deleteFaculty(createFaculty().getId());
//    }
//
//
//
//    @Test
//    void shouldGetStudentOnFacultyTest() {
//        String url = "http://localhost:" + port + "/faculty/students/1";
//
//        assertThat(this.testRestTemplate.getForObject(url, Faculty.class)).isNotNull();
//        ResponseEntity<Optional<Faculty>> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<Optional<Faculty>>() {
//                });
//        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
//        assertEquals(facultyController.getStudentsOnFaculty(1).toString(), response.getBody().toString());
//    }
//
//    @Test
//    void shouldGetLongNameFaculty() {
//        String url = "http://localhost:" + port + "/faculty/long-name-faculty";
//        ResponseEntity<String> response = testRestTemplate.exchange(url, GET, null, String.class);
//
//        assertThat(this.testRestTemplate.getForEntity(url, String.class)).isNotNull();
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//        assertEquals(facultyController.getLongNameFaculty().getBody(), response.getBody());
//    }
//
//    @Test
//    void shouldGetIntTest() {
//        String url = "http://localhost:" + port + "/faculty/get-int";
//
//        ResponseEntity<Integer> response = testRestTemplate.exchange(url, GET, null, Integer.class);
//        assertEquals(response.getStatusCode(),HttpStatus.OK);
//    }
