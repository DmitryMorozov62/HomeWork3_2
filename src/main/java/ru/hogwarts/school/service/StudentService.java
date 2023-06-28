package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentsRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Transactional
public class StudentService  {

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final StudentsRepository studentsRepository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentsRepository studentsRepository,AvatarRepository avatarRepository) {
        this.studentsRepository = studentsRepository;
        this.avatarRepository = avatarRepository;
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

    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
