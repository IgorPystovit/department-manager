package com;

import com.dao.DepartmentDAO;
import com.dao.LectorDAO;
import com.dao.impl.DepartmentDAOImpl;
import com.dao.impl.LectorDAOImpl;
import com.entities.Degree;
import com.entities.Department;
import com.entities.Lector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class JPATest {
    protected static SessionFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();

    private Session session = sessionFactory.openSession();
    protected DepartmentDAO departmentDAO = new DepartmentDAOImpl(session);

    protected LectorDAO lectorDAO = new LectorDAOImpl(session);

    protected Lector setUpTestLector() {
        Lector lector = new Lector("Anisa Rodriguez", Degree.ASSISTANT, 300);
        lectorDAO.save(lector);
        return lector;
    }

    public List<Lector> setUpTestLectors() {
        List<Lector> lectors = new ArrayList<>(
                Arrays.asList(
                        new Lector("Aidan Coffey", Degree.ASSISTANT, 350),
                        new Lector("Madeline Burnett", Degree.ASSISTANT, 200),
                        new Lector("Willie Russell", Degree.ASSISTANT, 300),
                        new Lector("Kieron Dotson", Degree.ASSOCIATE_PROFESSOR, 500),
                        new Lector("Willie Lyons", Degree.ASSOCIATE_PROFESSOR, 490),
                        new Lector("Reuben Hart", Degree.ASSOCIATE_PROFESSOR, 600),
                        new Lector("Haroon Bloggs", Degree.PROFESSOR, 900),
                        new Lector("Anas Bailey", Degree.PROFESSOR, 820),
                        new Lector("Tony Turner", Degree.PROFESSOR, 1200)
                )
        );

        lectors.forEach(lectorDAO::save);

        return lectors;
    }

    protected Department setUpTestDepartment() {
        List<Lector> lectors = EntityUtils.getLectorList();

        Department department = new Department("NULP", lectors.get(0), lectors.subList(0, 7));

        departmentDAO.save(department);

        return department;
    }

    protected List<Department> setUpTestDepartments() {
        List<Lector> lectors = setUpTestLectors();

        List<Department> departments = new ArrayList<>(
                Arrays.asList(
                        new Department("HCP", lectors.get(1), lectors.subList(1, 5)),
                        new Department("LPCK", lectors.get(2), lectors.subList(3, 7)),
                        new Department("GKTC", lectors.get(4), lectors.subList(2, 8))
                )
        );

        departments.forEach(departmentDAO::save);

        return departments;
    }
}
