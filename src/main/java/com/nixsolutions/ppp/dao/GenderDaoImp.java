package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.model.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenderDaoImp implements GenderDao{
    private final SessionFactory factory;
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    public GenderDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    public void insert(Gender gender) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.save(gender);

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public List<Gender> findAll() {
        Session session = factory.openSession();
        try {
            return session.createQuery("from genders", Gender.class).getResultList();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public void update(Gender gender) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.update(gender);

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public void delete(Gender gender) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();

            session.delete(gender);

            session.getTransaction().commit();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }

    public Gender findGenderById(Long id) {
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from genders where id=:id");
            query.setParameter("id", id);

            return (Gender) query.getSingleResult();
        } catch (Exception ex) {
            LOG.catching(ex);
            session.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }
}

