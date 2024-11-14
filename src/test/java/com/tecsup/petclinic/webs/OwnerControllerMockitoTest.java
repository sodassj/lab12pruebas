package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.OwnerTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import com.tecsup.petclinic.services.OwnerService;
import com.tecsup.petclinic.util.TObjectCreator;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class OwnerControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private OwnerService ownerService;

    OwnerMapper mapper = Mappers.getMapper(OwnerMapper.class);

    @BeforeEach
    void setUp() {
        // Initialize RestAssured
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testFindAllOwners() throws Exception {

        int NRO_RECORD = 10;
        int ID_FIRST_RECORD = 1;

        List<OwnerTO> ownerTOs  = TObjectCreator.getAllOwnerTOs();

        List<Owner> owners  = this.mapper.toOwnerList(ownerTOs);

        Mockito.when(ownerService.findAll())
                .thenReturn(owners);

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(NRO_RECORD)))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindOwnerOK() throws Exception {

        OwnerTO ownerTO  = TObjectCreator.getOwnerTO();

        Owner owner  = this.mapper.toOwner(ownerTO);

        Mockito.when(ownerService.findById(owner.getId()))
                .thenReturn(owner);

        mockMvc.perform(get("/owners/1"))  // Object must be BASIL
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ownerTO.getId())))
                .andExpect(jsonPath("$.firstName", is(ownerTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(ownerTO.getLastName())))
                .andExpect(jsonPath("$.address", is(ownerTO.getAddress())))
                .andExpect(jsonPath("$.city", is(ownerTO.getCity())))
                .andExpect(jsonPath("$.telephone", is(ownerTO.getTelephone())));
    }

    @Test
    public void testFindOwnerKO() throws Exception {

        Integer ID_NOT_EXIST = 666;

        Mockito.when(this.ownerService.findById(ID_NOT_EXIST))
                .thenThrow(new OwnerNotFoundException("Record not found...!"));

        mockMvc.perform(get("/owners/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOwner() throws Exception {

        OwnerTO newOwnerTO  = TObjectCreator.newOwnerTO();

        Owner newOwner  = this.mapper.toOwner(newOwnerTO);

        Mockito.when(ownerService.create(newOwner))
                .thenReturn(newOwner);

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(newOwnerTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(newOwnerTO.getLastName())))
                .andExpect(jsonPath("$.address", is(newOwnerTO.getAddress())))
                .andExpect(jsonPath("$.city", is(newOwnerTO.getCity())))
                .andExpect(jsonPath("$.telephone", is(newOwnerTO.getTelephone())));
    }

    @Test
    public void testDeleteOwner() throws Exception {

        OwnerTO newOwnerTO  = TObjectCreator.newOwnerTOForDelete();

        Owner newOwner  = this.mapper.toOwner(newOwnerTO);

        Mockito.when(ownerService.create(newOwner))
                .thenReturn(newOwner);

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();

        Mockito.doNothing().when(this.ownerService).delete(newOwner.getId());

        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }
}

