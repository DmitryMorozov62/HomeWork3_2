package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultiesRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultiesRepository facultiesRepositoryMock;

    @SpyBean
    private FacultyService facultyServiceSpy;

    @SpyBean
    private StudentService studentServiceSpy;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void addFaculty() throws Exception {
        Long id = 1L;
        String name = "Gryffindor";
        String color = "Red";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty(id, name, color);

        when(facultiesRepositoryMock.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                       .post("/faculty")
                       .content(facultyObject.toString())
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void findFaculties() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Gryffindor", "red");
        Faculty faculty2 = new Faculty(2L, "Gryffindor", "red");

        JSONArray facultyArr = new JSONArray();
        facultyArr.put(faculty1);
        facultyArr.put(faculty2);

        List<Faculty> list = List.of(faculty1,faculty2);

        when(facultiesRepositoryMock.findByColor(anyString())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=Red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty2.getColor()));
    }

    @Test
    void getFaculty() throws Exception {
        Long id = 1L;
        String name = "Gryffindor";
        String color = "Red";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
        Faculty faculty = new Faculty(id, name, color);

        when(facultiesRepositoryMock.findById(id)).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void editFaculty() throws Exception {
        Long id = 1L;
        String name = "Gryffindor";
        String color = "Red";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty(id, name, color);

        when(facultiesRepositoryMock.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        }

    @Test
    void findByColorOrName() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Gryffindor", "red");
        Faculty faculty2 = new Faculty(2L, "Gryffindor", "yellow");

        JSONArray facultyArr = new JSONArray();
        facultyArr.put(faculty1);
        facultyArr.put(faculty2);

        List<Faculty> list = List.of(faculty1,faculty2);

        Mockito.when(facultiesRepositoryMock.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(anyString(),anyString())).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter?colorOrName=red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()));
    }

    @Test
    void getAll() throws Exception {
        Faculty faculty1 = new Faculty(1L, "Gryffindor", "red");
        Faculty faculty2 = new Faculty(2L, "Slytherin", "green");
        Faculty faculty3 = new Faculty(3L, "Ravenclaw", "blue");
        Faculty faculty4 = new Faculty(4L, "Hufflepuff", "yellow");

        JSONArray facultyArr = new JSONArray();
        facultyArr.put(faculty1);
        facultyArr.put(faculty2);
        facultyArr.put(faculty3);
        facultyArr.put(faculty4);

        List<Faculty> list = List.of(faculty1,faculty2,faculty3,faculty4);

        when(facultiesRepositoryMock.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/allFaculties")
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll();
    }
}