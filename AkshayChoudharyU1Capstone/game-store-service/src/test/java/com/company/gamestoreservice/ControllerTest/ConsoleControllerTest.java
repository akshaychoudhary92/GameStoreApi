package com.company.gamestoreservice.ControllerTest;


import com.company.gamestoreservice.controller.ConsoleController;
import com.company.gamestoreservice.dto.Console;
import com.company.gamestoreservice.service.GameStoreServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
@WithMockUser(username = "Admin01", authorities = {"ROLE_USER","ROLE_MANAGER","ROLE_STAFF","ROLE_ADMIN"})
public class ConsoleControllerTest {

    @MockBean
    DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameStoreServiceLayer repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {


    }

    @Test
    public void createConsole() throws Exception{

        Console inconsole = new Console();
        inconsole.setModel("Wii");
        inconsole.setManufacturer("Nintendo");
        inconsole.setMemoryAmount("32g");
        inconsole.setProcessor("dell");
        inconsole.setPrice(new BigDecimal(56.77));
        inconsole.setQuantity(2);

        String inputJson = mapper.writeValueAsString(inconsole);

        Console outconsole = new Console();
        outconsole.setConsoleId(7);
        outconsole.setModel("Wii");
        outconsole.setManufacturer("Nintendo");
        outconsole.setMemoryAmount("32g");
        outconsole.setProcessor("dell");
        outconsole.setPrice(new BigDecimal(56.77));
        outconsole.setQuantity(2);

        String outputJson = mapper.writeValueAsString(outconsole);

        when(repo.saveConsole(inconsole)).thenReturn(outconsole);

        this.mockMvc.perform(post("/console").content(inputJson).contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }


    @Test
    public void getConsole() throws Exception{


        Console console = new Console();
        console.setConsoleId(7);
        console.setModel("Wii");
        console.setManufacturer("Nintendo");
        console.setMemoryAmount("32g");
        console.setProcessor("dell");
        console.setPrice(new BigDecimal(56.77));
        console.setQuantity(2);

        Optional<Console> returnVal = Optional.of(console);
        String outputJson = mapper.writeValueAsString(console);
        when(repo.findConsole(7)).thenReturn(returnVal.get());

        this.mockMvc.perform(get("/console/getId/7"))
                .andDo(print())
                .andExpect(status().isOk())
                //use the objectmapper output with the json method
                .andExpect(content().json(outputJson));

    }

    @Test
    public void deleteConsole() {

        //can't mock the call to delete. it returns void
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/console/delete/7").with(csrf().asHeader()))
                    .andDo(print()).andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(""));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void updateConsole() throws Exception{

        Console console = new Console();

        console.setModel("Wii");
        console.setManufacturer("Nintendo");
        console.setMemoryAmount("32g");
        console.setProcessor("dell");
        console.setPrice(new BigDecimal(56.77));
        console.setQuantity(2);
        console.setConsoleId(7);


        //these will be the same
        String inputJson = mapper.writeValueAsString(console);
        String outputJson = mapper.writeValueAsString(console);

        this.mockMvc.perform(put("/console/update/" + console.getConsoleId()).with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getConsoleByManufacturer() {

        Console console = new Console();

        console.setModel("Wii");
        console.setManufacturer("Nintendo");
        console.setMemoryAmount("32g");
        console.setProcessor("dell");
        console.setPrice(new BigDecimal(56.77));
        console.setQuantity(2);
        console.setConsoleId(7);


    }



}
