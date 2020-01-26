package com.dao.impl;

import com.JPATest;
import com.entities.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDAOTest extends JPATest {

    @Test
    public void save_test() {
        assertDoesNotThrow(this::setUpTestDepartment);
    }

    @Test
    public void delete_test() {
        Department department = setUpTestDepartment();
        assertDoesNotThrow(() -> departmentDAO.delete(department));
    }

    @Test
    public void findById_test() {
        Department savedDepartment = setUpTestDepartment();

        Optional<Department> optionalDepartment = departmentDAO.findById(savedDepartment.getId());

        assertTrue(optionalDepartment.isPresent());
        assertEquals(savedDepartment, optionalDepartment.get());
    }

    @Test
    public void findAll_test() {
        Department savedDepartment = setUpTestDepartment();

        List<Department> departments = departmentDAO.findAll();

        assertFalse(departments.isEmpty());
        assertTrue(departments.contains(savedDepartment));
    }

    @Test
    public void update_test() {
        Department department = setUpTestDepartment();

        department.setName("newName");

        assertDoesNotThrow(() -> departmentDAO.update(department));
    }

    @AfterEach
    public void tearDown() {
        departmentDAO.deleteAll();
    }
}
