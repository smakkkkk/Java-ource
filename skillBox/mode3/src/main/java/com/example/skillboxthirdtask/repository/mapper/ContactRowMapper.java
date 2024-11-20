package com.example.skillboxthirdtask.repository.mapper;

import com.example.skillboxthirdtask.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// отвечает за маппинг данных, считанных из БД в сущность Contact (результат маппинга Contact)
public class ContactRowMapper implements RowMapper<Contact> {

    @Override
    public Contact mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getLong(Contact.Fields.id));
        contact.setFirstName(resultSet.getString(Contact.Fields.firstName));
        contact.setLastName(resultSet.getString(Contact.Fields.lastName));
        contact.setEmail(resultSet.getString(Contact.Fields.email));
        contact.setPhone(resultSet.getString(Contact.Fields.phone));
        return contact;
    }
}
