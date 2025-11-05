package com.example.FacultetAndStudents.it.service;

import com.example.FacultetAndStudents.it.AbstractIntegrationTest;
import com.example.FacultetAndStudents.model.Faculty;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class FacultyServiceImplIntegrationTest extends AbstractIntegrationTest {
    @AfterEach
    void cleanUp() {
        facultyRepository.deleteAll();
    }
    @Test
    void test() {
        facultyRepository.save(getFaculty1());

        Collection<Faculty> actualFaculties = facultyService.getAllFaculty();

        Assert.assertEquals(1, actualFaculties.size());
    }

    @Test
    void getLongFacultyName() {
        facultyRepository.saveAll(getFaculties());

       String actualNameFaculty = facultyService.getLongFacultyName();

       Assert.assertEquals(getFaculty2().getName(),actualNameFaculty);
    }



    private Faculty getFaculty1() {
        Faculty facultyTest = new Faculty();
        facultyTest.setName("TEST_NAME");
        facultyTest.setColor("TEST_COLOR");
        return facultyTest;
    }

    private Faculty getFaculty2() {
        Faculty facultyTest = new Faculty();
        facultyTest.setName("TEST_NAME_LONG");
        facultyTest.setColor("TEST_COLOR");
        return facultyTest;
    }

    private List<Faculty> getFaculties() {
        return List.of(getFaculty1(), getFaculty2());
    }
}
