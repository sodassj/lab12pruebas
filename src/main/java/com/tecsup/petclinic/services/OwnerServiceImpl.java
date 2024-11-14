package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;

/**
 *
 * @author jgomezm
 *
 */
@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    /**
     * Create a new owner
     *
     * @param owner
     * @return
     */
    @Override
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }

    /**
     * Update an existing owner
     *
     * @param owner
     * @return
     */
    @Override
    public Owner update(Owner owner) {
        return ownerRepository.save(owner);
    }

    /**
     * Delete an owner by ID
     *
     * @param id
     * @throws OwnerNotFoundException
     */
    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        Owner owner = findById(id);
        ownerRepository.delete(owner);
    }

    /**
     * Find an owner by ID
     *
     * @param id
     * @return
     * @throws OwnerNotFoundException
     */
    @Override
    public Owner findById(Integer id) throws OwnerNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (!owner.isPresent()) {
            throw new OwnerNotFoundException("Owner not found with ID: " + id);
        }
        return owner.get();
    }

    /**
     * Find owners by first name
     *
     * @param firstName
     * @return
     */
    @Override
    public List<Owner> findByFirstName(String firstName) {
        List<Owner> owners = ownerRepository.findByFirstName(firstName);
        owners.forEach(owner -> log.info("Owner: " + owner));
        return owners;
    }

    /**
     * Find owners by last name
     *
     * @param lastName
     * @return
     */
    @Override
    public List<Owner> findByLastName(String lastName) {
        List<Owner> owners = ownerRepository.findByLastName(lastName);
        owners.forEach(owner -> log.info("Owner: " + owner));
        return owners;
    }

    /**
     * Find owners by city
     *
     * @param city
     * @return
     */
    @Override
    public List<Owner> findByCity(String city) {
        List<Owner> owners = ownerRepository.findByCity(city);
        owners.forEach(owner -> log.info("Owner: " + owner));
        return owners;
    }

    /**
     * Find owners by telephone
     *
     * @param telephone
     * @return
     */
    @Override
    public List<Owner> findByTelephone(String telephone) {
        List<Owner> owners = ownerRepository.findByTelephone(telephone);
        owners.forEach(owner -> log.info("Owner: " + owner));
        return owners;
    }

    /**
     * Get all owners
     *
     * @return
     */
    @Override
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
}
