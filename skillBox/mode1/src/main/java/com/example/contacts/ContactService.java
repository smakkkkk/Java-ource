package com.example.contacts;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactService {
    private List<Contact> contacts;

    public ContactService() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContactByEmail(String email) {
        contacts.removeIf(contact -> contact.getEmail().equalsIgnoreCase(email));
    }

    public void printAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Нет контактов для отображения.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
