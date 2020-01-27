package com;

import com.dao.DepartmentDAO;
import com.dao.LectorDAO;
import com.dao.impl.DepartmentDAOImpl;
import com.dao.impl.LectorDAOImpl;
import com.entities.Degree;
import com.entities.Department;
import com.entities.Lector;
import com.service.DepartmentService;
import com.service.LectorService;
import com.service.impl.DepartmentServiceImpl;
import com.service.impl.LectorServiceImpl;
import com.view.DepartmentManagerMenu;
import com.view.Menu;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApplicationInitializer {
    private Session session;
    private DepartmentDAO departmentDAO;
    private LectorDAO lectorDAO;
    private DepartmentService departmentService;
    private LectorService lectorService;
    private Menu menu;

    public void init() {
        session = SessionManager.getSession();

        departmentDAO = new DepartmentDAOImpl(session);
        lectorDAO = new LectorDAOImpl(session);

        departmentService = new DepartmentServiceImpl(departmentDAO);
        lectorService = new LectorServiceImpl(lectorDAO);

        menu = new DepartmentManagerMenu(departmentService, lectorService);

        initDB(session);
    }

    private void initDB(Session session) {
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
                        new Lector("Tony Turner", Degree.PROFESSOR, 1200),
                        new Lector("Anisa Rodriguez", Degree.ASSISTANT, 300),
                        new Lector("Frank Randall", Degree.PROFESSOR, 1500),
                        new Lector("Edward Potter", Degree.PROFESSOR, 1200),
                        new Lector("George Joseph", Degree.PROFESSOR, 900),
                        new Lector("William Pope", Degree.ASSOCIATE_PROFESSOR, 800),
                        new Lector("Harry Yates", Degree.ASSOCIATE_PROFESSOR, 689),
                        new Lector("Joseph York", Degree.ASSOCIATE_PROFESSOR, 550),
                        new Lector("Cora Rowland", Degree.ASSISTANT, 400),
                        new Lector("Abigail Larsen", Degree.ASSISTANT, 350),
                        new Lector("Nina Lowery", Degree.ASSISTANT, 500)
                )
        );

        lectors.forEach(session::save);

        List<Department> departments = new ArrayList<>(
                Arrays.asList(
                        new Department("HCP", lectors.get(1), lectors.subList(1, 5)),
                        new Department("LPCK", lectors.get(2), lectors.subList(3, 7)),
                        new Department("GKTC", lectors.get(4), lectors.subList(2, 8)),
                        new Department("NULP", lectors.get(0), lectors.subList(0, 7)),
                        new Department("KLPN", lectors.get(6), lectors.subList(5, 15)),
                        new Department("LGH", lectors.get(9), lectors.subList(0, 18)),
                        new Department("KFC", lectors.get(10), lectors.subList(10, 18)),
                        new Department("CTG", lectors.get(5), lectors.subList(2, 17)),
                        new Department("LGHC", lectors.get(3), lectors.subList(4, 17))
                )
        );

        departments.forEach(session::save);
    }

}
