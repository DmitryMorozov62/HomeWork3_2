package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentsRepository studentsRepository;

    @InjectMocks
    private StudentService out;

    @Test
    void addStudent() {
        Student expected = new Student(1L, "Ivan", 18);
        when(studentsRepository.save(expected)).thenReturn(expected);
        assertThat(out.addStudent(new Student(1L, "Ivan", 18))).isEqualTo(expected);
    }

    @Test
    void getStudentNegative() {
        when(studentsRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()-> out.getStudent(2L));
    }

    @Test
    void getStudent() {
        Student expected = new Student(1L, "Ivan", 18);
        when(studentsRepository.findById(1L)).thenReturn(Optional.of(expected));
        assertThat(out.getStudent(1L)).isEqualTo(expected);
    }
    @Test
    void editStudent() {
        Student expected = new Student(1L, "Ivan", 18);
        when(studentsRepository.save(expected)).thenReturn(expected);
        assertThat(out.editStudent(new Student(1L, "Ivan", 18))).isEqualTo(expected);
    }

    @Test
    void findByAge() {
        List<Student> expected = List.of(new Student(1L, "Ivan", 18)) ;
        when(studentsRepository.findByAge(18)).thenReturn(expected);
        assertThat(out.findByAge(18)).isEqualTo(expected);
    }
}