package com.service.impl;

import com.dao.DepartmentDAO;
import com.entities.Degree;
import com.entities.Department;
import com.entities.Lector;
import com.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDAO departmentDAO;

    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public void saveDepartment(Department newDepartment) {
        departmentDAO.save(newDepartment);
    }

    @Override
    public void deleteDepartment(Department deleteDepartment) {
        try {
            checkIfDepartmentExistsById(deleteDepartment.getId());
            departmentDAO.delete(deleteDepartment);
        } catch (NoSuchElementException e) {
            log.error("Attempt to delete non-existent Department");
            throw e;
        }
    }

    @Override
    public void updateDepartment(Department newDepartment) {
        try {
            checkIfDepartmentExistsById(newDepartment.getId());
            departmentDAO.update(newDepartment);
        } catch (NoSuchElementException e) {
            log.warn("Attempt to update non-existent Department");
            throw e;
        }
    }

    @Override
    public Optional<Department> getDepartmentByName(String departmentName) {
        return departmentDAO
                .findAll()
                .stream()
                .filter(department -> department.getName().equalsIgnoreCase(departmentName))
                .findAny();
    }

    @Override
    public DepartmentStatistic getDepartmentStatistic(Department department) {

        Map<Degree, Integer> statisticMap = department
                .getLectors()
                .stream()
                .collect(Collectors.groupingBy(Lector::getDegree))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
        return new DepartmentStatistic(statisticMap);
    }

    @Override
    public double getDepartmentAverageSalary(Department department) {
        return department
                .getLectors()
                .stream()
                .mapToDouble(Lector::getSalary)
                .average()
                .getAsDouble();
    }

    private void checkIfDepartmentExistsById(Integer id) {
        departmentDAO.findById(id).orElseThrow(() -> new NoSuchElementException("No Department entity with " + id + " present on DB"));
    }
}
