package com.example.basic_crud_app_one.repository;

import com.example.basic_crud_app_one.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
