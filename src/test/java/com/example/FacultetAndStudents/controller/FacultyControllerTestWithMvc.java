package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.service.impl.FacultyServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTestWithMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @Test
    void setFacultyService() {

    }

    Faculty createFaculty() throws Exception {
        Integer id = 23;
        String nameFaculty = "TestName";
        String colorFaculty = "TestColor";

        Faculty facultyTest = new Faculty();
        facultyTest.setId(id);
        facultyTest.setName(nameFaculty);
        facultyTest.setColor(colorFaculty);

        JSONObject facultyJSON = new JSONObject();
        facultyJSON.put("name", facultyTest.getName())
                .put("color", facultyTest.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(facultyTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyTest.getName()))
                .andExpect(jsonPath("$.color").value(facultyTest.getColor()));

        return facultyTest;
    }

    @Test
    void createAndFindFacultyTest() throws Exception {
        Faculty faculty = createFaculty();
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

    }

}
