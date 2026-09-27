package com.example.basic_crud_app_one.service;

import com.example.basic_crud_app_one.dto.CreateStudentRequestDto;
import com.example.basic_crud_app_one.dto.CreateStudentResponseDto;
import com.example.basic_crud_app_one.dto.UpdateStudentRequestDto;
import com.example.basic_crud_app_one.dto.UpdateStudentResponseDto;
import com.example.basic_crud_app_one.entity.Student;
import com.example.basic_crud_app_one.exception.DuplicateResourceException;
import com.example.basic_crud_app_one.exception.ResourceNotFoundException;
import com.example.basic_crud_app_one.repository.StudentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDto) {
        Student student = mapToCreateEntity(studentRequestDto);
        if (emailAlreadyExist(student)) {
            throw new DuplicateResourceException(
                    "Student with email: %s, already exist. Please use another email.".formatted(student.getEmail()));
        }
        Student studentResponse = studentRepository.save(student);
        return mapToCreateDto(studentResponse);
    }

    /*
     * With softDelete included in functionality,
     * we need to change the query to fetch a record.
     * Now the query will be like:
     * select * from student where id = 1 and deleted = false
     */
    public CreateStudentResponseDto getStudent(Long id) {
        Student studentResponse = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %d does not exist".formatted(id)));

        return mapToCreateDto(studentResponse);
    }

    /* Only fetches the student who are not softly deleted */
    public List<CreateStudentResponseDto> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        return studentList.stream().map(this::mapToCreateDto).toList();
    }

    /*
     * If a student is not found, then user should not be able to update it and for
     * that a ResourceNotFoundExcecption is thrown
     */
    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentRequest) {
        Student storedStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %d does not exist".formatted(id)));

        storedStudent.setAge(studentRequest.getAge());
        storedStudent.setName(studentRequest.getName());
        storedStudent.setRollNo(studentRequest.getRollNo());
        storedStudent.setSubject(studentRequest.getSubject());
        storedStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(storedStudent);
        return mapToUpdateDto(savedStudent);
    }

    /*
     * This is a method for HARD DELETE. If deleted via this then information of
     * that particular student is lost completely and can't be extracted in any way.
     */
    public void deleteStudent(Long id) {
        Student studentToBeDeleted = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %d does not exist".formatted(id)));

        /* Finally deleting the student if that exist in the database */
        studentRepository.delete(studentToBeDeleted);
    }

    /*
     * Steps:
     * 1. Get Student
     * 2. Set deleted = true
     * 3. Save Student
     */
    public void softDeleteStudent(Long id) {
        Student studentToBeDeleted = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id %d does not exist".formatted(id)));

        studentToBeDeleted.setDeleted(true);
        studentRepository.save(studentToBeDeleted);
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

    private boolean emailAlreadyExist(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }
}
