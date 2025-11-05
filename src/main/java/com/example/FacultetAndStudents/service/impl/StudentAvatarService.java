package com.example.FacultetAndStudents.service.impl;

import com.example.FacultetAndStudents.model.Student;
import com.example.FacultetAndStudents.model.StudentAvatar;
import com.example.FacultetAndStudents.repository.StudentAvatarRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Slf4j
@Service
@Transactional
public class StudentAvatarService {

    @Value("${books.cover.dir.path}")
    private String avatarDir;
    private final StudentServiceImpl studentService;
    private final StudentAvatarRepository studentRepository;

    public StudentAvatarService(StudentServiceImpl studentService, StudentAvatarRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public void uploadStudentAvatar(Integer studentId, MultipartFile file) throws IOException {
        log.info("Upload student avatar");
        Student studentEntity = studentService.findByIdStudent(studentId);
        Path filePath = Path.of(avatarDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        StudentAvatar studentAvatar = findStudentAvatar(studentId);
        studentAvatar.setStudent(studentEntity);
        studentAvatar.setFilePath(filePath.toString());
        studentAvatar.setFileSize(file.getSize());
        studentAvatar.setMediaType(file.getContentType());
        studentAvatar.setAvatar(generateImagePreview(filePath));

        studentRepository.save(studentAvatar);
    }

    public StudentAvatar findStudentAvatar(Integer studentAvatarId) {
        log.info("Find student avatar");
        return studentRepository.findStudentById(studentAvatarId).orElse(new StudentAvatar());
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public List<StudentAvatar> getAll(Integer pageNumber, Integer pageSize) {
        log.info("get all avatar");
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return studentRepository.findAll(pageRequest).getContent();
    }

    public void downloadAvatar(Integer id, HttpServletResponse response) throws IOException {
        log.info("download avatar");
        StudentAvatar studentAvatar = findStudentAvatar(id);

        Path path = Path.of(studentAvatar.getFilePath());

        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(studentAvatar.getMediaType());
            response.setContentLength((int) studentAvatar.getFileSize());
            is.transferTo(os);
        }
    }
}
