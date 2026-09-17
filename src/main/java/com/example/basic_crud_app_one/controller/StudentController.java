package com.example.basic_crud_app_one.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import com.example.basic_crud_app_one.dto.CreateStudentRequestDto;
import com.example.basic_crud_app_one.dto.CreateStudentResponseDto;
import com.example.basic_crud_app_one.dto.UpdateStudentRequestDto;
import com.example.basic_crud_app_one.dto.UpdateStudentResponseDto;
import com.example.basic_crud_app_one.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateStudentResponseDto> createStudent(
            @Valid @RequestBody CreateStudentRequestDto studentRequestDto) {
        CreateStudentResponseDto createdStudent = studentService.createStudent(studentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@RequestParam Long id) {
        CreateStudentResponseDto fetchedStudent = studentService.getStudent(id);

        if (fetchedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fetchedStudent);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudents() {
        List<CreateStudentResponseDto> studentList = studentService.getAllStudents();

        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentList);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id,
            @RequestBody UpdateStudentRequestDto studentRequest) {

        UpdateStudentResponseDto fetchStudent = studentService.updateStudent(id, studentRequest);
        return ResponseEntity.ok(fetchStudent);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {
        Boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }

    @PatchMapping("/softDelete")
    public ResponseEntity<String> softDeleteStudent(@RequestParam Long id) {
        Boolean isDeleted = studentService.softDeleteStudent(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }
}
