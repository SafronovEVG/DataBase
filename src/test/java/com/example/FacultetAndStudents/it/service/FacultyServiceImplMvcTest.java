package com.example.FacultetAndStudents.it.service;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.service.impl.FacultyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(MockitoExtension.class)
public class FacultyServiceImplMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    FacultyServiceImpl facultyService;

    private Faculty facultyTest;
    private Faculty facultyTest2;
    private Faculty facultyTest3;
    private Faculty getFacultyTestNull;

    @BeforeEach
    void setUp() {
        facultyTest = new Faculty();
        facultyTest2 = new Faculty();
        facultyTest3 = new Faculty();
        getFacultyTestNull = new Faculty();


        facultyTest.setName("TestFaculty1");
        facultyTest2.setName("TestFaculty2");
        facultyTest3.setName("TestFaculty3");
        getFacultyTestNull.setName(null);
        facultyTest.setColor("TestColorFaculty1");
        facultyTest2.setColor("TestColorFaculty2");
        facultyTest3.setColor("TestColorFaculty3");
        getFacultyTestNull.setColor(null);
        facultyTest.setId(1);
        facultyTest2.setId(2);
        facultyTest3.setId(3);
    }

    @Test
    void createFacultyTest() {
        when(facultyRepository.save(facultyTest)).thenReturn(facultyTest);

        assertEquals(facultyService.createFaculty(facultyTest), facultyTest);

    }

    @Test
    void findByNameTest() {
        when(facultyRepository.findByNameIgnoreCase("Name")).thenReturn(facultyTest2);

        assertEquals(facultyTest2, facultyService.findByName("Name"));

    }

    @Test
    void findByColorTest() {
        when(facultyRepository.findByColorIgnoreCase("color")).thenReturn(facultyTest3);

        assertEquals(facultyTest3,facultyService.findByColor("color"));

    }

    @Test
    void findAllStudentsInFacultyTest() {
        Optional<Faculty> facultyOptional = Optional.ofNullable(facultyTest);

        when(facultyRepository.findById(1)).thenReturn(facultyOptional);

        assertEquals(facultyOptional,facultyService.findAllStudentsInFaculty(1));
    }

    @Test
    void findFacultyTest() {
        when(facultyRepository.findByColorIgnoreCase("color")).thenReturn(facultyTest);
        when(facultyRepository.findByNameIgnoreCase("name")).thenReturn(facultyTest2);
        when(facultyRepository.findById(1)).thenReturn(Optional.ofNullable(facultyTest3));

        assertEquals(facultyTest,facultyService.findFaculty(null,null,"color"));
        assertEquals(facultyTest2,facultyService.findFaculty(null,"name",null));
        assertEquals(facultyTest3,facultyService.findFaculty(1,null,null));
    }

    @Test
    void editFacultyTest() {
        when(facultyRepository.save(facultyTest)).thenReturn(facultyTest);

        assertEquals(facultyTest,facultyService.editFaculty(facultyTest));
    }

    @Test
    void getAllFacultyTest() {
        List<Faculty> faculties = List.of(facultyTest,facultyTest2,facultyTest3);
        when(facultyRepository.findAll()).thenReturn(faculties);

        List<Faculty> allFaculty = facultyService.getAllFaculty();

        assertEquals(faculties.size(), allFaculty.size());
        assertEquals(faculties.get(0).getName(),allFaculty.get(0).getName());

    }

    @Test
    void getLongFacultyName() {
        List<Faculty> faculties = List.of(facultyTest,getFacultyTestNull);
        when(facultyRepository.findAll()).thenReturn((faculties));

        String longFacultyName = facultyService.getLongFacultyName();

        assertEquals(facultyTest.getName(), longFacultyName);
    }
}
