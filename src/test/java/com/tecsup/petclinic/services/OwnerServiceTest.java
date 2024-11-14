package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testFindOwnerById() {
        Integer ID = 1;
        String FIRST_NAME = "George";
        String LAST_NAME = "Franklin";
        Owner owner = null;

        try {
            owner = this.ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + owner);
        assertEquals(FIRST_NAME, owner.getFirstName());
        assertEquals(LAST_NAME, owner.getLastName());
    }

    @Test
    public void testCreateOwner() {
        String OWNER_FIRST_NAME = "Alice";
        String OWNER_LAST_NAME = "Smith";
        String OWNER_ADDRESS = "123 Main St";
        String OWNER_CITY = "Springfield";
        String OWNER_TELEPHONE = "1234567890";

        Owner owner = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);
        Owner ownerCreated = this.ownerService.create(owner);

        log.info("OWNER CREATED :" + ownerCreated);
        assertNotNull(ownerCreated.getId());
        assertEquals(OWNER_FIRST_NAME, ownerCreated.getFirstName());
        assertEquals(OWNER_LAST_NAME, ownerCreated.getLastName());
        assertEquals(OWNER_ADDRESS, ownerCreated.getAddress());
        assertEquals(OWNER_CITY, ownerCreated.getCity());
        assertEquals(OWNER_TELEPHONE, ownerCreated.getTelephone());
    }

    @Test
    public void testUpdateOwner() {
        String OWNER_FIRST_NAME = "Bob";
        String OWNER_LAST_NAME = "Johnson";
        String OWNER_ADDRESS = "456 Oak St";
        String OWNER_CITY = "Metropolis";
        String OWNER_TELEPHONE = "0987654321";

        String UP_OWNER_FIRST_NAME = "Bob Jr.";
        String UP_OWNER_LAST_NAME = "Johnson II";
        String UP_OWNER_ADDRESS = "789 Pine St";
        String UP_OWNER_CITY = "Gotham";
        String UP_OWNER_TELEPHONE = "1122334455";

        Owner owner = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);

        log.info(">" + owner);
        Owner ownerCreated = this.ownerService.create(owner);
        log.info(">>" + ownerCreated);

        ownerCreated.setFirstName(UP_OWNER_FIRST_NAME);
        ownerCreated.setLastName(UP_OWNER_LAST_NAME);
        ownerCreated.setAddress(UP_OWNER_ADDRESS);
        ownerCreated.setCity(UP_OWNER_CITY);
        ownerCreated.setTelephone(UP_OWNER_TELEPHONE);

        Owner updatedOwner = this.ownerService.update(ownerCreated);
        log.info(">>>>" + updatedOwner);

        assertEquals(UP_OWNER_FIRST_NAME, updatedOwner.getFirstName());
        assertEquals(UP_OWNER_LAST_NAME, updatedOwner.getLastName());
        assertEquals(UP_OWNER_ADDRESS, updatedOwner.getAddress());
        assertEquals(UP_OWNER_CITY, updatedOwner.getCity());
        assertEquals(UP_OWNER_TELEPHONE, updatedOwner.getTelephone());
    }

    @Test
    public void testDeleteOwner() {
        String OWNER_FIRST_NAME = "Charlie";
        String OWNER_LAST_NAME = "Brown";
        String OWNER_ADDRESS = "101 Maple St";
        String OWNER_CITY = "Peanuts";
        String OWNER_TELEPHONE = "5566778899";

        Owner owner = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);
        owner = this.ownerService.create(owner);
        log.info("" + owner);

        try {
            this.ownerService.delete(owner.getId());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        try {
            this.ownerService.findById(owner.getId());
            assertTrue(false);
        } catch (OwnerNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindOwnerByIdNotFound() {
        Integer nonExistentId = 999;
        try {
            this.ownerService.findById(nonExistentId);
            fail("Expected OwnerNotFoundException to be thrown");
        } catch (OwnerNotFoundException e) {
            assertTrue(true, "OwnerNotFoundException was thrown as expected");
        }
    }
}
