package com.example.contacts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactFileRepository {

    @Value("${contacts.filepath}")
    private String filePath;

    public List<Contact> loadContacts() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return List.of();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        return new Contact(parts[0], parts[1], parts[2]);
                    })
                    .collect(Collectors.toList());
        }
    }

    public void saveContacts(List<Contact> contacts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact contact : contacts) {
                writer.write(contact.getFullName() + ";" + contact.getPhoneNumber() + ";" + contact.getEmail());
                writer.newLine();
            }
        }
    }
}
