package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jgomezm
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OwnerTO {

    private Integer id;         // ID del propietario
    private String firstName;   // Primer nombre
    private String lastName;    // Apellido
    private String address;     // Dirección
    private String city;        // Ciudad
    private String telephone;   // Teléfono

}

