package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.model.User;

import java.util.List;

public interface UserDao {
    Long insert(User user);

    List<User> findAll();

    void update(User user);

    void delete(User user);

    User findUserByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);
}
