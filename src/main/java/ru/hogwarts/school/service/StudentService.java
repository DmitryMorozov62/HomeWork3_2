package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService  {

    private final Map<Long, Student> students = new HashMap<>();
    private long idStudent = 0;

    public Student addStudent(Student student) {
        student.setId(++idStudent);
        students.put(idStudent, student);
        return student;
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student removeStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentAge(Integer age) {
        return students.values().stream().filter(e -> e.getAge()== age).collect(Collectors.toList());
    }
}
