package com.dao.impl;

import com.dao.DepartmentDAO;
import com.dao.LectorDAO;
import com.entities.Degree;
import com.entities.Department;
import com.entities.Lector;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Collections;

public abstract class JPATest {
    protected SessionFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();

    protected DepartmentDAO departmentDAO = new DepartmentDAOImpl(sessionFactory.openSession());

    protected LectorDAO lectorDAO = new LectorDAOImpl(sessionFactory.openSession());

    protected Lector setUpTestLector() {
        Lector lector = new Lector("lector", Degree.PROFESSOR, 90);
        lectorDAO.save(lector);
        return lector;
    }

    protected Department setUpTestDepartment() {
        Lector lector = setUpTestLector();

        Department department = new Department("department", lector, Collections.singletonList(lector));

        departmentDAO.save(department);

        return department;
    }

}
