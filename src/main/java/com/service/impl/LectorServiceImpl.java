package com.service.impl;

import com.dao.LectorDAO;
import com.entities.Lector;
import com.service.LectorService;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class LectorServiceImpl implements LectorService {
    private LectorDAO lectorDAO;

    public LectorServiceImpl(LectorDAO lectorDAO) {
        this.lectorDAO = lectorDAO;
    }

    @Override
    public void saveLector(Lector newLector) {
        lectorDAO.save(newLector);
    }

    @Override
    public void deleteLector(Lector deleteLector) {
        try {
            checkIfLectorExistsById(deleteLector.getId());
            lectorDAO.delete(deleteLector);
        } catch (NoSuchElementException e) {
            log.error("Attempt to delete non-existent Lector");
        }
    }

    @Override
    public void updateLector(Lector newLector) {
        try {
            checkIfLectorExistsById(newLector.getId());
            lectorDAO.save(newLector);
        } catch (NoSuchElementException e) {
            log.error("Attempt to update non-existent Lector");
        }
    }

    @Override
    public Optional<Lector> getLectorByName(String lectorName) {
        return lectorDAO
                .findAll()
                .stream()
                .filter(lector -> lector.getName().equalsIgnoreCase(lectorName))
                .findAny();
    }

    private void checkIfLectorExistsById(Integer id) {
        lectorDAO.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
