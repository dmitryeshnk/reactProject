package com.nixsolutions.ppp.service;

import com.nixsolutions.ppp.model.User;

import java.util.List;

public interface UserService {
    Long save(User user);

    void update(User user);

    List<User> findAll();

    void delete(User user);

    User findUserByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);

}
