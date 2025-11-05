package com.example.FacultetAndStudents.it;

import com.example.FacultetAndStudents.controller.FacultyController;
import com.example.FacultetAndStudents.controller.StudentController;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.repository.StudentRepository;
import com.example.FacultetAndStudents.service.api.FacultyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class AbstractIntegrationTest {
    @Autowired
    protected StudentController studentController;

    @Autowired
    protected FacultyController facultyController;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected int port;

    @Autowired
    protected FacultyRepository facultyRepository;

    @Autowired
    protected FacultyService facultyService;

    @Autowired
    protected StudentRepository studentRepository;

    protected static final String LOCALHOST = "http://localhost:";

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("hogwarts")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    static {
        postgres.start();
        log.info("start testDB");
    }


}
