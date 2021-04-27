package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {
    private final SessionFactory factory;
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    public RoleDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void insert(Role role) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.save(role);

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Role> findAll() {
        Session session = factory.openSession();
        try {
            List<Role> result = session.createQuery("from roles", Role.class).getResultList();

            if (result != null) {
                result.forEach(item -> Hibernate.initialize(item.getUsers()));
            }
            return result;
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Role data) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.update(data);

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Role data) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.delete(data);

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public Role findByName(String name) {
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from roles where name=:name");
            query.setParameter("name", name);

            Role result = (Role) query.getSingleResult();

            if(result != null) {
                Hibernate.initialize(result.getUsers());
            }
            return result;
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }
}
