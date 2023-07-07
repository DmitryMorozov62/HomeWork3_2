package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService  {

    private final StudentsRepository studentsRepository;

    public StudentService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Student addStudent(Student student) {
        return studentsRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentsRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentsRepository.save(student);
    }

    public void removeStudent(Long id) {
        studentsRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentsRepository.findAll();
    }


    public Collection<Student> findByAge(Integer age) {
        return studentsRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(Integer max, Integer min) {
        return studentsRepository.findByAgeBetween(max, min);
    }

    public Faculty findFacultyOfStudent(Long id) {
        return studentsRepository.findById(id).map(Student::getFaculty).orElseThrow();
    }

    public Integer getSumStudents() {
        return studentsRepository.getExpensesByCategory();
    }

    public Double getAvgAge() {
        return studentsRepository.getAvgAge();
    }

    public List<Student> getLastStudents() {
        return studentsRepository.getLastStudents();
    }
}
