package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.model.Role;
import com.nixsolutions.ppp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private final SessionFactory factory;
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    public UserDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long insert(User user) {
        Role role = new Role();
        role.setId(2L);
        role.setName("user");

        user.setRoles(Collections.singleton(role));
        factory.getCurrentSession().save(user);
        return user.getId();
    }

    @Override
    public List<User> findAll() {
        List<User> result = factory.getCurrentSession().createQuery("from users", User.class).getResultList();
        if (result != null) {
            result.forEach(item -> Hibernate.initialize(item.getGender()));
            result.forEach(item -> Hibernate.initialize(item.getRoles()));
        }
        return result;
    }

    @Override
    public void update(User user) {
        factory.getCurrentSession().update(user);
    }

    @Override
    public void delete(User user) {
        factory.getCurrentSession().delete(user);
    }

    @Override
    public User findUserByUsername(String username) {
        Query<User> query = factory.getCurrentSession().createQuery("from users where username=:username", User.class);
        query.setParameter("username", username);

        User result = query.getSingleResult();

        if (result != null) {
            Hibernate.initialize(result.getGender());
            Hibernate.initialize(result.getRoles());
        }
        return result;
    }

    @Override
    public User findById(Long id) {
        User user = factory.getCurrentSession().get(User.class, id);

        if (user != null) {
            Hibernate.initialize(user.getGender());
            Hibernate.initialize(user.getRoles());
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        delete(factory.getCurrentSession().get(User.class, id));
    }
}
