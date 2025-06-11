package com.example.FacultetAndStudents.controller;

import com.example.FacultetAndStudents.model.StudentAvatar;
import com.example.FacultetAndStudents.service.impl.StudentAvatarService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final StudentAvatarService studentAvatarService;

    public AvatarController(StudentAvatarService studentAvatarService) {
        this.studentAvatarService = studentAvatarService;
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Integer id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is very big ");
        }
        studentAvatarService.uploadStudentAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Integer id) {
        StudentAvatar studentAvatar = studentAvatarService.findStudentAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(studentAvatar.getMediaType()));
        headers.setContentLength(studentAvatar.getAvatar().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(studentAvatar.getAvatar());
    }

    @GetMapping(value = "/{id}/cover")
    public void downloadAvatar(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        StudentAvatar studentAvatar = studentAvatarService.findStudentAvatar(id);

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

    @GetMapping()
    public List<StudentAvatar> getAllAvatars(@RequestParam("page") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        return studentAvatarService.getAll(pageNumber, pageSize);
    }
}
