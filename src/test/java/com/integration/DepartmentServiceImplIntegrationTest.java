package com.integration;

import com.EntityUtils;
import com.JPATest;
import com.entities.Department;
import com.service.DepartmentService;
import com.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentServiceImplIntegrationTest extends JPATest {
    private DepartmentService departmentService = new DepartmentServiceImpl(departmentDAO);

    @Test
    public void save_test() {
        Department department = EntityUtils.getDepartment();

        assertDoesNotThrow(() -> departmentService.saveDepartment(department));

        Optional<Department> optionalDepartment = departmentDAO.findById(department.getId());
        assertTrue(optionalDepartment.isPresent());
        assertEquals(department, optionalDepartment.get());
    }

    @Test
    public void delete_test() {
        Department savedDepartment = setUpTestDepartment();

        Optional<Department> optionalDepartment = departmentDAO.findById(savedDepartment.getId());
        assertTrue(optionalDepartment.isPresent());
        assertEquals(savedDepartment, optionalDepartment.get());

        departmentService.deleteDepartment(savedDepartment);

        Optional<Department> deletedDepartment = departmentDAO.findById(savedDepartment.getId());
        assertFalse(deletedDepartment.isPresent());
    }

    @Test
    public void deleteNonExistentDepartment_test() {
        assertThrows(NoSuchElementException.class, () -> departmentService.deleteDepartment(EntityUtils.getDepartment()));
    }

    @Test
    public void update_test() {
        Department savedDepartment = setUpTestDepartment();

        Optional<Department> optionalDepartment = departmentDAO.findById(savedDepartment.getId());
        assertTrue(optionalDepartment.isPresent());
        assertEquals(savedDepartment, optionalDepartment.get());

        Department updatedDepartment =
                new Department("UPDATED DEPARTMENT", savedDepartment.getHead(), savedDepartment.getLectors());

        updatedDepartment.setId(savedDepartment.getId());

        departmentService.updateDepartment(updatedDepartment);

        Optional<Department> optionalUpdatedDepartment = departmentDAO.findById(savedDepartment.getId());
        assertTrue(optionalUpdatedDepartment.isPresent());
        assertDepartmentFieldsEquals(updatedDepartment, optionalUpdatedDepartment.get());
    }

    private void assertDepartmentFieldsEquals(Department department1, Department department2) {
        assertEquals(department1.getId(), department2.getId());
        assertEquals(department1.getName(), department2.getName());
        assertEquals(department1.getHead(), department2.getHead());
        assertEquals(department1.getLectors(), department2.getLectors());
    }

    @Test
    public void updateNonExistentDepartment_test() {
        assertThrows(NoSuchElementException.class, () -> departmentService.updateDepartment(EntityUtils.getDepartment()));
    }

    @Test
    public void getDepartmentByName_test() {
        List<Department> savedDepartments = setUpTestDepartments();

        Optional<Department> optionalDepartment = departmentService.getDepartmentByName(savedDepartments.get(0).getName());
        assertTrue(optionalDepartment.isPresent());
        assertEquals(savedDepartments.get(0), optionalDepartment.get());
    }

    @Test
    public void getAllDepartments_test() {
        List<Department> savedDepartments = setUpTestDepartments();

        List<Department> departments = departmentService.getAllDepartments();

        assertFalse(departments.isEmpty());
        assertEquals(savedDepartments.size(), departments.size());
    }

    @AfterEach
    public void tearDown() {
        departmentDAO.deleteAll();
    }
}
