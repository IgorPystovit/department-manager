package com.service.impl;

import com.dao.LectorDAO;
import com.entities.Lector;
import com.service.LectorService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            throw e;
        }
    }

    @Override
    public void updateLector(Lector newLector) {
        try {
            checkIfLectorExistsById(newLector.getId());
            lectorDAO.update(newLector);
        } catch (NoSuchElementException e) {
            log.error("Attempt to update non-existent Lector");
            throw e;
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

    @Override
    public List<Lector> getAllLectors() {
        return lectorDAO.findAll();
    }

    @Override
    public List<Lector> getAllLectorsByNameTemplate(String template) {
        Pattern pattern = Pattern.compile(".*" + template.toUpperCase() + ".*");
        Predicate<Lector> lectorNameMatches =
                lector -> pattern.matcher(lector.getName().toUpperCase()).matches();
        return lectorDAO
                .findAll()
                .stream()
                .filter(lectorNameMatches)
                .collect(Collectors.toList());
    }

    private void checkIfLectorExistsById(Integer id) {
        lectorDAO.findById(id).orElseThrow(() -> new NoSuchElementException("No Lector entity with " + id + " present on DB"));
    }
}
