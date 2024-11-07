package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

/**
 *
 * @author jgomezm
 *
 */
public interface OwnerService {

    /**
     * Create a new owner
     *
     * @param owner
     * @return
     */
    Owner create(Owner owner);

    /**
     * Update an existing owner
     *
     * @param owner
     * @return
     */
    Owner update(Owner owner);

    /**
     * Delete an owner by ID
     *
     * @param id
     * @throws OwnerNotFoundException
     */
    void delete(Integer id) throws OwnerNotFoundException;

    /**
     * Find an owner by ID
     *
     * @param id
     * @return
     * @throws OwnerNotFoundException
     */
    Owner findById(Integer id) throws OwnerNotFoundException;

    /**
     * Find owners by first name
     *
     * @param firstName
     * @return
     */
    List<Owner> findByFirstName(String firstName);

    /**
     * Find owners by last name
     *
     * @param lastName
     * @return
     */
    List<Owner> findByLastName(String lastName);

    /**
     * Find owners by city
     *
     * @param city
     * @return
     */
    List<Owner> findByCity(String city);

    /**
     * Find owners by telephone
     *
     * @param telephone
     * @return
     */
    List<Owner> findByTelephone(String telephone);

    /**
     * Get all owners
     *
     * @return
     */
    List<Owner> findAll();
}
