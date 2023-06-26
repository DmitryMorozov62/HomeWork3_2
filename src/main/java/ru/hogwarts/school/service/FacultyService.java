package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultiesRepository;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private final FacultiesRepository facultiesRepository;

    private final StudentsRepository studentsRepository;

    public FacultyService(FacultiesRepository facultiesRepository, StudentsRepository studentsRepository) {
        this.facultiesRepository = facultiesRepository;
        this.studentsRepository = studentsRepository;
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

    public List<Faculty> findByColorOrName(String colorOrName) {
        return facultiesRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName, colorOrName);
    }

    public List<Student> findStudentsOfFaculty(Long id) {
        return studentsRepository.findAllByFaculty_id(id);
    }
}
