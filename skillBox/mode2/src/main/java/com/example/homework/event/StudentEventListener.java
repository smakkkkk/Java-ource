package com.example.homework.event;

import com.example.homework.event.StudentCreatedEvent;
import com.example.homework.event.StudentDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    @EventListener
    public void handleStudentCreated(StudentCreatedEvent event) {
        System.out.println("Student created: " + event.getStudent());
    }

    @EventListener
    public void handleStudentDeleted(StudentDeletedEvent event) {
        System.out.println("Student deleted: " + event.getStudentId());
    }
}