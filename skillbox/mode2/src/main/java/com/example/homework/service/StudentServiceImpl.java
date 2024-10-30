package com.example.homework.service;

import com.example.homework.model.Student;
import com.example.homework.event.StudentCreatedEvent;
import com.example.homework.event.StudentDeletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentServiceImpl implements StudentService {
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ApplicationEventPublisher eventPublisher;

    public StudentServiceImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void addStudent(Student student) {
        student.setId(idGenerator.getAndIncrement());
        students.add(student);
        eventPublisher.publishEvent(new StudentCreatedEvent(this, student));
    }

    @Override
    public void removeStudent(Long id) {
        students.removeIf(student -> student.getId().equals(id));
        eventPublisher.publishEvent(new StudentDeletedEvent(this, id));
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public void clearStudents() {
        students.clear();
    }
}
