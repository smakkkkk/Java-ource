package com.example.postnews.service;

import com.example.postnews.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> findAll();

    Page<User> findAll(int pageNumber, int pageSize);

    User findById(Long id);

    User save(User user);

    User update(User user);

    User deleteById(Long id);
}