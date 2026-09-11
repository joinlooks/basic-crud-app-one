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
        // By default if someone is creating a record, it is not deleted. So it is
        // better practice to mark that as NOT deleted
        studentRequest.setDeleted(false);

        Student studentResponse = studentRepository.save(studentRequest);
        return studentResponse;
    }

    // With softDelete included in functionality,
    // we need to change the query to fetch a record.
    // Now the query will be like:
    // select * from student where id = 1 and deleted = false
    public Student getStudent(Long id) {
        Optional<Student> studentResponse = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResponse.isPresent()) {
            return studentResponse.get();
        }
        return null;
    }

    // NEED TO UPDATE
    // select * from student where deleted = false
    public List<Student> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        return studentList;
    }

    public Student updateStudent(Long id, Student studentRequest) {
        Optional<Student> storedStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if (storedStudent.isEmpty()) {
            return null;
        }

        Student studentToEdit = storedStudent.get();
        studentToEdit.setAge(studentRequest.getAge());
        studentToEdit.setEmail(studentRequest.getEmail());
        studentToEdit.setName(studentRequest.getName());
        studentToEdit.setRollNo(studentRequest.getRollNo());
        studentToEdit.setSubject(studentRequest.getSubject());
        studentToEdit.setDeleted(false);

        return studentRepository.save(studentToEdit);
    }

    public boolean deleteStudent(Long id) {
        Boolean isStudentPresent = studentRepository.existsById(id);
        if (!isStudentPresent) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    // TODO
    public boolean softDeleteStudent(Long id) {
        // Steps:
        // 1. Get Student
        // 2. Set deleted = true
        // 3. Save Student
        Optional<Student> studentResponse = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResponse.isEmpty()) {
            return false;
        }
        Student studentToDelete = studentResponse.get();
        studentToDelete.setDeleted(true);
        studentRepository.save(studentToDelete);
        return true;
    }
}
