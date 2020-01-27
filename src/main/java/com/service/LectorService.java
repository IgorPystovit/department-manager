package com.service;

import com.entities.Lector;

import java.util.List;
import java.util.Optional;

public interface LectorService {
    void saveLector(Lector newLector);

    void deleteLector(Lector deleteLector);

    void updateLector(Lector newLector);

    Optional<Lector> getLectorByName(String lectorName);

    List<Lector> getAllLectors();

    List<Lector> getAllLectorsByNameTemplate(String template);
}
