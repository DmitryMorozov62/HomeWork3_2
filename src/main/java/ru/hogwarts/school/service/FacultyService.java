package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private Map<Long, Faculty> faculties = new HashMap<>();
    private long idFaculty = 0;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++idFaculty);
        faculties.put(idFaculty, faculty);
        return faculty;
    }

    public Faculty getFaculty(Long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty removeFaculty(Long id) {
        return faculties.remove(id);
    }

    public List<Faculty> getFacultiesColor(String color) {
        return faculties.values().stream().filter(e -> e.getColor().equals(color)).collect(Collectors.toList());
    }
}
