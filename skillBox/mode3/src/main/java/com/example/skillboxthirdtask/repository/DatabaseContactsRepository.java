package com.example.skillboxthirdtask.repository;

import com.example.skillboxthirdtask.exeption.ContactNotFoundException;
import com.example.skillboxthirdtask.model.Contact;
import com.example.skillboxthirdtask.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// вариант репозитория c БД
@Repository
// !!! приоритет создания этого бина !!!
@Primary
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactsRepository implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("Calling DatabaseContactsRepository -> findAll");
        String sqlRequest = "SELECT * FROM contact";
        return jdbcTemplate.query(sqlRequest, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling DatabaseContactsRepository -> findById with ID: {}", id);
        String sqlRequest = "SELECT * FROM contact WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sqlRequest,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Calling DatabaseContactsRepository -> save with Contact: {}", contact);
        contact.setId(System.currentTimeMillis());
        String sqlRequest = "INSERT INTO contact (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlRequest, contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling DatabaseContactsRepository -> update with Contact: {}", contact);
        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null) {
            String sqlRequest = "UPDATE contact SET firstName = ?, lastName = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sqlRequest, contact.getFirstName(), contact.getLastName(),
                    contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }
        log.warn("Contact with Id {} not found", contact.getId());
        throw new ContactNotFoundException("Contact for update not found! Id: " + contact.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DatabaseContactsRepository -> deleteById with ID: {}", id);
        String sqlRequest = "DELETE FROM contact WHERE id = ?";
        jdbcTemplate.update(sqlRequest, id);
    }

    // пакетная операция вставки (отправка несколько sql запросов за одну трансакцию)
    @Override
    public void batchInsert(List<Contact> contacts) {
        log.debug("Calling DatabaseContactsRepository -> batchInsert");
        String sqlRequest = "INSERT INTO contact (firstName, lastName, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sqlRequest, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Contact contact = contacts.get(i);
                preparedStatement.setString(1, contact.getFirstName());
                preparedStatement.setString(2, contact.getLastName());
                preparedStatement.setString(3, contact.getEmail());
                preparedStatement.setString(4, contact.getPhone());
                preparedStatement.setLong(5, contact.getId());
            }
            // размер пакетной операции
            @Override
            public int getBatchSize() {
                return contacts.size();
            }
        });
    }
}