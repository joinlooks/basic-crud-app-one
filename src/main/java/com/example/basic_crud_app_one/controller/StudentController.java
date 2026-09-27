package com.example.basic_crud_app_one.controller;

import com.example.basic_crud_app_one.dto.CreateStudentRequestDto;
import com.example.basic_crud_app_one.dto.CreateStudentResponseDto;
import com.example.basic_crud_app_one.dto.UpdateStudentRequestDto;
import com.example.basic_crud_app_one.dto.UpdateStudentResponseDto;
import com.example.basic_crud_app_one.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
     * To create a student, requests will come on this mapping.
     * Expects a request VALID request body and if entry is created successfully in
     * the database, returns a successfull 201 response
     */
    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(
            @Valid @RequestBody CreateStudentRequestDto studentRequestDto) {
        CreateStudentResponseDto createdStudent = studentService.createStudent(studentRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    /*
     * To get a single student record from database.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id) {
        CreateStudentResponseDto fetchedStudent = studentService.getStudent(id);
        return ResponseEntity.ok(fetchedStudent);
    }

    /*
     * To get all the existing students which are not soft deleted.
     * IMPORTANT: Even if the list is empty, we should return it.
     */
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudents() {
        List<CreateStudentResponseDto> studentList = studentService.getAllStudents();
        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(
            @PathVariable Long id, @RequestBody UpdateStudentRequestDto studentRequest) {

        UpdateStudentResponseDto fetchStudent = studentService.updateStudent(id, studentRequest);
        return ResponseEntity.ok(fetchStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/softDelete/{id}")
    public ResponseEntity<String> softDeleteStudent(@PathVariable Long id) {
        studentService.softDeleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
