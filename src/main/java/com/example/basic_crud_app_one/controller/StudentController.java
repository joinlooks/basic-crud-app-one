package com.example.basic_crud_app_one.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.basic_crud_app_one.entity.Student;
import com.example.basic_crud_app_one.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        System.out.println("Entering Controller");
        Student createdStudent = studentService.createStudent(student);
        System.out.println("Exiting Controller");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/getAll")
    public String readStudent() {
        return "Hahahaha...";
    }

    public void updateStudent() {

    }

    public void deleteStudent() {

    }
}
