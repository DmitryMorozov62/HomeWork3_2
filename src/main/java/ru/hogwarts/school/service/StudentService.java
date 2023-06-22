package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.Collection;

@Service
public class StudentService  {

    private final StudentsRepository studentsRepository;

    public StudentService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public Student addStudent(Student student) {
        return studentsRepository.save(student);
    }

    public Student getStudent(Long id) {
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
}
