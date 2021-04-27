package com.nixsolutions.ppp.service;

import com.nixsolutions.ppp.dao.UserDao;
import com.nixsolutions.ppp.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public Long save(User user) {
        try {
            userDao.insert(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user.getId();
    }

    @Transactional
    public void update(User user) {
        try {
            userDao.update(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public void delete(User user) {
        try {
            userDao.delete(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Transactional
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            userDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
