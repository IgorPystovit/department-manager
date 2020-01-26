package com.dao;

import com.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDAO {
    void save(Department department);

    void delete(Department department);

    void update(Department department);

    Optional<Department> findById(Integer id);

    List<Department> findAll();

    void deleteAll();
}
