package com.example.basic_crud_app_one.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.basic_crud_app_one.dto.CreateStudentRequestDto;
import com.example.basic_crud_app_one.dto.CreateStudentResponseDto;
import com.example.basic_crud_app_one.dto.UpdateStudentRequestDto;
import com.example.basic_crud_app_one.dto.UpdateStudentResponseDto;
import com.example.basic_crud_app_one.entity.Student;
import com.example.basic_crud_app_one.repository.StudentRepository;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDto) {
        Student student = mapToCreateEntity(studentRequestDto);
        Student studentResponse = studentRepository.save(student);

        return mapToCreateDto(studentResponse);
    }

    // With softDelete included in functionality,
    // we need to change the query to fetch a record.
    // Now the query will be like:
    // select * from student where id = 1 and deleted = false
    public CreateStudentResponseDto getStudent(Long id) {
        Optional<Student> studentResponse = studentRepository.findByIdAndDeletedIsFalse(id);
        if (studentResponse.isPresent()) {
            return mapToCreateDto(studentResponse.get());
        }
        return null;
    }

    // NEED TO UPDATE
    // select * from student where deleted = false
    public List<CreateStudentResponseDto> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToCreateDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentRequest) {
        Optional<Student> storedStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if (storedStudent.isEmpty()) {
            return null;
        }

        Student studentToEdit = storedStudent.get();

        studentToEdit.setAge(studentRequest.getAge());
        studentToEdit.setName(studentRequest.getName());
        studentToEdit.setRollNo(studentRequest.getRollNo());
        studentToEdit.setSubject(studentRequest.getSubject());
        studentToEdit.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(studentToEdit);
        return mapToUpdateDto(savedStudent);
    }

    public boolean deleteStudent(Long id) {
        Boolean isStudentPresent = studentRepository.existsById(id);
        if (!isStudentPresent) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

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

    private Student mapToCreateEntity(CreateStudentRequestDto studentRequestDto) {
        Student student = new Student();

        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());

        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }

    private CreateStudentResponseDto mapToCreateDto(Student student) {
        CreateStudentResponseDto studentResponseDto = new CreateStudentResponseDto();

        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setRollNo(student.getRollNo());
        studentResponseDto.setSubject(student.getSubject());

        studentResponseDto.setMessage("Student saved successfully");
        studentResponseDto.setCreatedAt(student.getCreatedAt());
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());

        return studentResponseDto;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student) {
        UpdateStudentResponseDto studentResponseDto = new UpdateStudentResponseDto();

        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setRollNo(student.getRollNo());
        studentResponseDto.setSubject(student.getSubject());

        studentResponseDto.setMessage("Student updated successfully");
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());

        return studentResponseDto;
    }
}
