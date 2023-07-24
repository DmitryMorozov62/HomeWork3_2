package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Integer age) {
        if (age == null) {
            return ResponseEntity.ok(studentService.getAllStudents());
        }
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/filter")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam Integer min,
                                                                @RequestParam Integer max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @GetMapping("/{id}/faculty")
    public Faculty findFacultyOfStudent(@PathVariable Long id) {
        return studentService.findFacultyOfStudent(id);
    }

    @GetMapping("/sum-students")
    public Integer getSumStudents() {
        return studentService.getSumStudents();
    }

    @GetMapping("/avgAge")
    public Double getAvgAgeStudents() {
        return studentService.getAvgAge();
    }

    @GetMapping("/lastStudents")
    public List<Student> getLastStudents() {
        return studentService.getLastStudents();
    }

    @GetMapping("/name/{name}")
    public Student getStudentByName(@PathVariable ("name") String name) {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/find_student/{letter}")
    public ResponseEntity<List<Student>> findStudentWithChar(@PathVariable String letter){
        return ResponseEntity.ok(studentService.findStudentWithChar(letter));
    }

    @GetMapping("/average_age_for_stream")
    public Double getAvgAgeStudentsForStream(){
        return studentService.getAvgAgeStudentsForStream();
    }

    @GetMapping("/allStudentForPrint")
    public void allStudentForPrint() {
        studentService.allStudentForPrint();
    }

    @GetMapping("/allStudentForPrintSynchronized")
    public void allStudentForPrintSynchronized() {
        studentService.allStudentForPrintSynchronized();
    }
}
