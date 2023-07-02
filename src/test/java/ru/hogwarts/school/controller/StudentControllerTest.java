package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentsRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentsRepository studentsRepositoryMock;

    @MockBean
    private AvatarRepository avatarRepositoryMock;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    StudentController studentController;

    @Test
    void addStudent() throws Exception {
        Long id = 1L;
        String name = "Potter";
        int age = 18;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student(id, name , age);

        when(studentsRepositoryMock.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getStudent() throws Exception {
        Long id = 1L;
        String name = "Potter";
        int age = 18;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student(id, name , age);

        when(studentsRepositoryMock.findById(id)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void editStudent() throws Exception {
        Long id = 1L;
        String name = "Potter";
        int age = 18;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student(id, name , age);

        when(studentsRepositoryMock.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void findStudents() throws Exception {
        Student student1 = new Student(1L, "S1", 18);
        Student student2 = new Student(2L, "S2", 22);
        Student student3 = new Student(3L, "S3", 18);

        JSONArray studentsArr = new JSONArray();
        studentsArr.put(student1);
        studentsArr.put(student2);
        studentsArr.put(student3);

        List<Student> list = List.of(student1, student2, student3);
        when(studentsRepositoryMock.findAll()).thenReturn(list);
        when(studentsRepositoryMock.findByAge(anyInt())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .param("age","18")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpectAll();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student")
                        .param("age","18")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("S1"))
                .andExpect(jsonPath("$.[0].age").value(18))
                .andExpect(jsonPath("$.[2].id").value(3L))
                .andExpect(jsonPath("$.[2].name").value("S3"))
                .andExpect(jsonPath("$.[2].age").value(18));
    }

    @Test
    void findByAgeBetween() throws Exception {
        Student student1 = new Student(1L, "S1", 18);
        Student student2 = new Student(2L, "S2", 22);
        Student student3 = new Student(3L, "S3", 18);

        JSONArray studentsArr = new JSONArray();
        studentsArr.put(student1);
        studentsArr.put(student2);
        studentsArr.put(student3);

        List<Student> list = List.of(student1, student2, student3);
        when(studentsRepositoryMock.findByAgeBetween(anyInt(),anyInt())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter")
                        .param("min","18")
                        .param("max","19")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("S1"))
                .andExpect(jsonPath("$.[0].age").value(18))
                .andExpect(jsonPath("$.[2].id").value(3L))
                .andExpect(jsonPath("$.[2].name").value("S3"))
                .andExpect(jsonPath("$.[2].age").value(18));
    }
}