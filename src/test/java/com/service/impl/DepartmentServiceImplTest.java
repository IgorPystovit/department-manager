package com.service.impl;

import com.EntityUtils;
import com.dao.DepartmentDAO;
import com.entities.Degree;
import com.entities.Department;
import com.service.DepartmentService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentServiceImplTest {
    private DepartmentDAO departmentDAO = mock(DepartmentDAO.class);

    private DepartmentService departmentService = new DepartmentServiceImpl(departmentDAO);

    @Test
    public void saveDepartment_test() {
        Department department = EntityUtils.getDepartment();
        departmentService.saveDepartment(department);
        verify(departmentDAO).save(department);
    }

    @Test
    public void deleteDepartment_test() {
        Department deleteDepartment = EntityUtils.getDepartment();
        when(departmentDAO.findById(deleteDepartment.getId())).thenReturn(Optional.of(deleteDepartment));
        departmentService.deleteDepartment(deleteDepartment);
        verify(departmentDAO).delete(deleteDepartment);
    }

    @Test
    public void deleteNonExistentDepartment_test() {
        when(departmentDAO.findById(anyInt())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> departmentService.deleteDepartment(EntityUtils.getDepartment()));
        verify(departmentDAO, times(0)).delete(any(Department.class));
    }

    @Test
    public void updateDepartment_test() {
        Department department = EntityUtils.getDepartment();
        when(departmentDAO.findById(department.getId())).thenReturn(Optional.of(department));
        departmentService.updateDepartment(department);
        verify(departmentDAO).update(department);
    }

    @Test
    public void updateNonExistentDepartment_test() {
        when(departmentDAO.findById(anyInt())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> departmentService.updateDepartment(EntityUtils.getDepartment()));
        verify(departmentDAO, times(0)).update(any(Department.class));
    }

    @Test
    public void getDepartmentByName_test() {
        List<Department> departments = EntityUtils.getDepartments();
        when(departmentDAO.findAll()).thenReturn(departments);
        Optional<Department> optionalDepartmentByName = departmentService.getDepartmentByName(departments.get(0).getName());
        assertTrue(optionalDepartmentByName.isPresent());
        assertEquals(departments.get(0), optionalDepartmentByName.get());
    }

    @Test
    public void getDepartmentStatistic_test() {
        Department department = EntityUtils.getDepartment();
        DepartmentStatistic statistic = departmentService.getDepartmentStatistic(department);
        Map<Degree, Integer> statisticMap = statistic.getStatisticMap();

        assertEquals(1, statisticMap.get(Degree.PROFESSOR));
        assertEquals(3, statisticMap.get(Degree.ASSISTANT));
        assertEquals(3, statisticMap.get(Degree.ASSOCIATE_PROFESSOR));
    }

    @Test
    public void getDepartmentAverageSalary_test() {
        assertEquals(
                477.14, departmentService.getDepartmentAverageSalary(EntityUtils.getDepartment()), 0.01);
    }
}
