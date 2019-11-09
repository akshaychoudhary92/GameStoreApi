package com.company.gamestoreservice.ControllerTest;


import com.company.gamestoreservice.controller.TshirtController;
import com.company.gamestoreservice.dto.Tshirt;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TshirtController.class)
@WithMockUser(username = "Admin01", authorities = {"ROLE_USER","ROLE_MANAGER","ROLE_STAFF","ROLE_ADMIN"})
public class TshirtControllerTest {

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
    public void createTShirt() throws Exception {

        Tshirt  inShirt = new Tshirt();
        inShirt.setSize("large");
        inShirt.setColor("red");
        inShirt.setDescription("Slim fitting shirt");
        inShirt.setPrice(new BigDecimal(14.99));
        inShirt.setQuantity(8);

        String inputJson = mapper.writeValueAsString(inShirt);

        Tshirt outShirt = new Tshirt();
        outShirt.settShirtId(1);
        outShirt.setSize("large");
        outShirt.setColor("red");
        outShirt.setDescription("Slim fitting shirt");
        outShirt.setPrice(new BigDecimal(14.99));
        outShirt.setQuantity(8);

        String outputJson = mapper.writeValueAsString(outShirt);

        when(repo.saveTshirt(inShirt)).thenReturn(outShirt);

        this.mockMvc.perform(post("/tshirt").content(inputJson).contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));


    }


    @Test
    public void getTShirt() throws Exception{


        Tshirt tShirt = new Tshirt();
        tShirt.setSize("large");
        tShirt.setColor("red");
        tShirt.setDescription("Slim fitting shirt");
        tShirt.setPrice(new BigDecimal(14.99));
        tShirt.setQuantity(8);

        Optional<Tshirt> returnVal = Optional.of(tShirt);
        String outputJson = mapper.writeValueAsString(tShirt);
        when(repo.findTshirt(9)).thenReturn(returnVal.get());

        this.mockMvc.perform(get("/tshirt/9"))
                .andDo(print())
                .andExpect(status().isOk())
                //use the objectmapper output with the json method
                .andExpect(content().json(outputJson));

    }

    @Test
    public void deleteTShirt() {

        //can't mock the call to delete. it returns void
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/tshirt/9").with(csrf().asHeader()))
                    .andDo(print()).andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateTShirt() throws Exception{

        Tshirt  tShirt = new Tshirt();
        tShirt.setSize("large");
        tShirt.setColor("red");
        tShirt.setDescription("Slim fitting shirt");
        tShirt.setPrice(new BigDecimal(14.99));
        tShirt.setQuantity(8);


        //these will be the same
        String inputJson = mapper.writeValueAsString(tShirt);
        String outputJson = mapper.writeValueAsString(tShirt);

        this.mockMvc.perform(put("/tshirt/" + tShirt.gettShirtId()).with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getTShirtsByColor() throws Exception{

        Tshirt  tShirt = new Tshirt();
        tShirt.setSize("large");
        tShirt.setColor("red");
        tShirt.setDescription("Slim fitting shirt");
        tShirt.setPrice(new BigDecimal(14.99));
        tShirt.setQuantity(8);


        Tshirt  tShirt2 = new Tshirt();
        tShirt2.setSize("large");
        tShirt2.setColor("red");
        tShirt2.setDescription("Slim fitting shirt");
        tShirt2.setPrice(new BigDecimal(14.99));
        tShirt2.setQuantity(8);


        List<Tshirt> listChecker = new ArrayList<>();
        listChecker.add(tShirt);
        listChecker.add(tShirt2);

        when(repo.findTshirtByColor("red")).thenReturn(listChecker);

        List<Tshirt> listChecker2 = new ArrayList<>();

        listChecker2.addAll(listChecker);

        String outputJson = mapper.writeValueAsString(listChecker2);

        this.mockMvc.perform(get("/tshirt/color/red"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));



    }

    @Test
    public void getTShirtsBySize() throws Exception{

        Tshirt  tShirt = new Tshirt();
        tShirt.setSize("XL");
        tShirt.setColor("red");
        tShirt.setDescription("Slim fitting shirt");
        tShirt.setPrice(new BigDecimal(14.99));
        tShirt.setQuantity(8);


        Tshirt tShirt2 = new Tshirt();
        tShirt2.setSize("XL");
        tShirt2.setColor("red");
        tShirt2.setDescription("Slim fitting shirt");
        tShirt2.setPrice(new BigDecimal(14.99));
        tShirt2.setQuantity(8);


        List<Tshirt> listChecker = new ArrayList<>();
        listChecker.add(tShirt);
        listChecker.add(tShirt2);

        when(repo.findTshirtBySize("XL")).thenReturn(listChecker);

        List<Tshirt> listChecker2 = new ArrayList<>();

        listChecker2.addAll(listChecker);

        String outputJson = mapper.writeValueAsString(listChecker2);

        this.mockMvc.perform(get("/tshirt/size/XL"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }
}
