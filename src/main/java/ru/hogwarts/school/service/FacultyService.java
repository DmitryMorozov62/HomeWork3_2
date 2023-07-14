package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultiesRepository facultiesRepository, StudentsRepository studentsRepository) {
        this.facultiesRepository = facultiesRepository;
        this.studentsRepository = studentsRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.debug("Был вызван метод addFaculty");
        return facultiesRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        logger.debug("Был вызван метод getFaculty");
        return facultiesRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Был вызван метод editFaculty");
        return facultiesRepository.save(faculty);
    }

    public void removeFaculty(Long id) {
        logger.debug("Был вызван метод removeFaculty");
        facultiesRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("Был вызван метод getAllFaculties");
        return facultiesRepository.findAll();
    }

    public List<Faculty> findByColor(String color) {
        logger.debug("Был вызван метод findByColor");
        return facultiesRepository.findByColor(color);
    }

    public List<Faculty> findByColorOrName(String colorOrName) {
        logger.debug("Был вызван метод findByColorOrName");
        return facultiesRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName, colorOrName);
    }

    public List<Student> findStudentsOfFaculty(Long id) {
        logger.debug("Был вызван метод findStudentsOfFaculty");
        return studentsRepository.findAllByFaculty_id(id);
    }
}
