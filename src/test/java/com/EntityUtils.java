package com;

import com.entities.Degree;
import com.entities.Department;
import com.entities.Lector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityUtils {
    public static Lector getLector() {
        return new Lector("Anisa Rodriguez", Degree.ASSISTANT, 300);
    }

    public static List<Lector> getLectorList() {
        return new ArrayList<>(
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
    }

    public static Department getDepartment() {
        List<Lector> lectors = getLectorList();

        return new Department("NULP", lectors.get(0), lectors.subList(0, 7));
    }

    public static List<Department> getDepartments() {
        List<Lector> lectors = getLectorList();

        return new ArrayList<>(
                Arrays.asList(
                        new Department("HCP", lectors.get(1), lectors.subList(1, 5)),
                        new Department("LPCK", lectors.get(2), lectors.subList(3, 7)),
                        new Department("GKTC", lectors.get(4), lectors.subList(2, 8))
                )
        );
    }
}
