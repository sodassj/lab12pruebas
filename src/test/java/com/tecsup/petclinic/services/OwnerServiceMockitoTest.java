package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class OwnerServiceMockitoTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService; // Cambiado de OwnerService a OwnerServiceImpl

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindOwnerById() throws OwnerNotFoundException {
        Integer ID = 1;
        String FIRST_NAME = "George";
        String LAST_NAME = "Franklin";

        Owner mockOwner = new Owner();
        mockOwner.setId(ID);
        mockOwner.setFirstName(FIRST_NAME);
        mockOwner.setLastName(LAST_NAME);

        when(ownerRepository.findById(ID)).thenReturn(Optional.of(mockOwner));

        Owner foundOwner = ownerService.findById(ID);

        assertNotNull(foundOwner);
        assertEquals(FIRST_NAME, foundOwner.getFirstName());
        assertEquals(LAST_NAME, foundOwner.getLastName());
    }

    @Test
    public void testFindOwnerByIdNotFound() {
        Integer nonExistentId = 999;

        when(ownerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findById(nonExistentId);
        });
    }

    @Test
    public void testCreateOwner() {
        String OWNER_FIRST_NAME = "Alice";
        String OWNER_LAST_NAME = "Smith";
        String OWNER_ADDRESS = "123 Main St";
        String OWNER_CITY = "Springfield";
        String OWNER_TELEPHONE = "1234567890";

        Owner ownerToCreate = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);

        when(ownerRepository.save(any(Owner.class))).thenReturn(ownerToCreate);

        Owner createdOwner = ownerService.create(ownerToCreate);

        assertNotNull(createdOwner);
        assertEquals(OWNER_FIRST_NAME, createdOwner.getFirstName());
        assertEquals(OWNER_LAST_NAME, createdOwner.getLastName());
        assertEquals(OWNER_ADDRESS, createdOwner.getAddress());
        assertEquals(OWNER_CITY, createdOwner.getCity());
        assertEquals(OWNER_TELEPHONE, createdOwner.getTelephone());
    }

    @Test
    public void testUpdateOwner() throws OwnerNotFoundException {
        Integer ID = 1;
        String OWNER_FIRST_NAME = "Bob";
        String OWNER_LAST_NAME = "Johnson";
        String UPDATED_OWNER_FIRST_NAME = "Bob Jr.";
        String UPDATED_OWNER_LAST_NAME = "Johnson II";

        Owner existingOwner = new Owner();
        existingOwner.setId(ID);
        existingOwner.setFirstName(OWNER_FIRST_NAME);
        existingOwner.setLastName(OWNER_LAST_NAME);

        Owner updatedOwner = new Owner();
        updatedOwner.setId(ID);
        updatedOwner.setFirstName(UPDATED_OWNER_FIRST_NAME);
        updatedOwner.setLastName(UPDATED_OWNER_LAST_NAME);

        when(ownerRepository.findById(ID)).thenReturn(Optional.of(existingOwner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(updatedOwner);

        Owner result = ownerService.update(existingOwner);

        assertNotNull(result);
        assertEquals(UPDATED_OWNER_FIRST_NAME, result.getFirstName());
        assertEquals(UPDATED_OWNER_LAST_NAME, result.getLastName());
    }

    @Test
    public void testDeleteOwner() throws OwnerNotFoundException {
        Integer ID = 1;

        Owner ownerToDelete = new Owner();
        ownerToDelete.setId(ID);

        when(ownerRepository.findById(ID)).thenReturn(Optional.of(ownerToDelete));
        doNothing().when(ownerRepository).deleteById(ID);

        ownerService.delete(ID);
    }
}
