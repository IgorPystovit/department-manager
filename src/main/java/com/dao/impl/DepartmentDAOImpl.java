package com.dao.impl;

import com.dao.DepartmentDAO;
import com.entities.Department;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class DepartmentDAOImpl implements DepartmentDAO {
    private Session session;

    public DepartmentDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public void save(Department department) {
        session.getTransaction().begin();

        session.save(department);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Department department) {
        session.getTransaction().begin();

        session.delete(department);

        session.getTransaction().commit();
    }

    @Override
    public Optional<Department> findById(Integer id) {
        return Optional.ofNullable(session.find(Department.class, id));
    }

    @Override
    public List<Department> findAll() {
        return session
                .createQuery("select d from Department d", Department.class)
                .getResultList();
    }

    @Override
    public void deleteAll() {
        session.getTransaction().begin();

        session.createQuery("delete from Department ").executeUpdate();

        session.getTransaction().commit();
    }
}
