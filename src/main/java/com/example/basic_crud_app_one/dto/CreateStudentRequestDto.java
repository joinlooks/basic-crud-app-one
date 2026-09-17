package com.example.basic_crud_app_one.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateStudentRequestDto {
    @NotBlank(message = "Name cannot be empty/blank/null")
    @Size(min = 2, max = 50, message = "Length of name should be between 2 and 50 characters")
    private String name;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email can't be empty/blank/null")
    private String email;

    @Min(value = 18, message = "Minimum age is 18")
    @NotNull(message = "Age can't be null")
    private Integer age;

    @NotNull(message = "Roll Number cant' be null")
    private Integer rollNo;

    @NotBlank(message = "Subject cannot be empty/blank/null")
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
