package com.example.skillboxthirdtask.listener;

import com.example.skillboxthirdtask.model.Contact;
import com.example.skillboxthirdtask.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactCreator {

    private final ContactService contactService;

    // будет вызван после старта приложения и настройки контекста
    @EventListener(ApplicationStartedEvent.class)
    public void createTaskData() {
        log.debug("Calling DatabaseContactCreator createTaskData ...");
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        contact.setId(System.currentTimeMillis());
        contact.setFirstName("Ivan");
        contact.setLastName("Ivanov");
        contact.setEmail("ivanov@mail.ru");
        contact.setPhone("+74959996365");
        contacts.add(contact);

        contactService.bathInsert(contacts);
    }
}
