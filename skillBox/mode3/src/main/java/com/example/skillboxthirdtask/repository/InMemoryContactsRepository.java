package com.example.skillboxthirdtask.repository;

import com.example.skillboxthirdtask.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// вариант репозитория без БД
@Repository
//@Primary
@Slf4j

public class InMemoryContactsRepository implements ContactRepository {

    private final List<Contact> contacts = new ArrayList<>();

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in InMemoryContactsRepository");
        return contacts;
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Call findById in InMemoryContactsRepository. Id is {}", id);
        return contacts.stream()
                .filter(contact -> contact.getId().equals(id))
                .findFirst();
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in InMemoryContactsRepository");
        contact.setId(System.currentTimeMillis());
        contacts.add(contact);
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in InMemoryContactsRepositoryy. Task is {}", contact);
        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null) {
            existedContact.setFirstName(contact.getFirstName());
            existedContact.setLastName(contact.getLastName());
            existedContact.setEmail(contact.getEmail());
            existedContact.setPhone(contact.getPhone());
        }
        return existedContact;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in InMemoryContactsRepository. ID is {}", id);
        findById(id).ifPresent(contacts::remove);
    }

    @Override
    public void batchInsert(List<Contact> contacts) {
        this.contacts.addAll(contacts);
    }
}