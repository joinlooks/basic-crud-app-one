package com.example.basic_crud_app_one.service;

import org.springframework.stereotype.Service;

import com.example.basic_crud_app_one.entity.Student;
import com.example.basic_crud_app_one.repository.StudentRepository;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentRequest) {
        System.out.println("Entering Service");
        Student studentResponse = studentRepository.saveStudentToDB(studentRequest);
        System.out.println("Exiting Service");
        return studentResponse;
    }
}
