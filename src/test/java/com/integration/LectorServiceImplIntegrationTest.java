package com.integration;

import com.EntityUtils;
import com.JPATest;
import com.entities.Lector;
import com.service.LectorService;
import com.service.impl.LectorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LectorServiceImplIntegrationTest extends JPATest {
    private LectorService lectorService = new LectorServiceImpl(lectorDAO);

    @Test
    public void saveLector_test() {
        Lector lector = EntityUtils.getLector();
        assertDoesNotThrow(() -> lectorService.saveLector(lector));
        Optional<Lector> optionalLector = lectorDAO.findById(lector.getId());
        assertTrue(optionalLector.isPresent());
        assertEquals(lector, optionalLector.get());
    }

    @Test
    public void deleteLector_test() {
        Lector savedLector = setUpTestLector();

        Optional<Lector> optionalLector = lectorDAO.findById(savedLector.getId());
        assertTrue(optionalLector.isPresent());
        assertEquals(savedLector, optionalLector.get());

        lectorService.deleteLector(savedLector);

        Optional<Lector> deletedLector = lectorDAO.findById(savedLector.getId());
        assertFalse(deletedLector.isPresent());
    }

    @Test
    public void deleteNonExistentLector_test() {
        assertThrows(NoSuchElementException.class, () -> lectorService.deleteLector(EntityUtils.getLector()));
    }

    @Test
    public void updateLector_test() {
        Lector savedLector = setUpTestLector();

        Optional<Lector> optionalLector = lectorDAO.findById(savedLector.getId());
        assertTrue(optionalLector.isPresent());
        assertEquals(savedLector, optionalLector.get());

        Lector updatedLector = new Lector();
        updatedLector.setSalary(savedLector.getSalary() + 3);
        updatedLector.setName(savedLector.getName());
        updatedLector.setId(savedLector.getId());
        updatedLector.setDegree(savedLector.getDegree());

        lectorService.updateLector(updatedLector);

        Optional<Lector> updatedOptionalLector = lectorDAO.findById(savedLector.getId());

        assertTrue(updatedOptionalLector.isPresent());
        assertLectorFieldsEquals(updatedLector, updatedOptionalLector.get());
    }

    private void assertLectorFieldsEquals(Lector lector1, Lector lector2) {
        assertEquals(lector1.getId(), lector2.getId());
        assertEquals(lector1.getSalary(), lector2.getSalary());
        assertEquals(lector1.getName(), lector2.getName());
        assertEquals(lector1.getDegree(), lector2.getDegree());

    }

    @Test
    public void updateNonExistentLector_test() {
        assertThrows(NoSuchElementException.class, () -> lectorService.updateLector(EntityUtils.getLector()));
    }

    @Test
    public void getLectorByName_test() {
        List<Lector> savedLectors = setUpTestLectors();

        Optional<Lector> optionalLectorByName = lectorService.getLectorByName(savedLectors.get(0).getName());

        assertTrue(optionalLectorByName.isPresent());
        assertEquals(savedLectors.get(0), optionalLectorByName.get());
    }

    @Test
    public void getAllLectors() {
        List<Lector> savedLectors = setUpTestLectors();

        List<Lector> lectors = lectorService.getAllLectors();

        assertFalse(lectors.isEmpty());
        assertEquals(savedLectors.size(), lectors.size());
    }

    @Test
    public void getAllLectorsByNameTemplate() {
        setUpTestLectors();

        List<Lector> lectorsByNameTemplate = lectorService.getAllLectorsByNameTemplate("lie");

        assertEquals(2, lectorsByNameTemplate.size());
        assertTrue(lectorsByNameTemplate
                .stream()
                .map(Lector::getName)
                .allMatch(name -> Arrays.asList("Willie Russell", "Willie Lyons").contains(name)));

    }

    @AfterEach
    public void tearDown() {
        lectorDAO.deleteAll();
    }
}
