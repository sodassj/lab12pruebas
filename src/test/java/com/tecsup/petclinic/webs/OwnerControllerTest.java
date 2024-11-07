package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.OwnerTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllOwners() throws Exception {

        // Verifica el primer propietario (id=1)
        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }


    /**
     *
     * @throws Exception
     *
     */
    @Test
    public void testFindOwnerOK() throws Exception {

        String OWNER_NAME = "George Franklin";
        String CITY = "Madison";
        String TELEPHONE = "6085551023";

        mockMvc.perform(get("/owners/1"))  // Busca al propietario con id 1
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("George")))
                .andExpect(jsonPath("$.lastName", is("Franklin")))
                .andExpect(jsonPath("$.city", is(CITY)))
                .andExpect(jsonPath("$.telephone", is(TELEPHONE)));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testFindOwnerKO() throws Exception {

        mockMvc.perform(get("/owners/9999"))  // No debería existir el propietario con id 9999
                .andExpect(status().isNotFound());

    }

    /**
     * @throws Exception
     */
    @Test
    public void testCreateOwner() throws Exception {

        String FIRST_NAME = "Alice";
        String LAST_NAME = "Johnson";
        String ADDRESS = "123 Elm St.";
        String CITY = "Madison";
        String TELEPHONE = "6085551234";

        OwnerTO newOwnerTO = new OwnerTO();
        newOwnerTO.setFirstName(FIRST_NAME);
        newOwnerTO.setLastName(LAST_NAME);
        newOwnerTO.setAddress(ADDRESS);
        newOwnerTO.setCity(CITY);
        newOwnerTO.setTelephone(TELEPHONE);

        mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.city", is(CITY)))
                .andExpect(jsonPath("$.telephone", is(TELEPHONE)));

    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void testDeleteOwner() throws Exception {

        String FIRST_NAME = "Jack";
        String LAST_NAME = "Smith";
        String ADDRESS = "789 Pine St.";
        String CITY = "Madison";
        String TELEPHONE = "6085559876";

        OwnerTO newOwnerTO = new OwnerTO();
        newOwnerTO.setFirstName(FIRST_NAME);
        newOwnerTO.setLastName(LAST_NAME);
        newOwnerTO.setAddress(ADDRESS);
        newOwnerTO.setCity(CITY);
        newOwnerTO.setTelephone(TELEPHONE);

        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();

        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/owners/" + id ))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteOwnerKO() throws Exception {

        mockMvc.perform(delete("/owners/1000"))  // No debería existir el propietario con id 1000
                .andExpect(status().isNotFound());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testUpdateOwner() throws Exception {

        String FIRST_NAME = "John";
        String LAST_NAME = "Doe";
        String ADDRESS = "456 Oak St.";
        String CITY = "Madison";
        String TELEPHONE = "6085556543";

        String UPDATED_FIRST_NAME = "Johnathan";
        String UPDATED_LAST_NAME = "Doe";
        String UPDATED_ADDRESS = "456 Oak St. Suite 2";
        String UPDATED_CITY = "Sun Prairie";
        String UPDATED_TELEPHONE = "6085554321";

        OwnerTO newOwnerTO = new OwnerTO();
        newOwnerTO.setFirstName(FIRST_NAME);
        newOwnerTO.setLastName(LAST_NAME);
        newOwnerTO.setAddress(ADDRESS);
        newOwnerTO.setCity(CITY);
        newOwnerTO.setTelephone(TELEPHONE);

        // CREAR
        ResultActions mvcActions = mockMvc.perform(post("/owners")
                        .content(om.writeValueAsString(newOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // ACTUALIZAR
        OwnerTO upOwnerTO = new OwnerTO();
        upOwnerTO.setId(id);
        upOwnerTO.setFirstName(UPDATED_FIRST_NAME);
        upOwnerTO.setLastName(UPDATED_LAST_NAME);
        upOwnerTO.setAddress(UPDATED_ADDRESS);
        upOwnerTO.setCity(UPDATED_CITY);
        upOwnerTO.setTelephone(UPDATED_TELEPHONE);

        mockMvc.perform(put("/owners/"+id)
                        .content(om.writeValueAsString(upOwnerTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // CONSULTAR
        mockMvc.perform(get("/owners/" + id))  //
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is(UPDATED_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(UPDATED_LAST_NAME)))
                .andExpect(jsonPath("$.city", is(UPDATED_CITY)))
                .andExpect(jsonPath("$.telephone", is(UPDATED_TELEPHONE)));

        // ELIMINAR
        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }
}

