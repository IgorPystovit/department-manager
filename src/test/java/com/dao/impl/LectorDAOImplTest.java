package com.dao.impl;

import com.JPATest;
import com.entities.Lector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LectorDAOImplTest extends JPATest {

    @Test
    public void save_test() {
        assertDoesNotThrow(this::setUpTestLector);
    }

    @Test
    public void delete_test() {
        Lector deleteLector = setUpTestLector();
        assertDoesNotThrow(() -> lectorDAO.delete(deleteLector));
    }

    @Test
    public void findById_test() {
        Lector lector = setUpTestLector();
        Optional<Lector> optionalLector = lectorDAO.findById(lector.getId());

        assertTrue(optionalLector.isPresent());
        assertEquals(lector, optionalLector.get());
    }

    @Test
    public void findAll_test() {
        Lector lector = setUpTestLector();
        List<Lector> lectors = lectorDAO.findAll();

        assertFalse(lectors.isEmpty());
        assertTrue(lectors.contains(lector));
    }

    @AfterEach
    public void tearDown() {
        lectorDAO.deleteAll();
    }
}