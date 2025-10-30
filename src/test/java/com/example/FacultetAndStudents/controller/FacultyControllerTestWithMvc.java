package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.Faculty;
import com.example.FacultetAndStudents.repository.FacultyRepository;
import com.example.FacultetAndStudents.service.impl.FacultyServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Faculty facultyTest;
    private Faculty facultyTest2;
    private Faculty facultyTest3;

    @BeforeEach
    void setUp() {
        facultyTest = new Faculty();
        facultyTest2 = new Faculty();
        facultyTest3 = new Faculty();

        facultyTest.setName("TestFaculty1");
        facultyTest2.setName("TestFaculty2");
        facultyTest3.setName("TestFaculty3");
        facultyTest.setColor("TestColorFaculty1");
        facultyTest2.setColor("TestColorFaculty2");
        facultyTest3.setColor("TestColorFaculty3");
        facultyTest.setId(1);
        facultyTest2.setId(2);
        facultyTest3.setId(3);
    }

    @Test
    void createFaculty() throws Exception {
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

    }

    @Test
    void editFacultyTest() throws Exception {
        JSONObject facultyJSON = new JSONObject();
        facultyJSON.put("name", facultyTest.getName())
                .put("color", facultyTest.getColor());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(facultyTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyJSON.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyTest.getName()))
                .andExpect(jsonPath("$.color").value(facultyTest.getColor()));
        verify(facultyRepository, times(1)).save(any());
    }

    @Test
    void getAllFacultyTest() throws Exception {

        when(facultyRepository.findAll()).thenReturn(List.of(facultyTest, facultyTest2, facultyTest3));

        mockMvc.perform(get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(facultyTest.getName()))
                .andExpect(jsonPath("$[1].name").value(facultyTest2.getName()))
                .andExpect(jsonPath("$[2].name").value(facultyTest3.getName()))
                .andExpect(jsonPath("$[0].color").value(facultyTest.getColor()))
                .andExpect(jsonPath("$[1].color").value(facultyTest2.getColor()))
                .andExpect(jsonPath("$[2].color").value(facultyTest3.getColor()));
    }

    @Test
    void deleteFacultyTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + facultyTest.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentsOnFacultyTest() throws Exception {

        when(facultyRepository.findById(any(Integer.class))).thenReturn(Optional.of(facultyTest));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/students/" + facultyTest.getId())
                        .content(facultyTest.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyTest.getName()))
                .andExpect(jsonPath("$.color").value(facultyTest.getColor()));
        verify(facultyRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    void getLongFacultyNameTest() throws Exception {

        when(facultyRepository.findAll()).thenReturn(List.of(facultyTest2));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/long-name-faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(facultyTest2.getName()));
        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    void getIntTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/get-int"))
                .andExpect(status().isOk());
    }
}
