package com.example.basic_crud_app_one.repository;

import com.example.basic_crud_app_one.entity.Student;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    // Always follow this Condition: findBy + field + condition
    List<Student> findByDeletedIsFalse();

}
