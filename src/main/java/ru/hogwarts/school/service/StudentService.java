package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService  {

    private final StudentsRepository studentsRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Student addStudent(Student student) {
        logger.debug("Был вызван метод addStudent");
        return studentsRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.debug("Был вызван метод findStudent");
        return studentsRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.debug("Был вызван метод editStudent");
        return studentsRepository.save(student);
    }

    public void removeStudent(Long id) {
        logger.debug("Был вызван метод removeStudent");
        studentsRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.debug("Был вызван метод getAllStudents");
        return studentsRepository.findAll();
    }


    public Collection<Student> findByAge(Integer age) {
        logger.debug("Был вызван метод findByAge");
        return studentsRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(Integer max, Integer min) {
        logger.debug("Был вызван метод findByAgeBetween");
        return studentsRepository.findByAgeBetween(max, min);
    }

    public Faculty findFacultyOfStudent(Long id) {
        logger.debug("Был вызван метод findFacultyOfStudent");
        return studentsRepository.findById(id).map(Student::getFaculty).orElseThrow();
    }

    public Integer getSumStudents() {
        logger.debug("Был вызван метод getSumStudents");
        return studentsRepository.getExpensesByCategory();
    }

    public Double getAvgAge() {
        logger.debug("Был вызван метод getAvgAge");
        return studentsRepository.getAvgAge();
    }

    public List<Student> getLastStudents() {
        logger.debug("Был вызван метод getLastStudents");
        return studentsRepository.getLastStudents();
    }

    public Student getStudentByName(String name) {
        logger.debug("Был вызван метод getStudentByName");
        return studentsRepository.getStudentByName(name);
    }

    public List<Student> findStudentWithChar(String letter) {
        return studentsRepository.findAll().stream()
                .filter(e->e.getName().startsWith(letter.toUpperCase()))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public Double getAvgAgeStudentsForStream() {
        return studentsRepository.findAll().stream()
                .mapToDouble(e -> e.getAge())
                .average()
                .orElseThrow();
    }
}
