package com.example.skillboxthirdtask.repository;

import com.example.skillboxthirdtask.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    Optional<Contact> findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    // пакетная операция вставки (отправка несколько sql запросов за одну трансакцию)
    void batchInsert(List<Contact> contacts);
}
