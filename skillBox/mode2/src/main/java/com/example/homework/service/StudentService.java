package com.example.homework.service;

import com.example.homework.model.Student;

import java.util.List;

public interface StudentService {
    void addStudent(Student student);
    void removeStudent(Long id);
    List<Student> getAllStudents();
    void clearStudents();
}