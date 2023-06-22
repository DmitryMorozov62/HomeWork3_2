package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultiesRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private final FacultiesRepository facultiesRepository;

    public FacultyService(FacultiesRepository facultiesRepository) {
        this.facultiesRepository = facultiesRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultiesRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return facultiesRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultiesRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        facultiesRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultiesRepository.findAll();
    }

    public List<Faculty> findByColor(String color) {
        return facultiesRepository.findByColor(color);
    }
}
