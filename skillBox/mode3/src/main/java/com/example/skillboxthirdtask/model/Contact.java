package com.example.skillboxthirdtask.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
// предоставляет имена полей в ContactRowMapper
@FieldNameConstants
public class Contact {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
