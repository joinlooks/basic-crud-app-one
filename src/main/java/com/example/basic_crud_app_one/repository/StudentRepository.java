package com.example.basic_crud_app_one.repository;

import com.example.basic_crud_app_one.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
    public Student saveStudentToDB(Student studentRequest) {
        System.out.println("Entering Repository");
        System.out.println("Exiting Repository");

        Student toReturn = new Student();
        toReturn.setName("Lakshya");
        toReturn.setAge(24);
        toReturn.setEmail("lakshya@google.com");

        return toReturn;
    }
}
