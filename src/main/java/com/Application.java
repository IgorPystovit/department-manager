package com;

import com.entities.Degree;
import com.entities.Department;
import com.entities.Lector;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    private static Session session = SessionManager.getSession();
//    private static DepartmentDAO departmentDAO = new DepartmentDAOImpl(session);
//    private static LectorDAO lectorDAO = new LectorDAOImpl(session);

    public static void main(String[] args) {
        init(session);
        System.out.println(session.createQuery("select l from Lector l where l.name = 'lector1'", Lector.class).getResultList().get(0).getDepartments());
        System.out.println(session.createQuery("select d from Department d where d.name = 'dep1'", Department.class).getResultList().get(0).getLectors());
    }

    private static void init(Session session) {
        List<Lector> lectors = new ArrayList<>(
                Arrays.asList(
                        new Lector("lector1", Degree.ASSISTANT, 115),
                        new Lector("lector2", Degree.PROFESSOR, 1111),
                        new Lector("lector3", Degree.ASSISTANT, 11),
                        new Lector("lector4", Degree.ASSOCIATE_PROFESSOR, 112),
                        new Lector("lector5", Degree.ASSISTANT, 113)));

        lectors.forEach(session::save);


        List<Department> departments = new ArrayList<>(
                Arrays.asList(
                        new Department("dep1", lectors.get(0), lectors.subList(0, 3)),
                        new Department("dep2", lectors.get(1), lectors.subList(1, 3))));
        departments.forEach(session::save);
    }
}
