package com.example.FacultetAndStudents.repository;

import com.example.FacultetAndStudents.model.StudentAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAvatarRepository extends JpaRepository<StudentAvatar, Integer> {

    Optional<StudentAvatar> findStudentById(Integer id);

    List<StudentAvatar> findAll();
}
