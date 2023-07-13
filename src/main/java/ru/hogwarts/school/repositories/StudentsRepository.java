package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
@Repository
public interface StudentsRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int max, int min);

    List<Student> findAllByFaculty_id(Long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student",nativeQuery = true)
    Integer getExpensesByCategory();

    @Query(value = "SELECT avg(age) from student",nativeQuery = true)
    Double getAvgAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 2",nativeQuery = true)
    List<Student> getLastStudents();

    Student getStudentByName(String name);
}
