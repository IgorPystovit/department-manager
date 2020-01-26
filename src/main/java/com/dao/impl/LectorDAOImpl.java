package com.dao.impl;

import com.dao.LectorDAO;
import com.entities.Lector;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class LectorDAOImpl implements LectorDAO {
    private Session session;

    public LectorDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public void save(Lector lector) {
        session.getTransaction().begin();

        session.save(lector);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Lector lector) {
        session.getTransaction().begin();

        session.delete(lector);

        session.getTransaction().commit();
    }

    @Override
    public Optional<Lector> findById(Integer id) {
        return Optional.ofNullable(session.find(Lector.class, id));
    }

    @Override
    public List<Lector> findAll() {
        return session
                .createQuery("select l from Lector l", Lector.class)
                .getResultList();
    }

    @Override
    public void update(Lector lector) {
        session.getTransaction().begin();

        session.merge(lector);

        session.getTransaction().commit();
    }

    @Override
    public void deleteAll() {
        session.getTransaction().begin();

        session.createQuery("delete from Lector ").executeUpdate();

        session.getTransaction().commit();
    }
}
