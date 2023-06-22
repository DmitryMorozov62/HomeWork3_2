package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultiesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultiesRepository facultiesRepositoryMock;

    @InjectMocks
    private FacultyService out;

    @Test
    void addFaculty() {
        Faculty expected = new Faculty(1L, "Ivan", "red");
        when(facultiesRepositoryMock.save(expected)).thenReturn(expected);
        assertThat(out.addFaculty(new Faculty(1L, "Ivan", "red"))).isEqualTo(expected);
    }

    @Test
    void getFacultyNegative() {
        when(facultiesRepositoryMock.findById(2L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()-> out.getFaculty(2L));
    }

    @Test
    void getFaculty() {
        Faculty expected = new Faculty(1L, "Ivan", "red");
        when(facultiesRepositoryMock.findById(1L)).thenReturn(Optional.of(expected));
        assertThat(out.getFaculty(1L)).isEqualTo(expected);
    }
    @Test
    void editStudent() {
        Faculty expected = new Faculty(1L, "Ivan", "red");
        when(facultiesRepositoryMock.save(expected)).thenReturn(expected);
        assertThat(out.editFaculty(new Faculty(1L, "Ivan", "red"))).isEqualTo(expected);
    }

    @Test
    void findByAge() {
        List<Faculty> expected = List.of(new Faculty(1L, "Ivan", "red")) ;
        when(facultiesRepositoryMock.findByColor("red")).thenReturn(expected);
        assertThat(out.findByColor("red")).isEqualTo(expected);
    }

}