package com.dao;

import com.entities.Lector;

import java.util.List;
import java.util.Optional;

public interface LectorDAO {
    void save(Lector lector);

    void delete(Lector lector);

    void update(Lector lector);

    Optional<Lector> findById(Integer id);

    List<Lector> findAll();


    void deleteAll();
}
