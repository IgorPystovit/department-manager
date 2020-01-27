package com.service.impl;

import com.EntityUtils;
import com.dao.LectorDAO;
import com.entities.Lector;
import com.service.LectorService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LectorServiceImplTest {
    private LectorDAO lectorDAO = mock(LectorDAO.class);

    private LectorService lectorService = new LectorServiceImpl(lectorDAO);

    @Test
    public void saveLector_test() {
        Lector lector = EntityUtils.getLector();
        lectorService.saveLector(lector);
        verify(lectorDAO).save(lector);
    }

    @Test
    public void delete_test() {
        Lector deleteLector = EntityUtils.getLector();
        when(lectorDAO.findById(deleteLector.getId())).thenReturn(Optional.of(deleteLector));
        lectorService.deleteLector(deleteLector);
        verify(lectorDAO).delete(deleteLector);
    }

    @Test
    public void deleteNonExistentLector_test() {
        when(lectorDAO.findById(anyInt())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> lectorService.deleteLector(EntityUtils.getLector()));
        verify(lectorDAO, times(0)).delete(any(Lector.class));
    }

    @Test
    public void updateLector_test() {
        Lector lector = EntityUtils.getLector();
        when(lectorDAO.findById(lector.getId())).thenReturn(Optional.of(new Lector()));
        lectorService.updateLector(lector);
        verify(lectorDAO).update(lector);
    }

    @Test
    public void updateNonExistentLector_test() {
        when(lectorDAO.findById(anyInt())).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> lectorService.updateLector(EntityUtils.getLector()));
        verify(lectorDAO, times(0)).update(any(Lector.class));
    }

    @Test
    public void getLectorByName_test() {
        List<Lector> lectors = EntityUtils.getLectorList();
        when(lectorDAO.findAll()).thenReturn(lectors);

        Optional<Lector> optionalLectorByName = lectorService.getLectorByName(lectors.get(0).getName());

        assertTrue(optionalLectorByName.isPresent());
        assertEquals(lectors.get(0), optionalLectorByName.get());
    }

    @Test
    public void getAllLectors_test() {
        when(lectorDAO.findAll()).thenReturn(EntityUtils.getLectorList());

        List<Lector> lectors = lectorService.getAllLectors();
        assertFalse(lectors.isEmpty());
    }

    @Test
    public void getAllLectorsByNameTemplate_test() {
        when(lectorDAO.findAll()).thenReturn(EntityUtils.getLectorList());

        List<Lector> lectorsByNameTemplate = lectorService.getAllLectorsByNameTemplate("lie");
        assertEquals(2, lectorsByNameTemplate.size());
        assertTrue(lectorsByNameTemplate
                .stream()
                .map(Lector::getName)
                .allMatch(name -> Arrays.asList("Willie Russell", "Willie Lyons").contains(name)));
    }
}
