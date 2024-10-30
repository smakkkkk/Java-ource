package com.example.homework.command;

import com.example.homework.model.Student;
import com.example.homework.service.StudentService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class StudentCommands {
    private final StudentService studentService;

    public StudentCommands(StudentService studentService) {
        this.studentService = studentService;
    }

    @ShellMethod("Add a new student")
    public void addStudent(@ShellOption String firstName, @ShellOption String lastName, @ShellOption int age) {
        Student student = new Student(null, firstName, lastName, age);
        studentService.addStudent(student);
    }

    @ShellMethod("Remove a student by ID")
    public void removeStudent(@ShellOption Long id) {
        studentService.removeStudent(id);
    }

    @ShellMethod("List all students")
    public void listStudents() {
        List<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }

    @ShellMethod("Clear all students")
    public void clearStudents() {
        studentService.clearStudents();
    }
}