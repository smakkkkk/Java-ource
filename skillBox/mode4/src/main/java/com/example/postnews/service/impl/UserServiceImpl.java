package com.example.postnews.service.impl;

import com.example.postnews.entity.User;
import com.example.postnews.repository.UserRepository;
import com.example.postnews.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        log.debug("UserServiceImpl -> findAll");
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(int pageNumber, int pageSize) {
        log.debug("UserServiceImpl -> findAll pageNumber= {}, pageSize= {}", pageNumber, pageSize);
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public User findById(Long id) {
        log.debug("UserServiceImpl -> findById id= {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        log.debug("UserServiceImpl -> save user= {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        log.debug("UserServiceImpl -> update user= {}", user);
        User existedUser = userRepository.findById(user.getId()).orElse(null);
        if (existedUser != null) {
            existedUser.setUsername(user.getUsername());
            existedUser.setEmail(user.getEmail());
            existedUser.setComments(user.getComments());
            existedUser.setPosts(user.getPosts());
            return userRepository.save(existedUser);
        }
        return null;
    }

    @Override
    public User deleteById(Long id) {
        log.debug("UserServiceImpl -> deleteById id= {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
            return user;
        }
        return null;
    }
}