package com.example.basic_crud_app_one.service;

import java.util.List;
import java.util.Optional;

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
        Student studentResponse = studentRepository.save(studentRequest);
        return studentResponse;
    }

    public Student getStudent(Long id) {
        Optional<Student> studentResponse = studentRepository.findById(id);
        if (studentResponse.isPresent()) {
            return studentResponse.get();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    public Student updateStudent(Long id, Student studentRequest) {
        Optional<Student> storedStudent = studentRepository.findById(id);
        if (storedStudent.isEmpty()) {
            return null;
        }

        Student studentToEdit = storedStudent.get();
        studentToEdit.setAge(studentRequest.getAge());
        studentToEdit.setEmail(studentRequest.getEmail());
        studentToEdit.setName(studentRequest.getName());
        studentToEdit.setRollNo(studentRequest.getRollNo());
        studentToEdit.setSubject(studentRequest.getSubject());

        return studentRepository.save(studentToEdit);
    }

    public Boolean deleteStudent(Long id) {
        Boolean isStudentPresent = studentRepository.existsById(id);
        if (!isStudentPresent) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
