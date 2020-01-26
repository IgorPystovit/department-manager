package com.service;

import com.entities.Department;
import com.service.impl.DepartmentStatistic;

import java.util.Optional;

public interface DepartmentService {
    void saveDepartment(Department newDepartment);

    void deleteDepartment(Department deleteDepartment);

    void updateDepartment(Department newDepartment);

    Optional<Department> getDepartmentByName(String departmentName);

    DepartmentStatistic getDepartmentStatistic(Department department);

    double getDepartmentAverageSalary(Department department);
}
