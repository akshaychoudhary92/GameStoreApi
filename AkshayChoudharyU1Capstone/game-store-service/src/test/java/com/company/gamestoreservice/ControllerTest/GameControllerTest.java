package com.company.gamestoreservice.ControllerTest;

import com.company.gamestoreservice.controller.GameController;
import com.company.gamestoreservice.dto.Game;
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
@WebMvcTest(GameController.class)
@WithMockUser(username = "Admin01", authorities = {"ROLE_USER","ROLE_MANAGER","ROLE_STAFF","ROLE_ADMIN"})
public class GameControllerTest {

    @MockBean
    DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameStoreServiceLayer gameService;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {

    }

    @Test
    public void createGame() throws Exception {

        Game inGame = new Game();
        inGame.setTitle("megaman");
        inGame.setEsrbRating("E");
        inGame.setDescription("A game that has megaman");
        inGame.setPrice(new BigDecimal(50.00));
        inGame.setStudio("EA Sports");
        inGame.setQuantity(8);

        String inputJson = mapper.writeValueAsString(inGame);

        Game outGame = new Game();
        outGame.setTitle("megaman");
        outGame.setEsrbRating("E");
        outGame.setDescription("A game that has megaman");
        outGame.setPrice(new BigDecimal(50.00));
        outGame.setStudio("EA Sports");
        outGame.setQuantity(8);

        String outputJson = mapper.writeValueAsString(outGame);

        when(gameService.saveGame(inGame)).thenReturn(outGame);

        this.mockMvc.perform(post("/game").content(inputJson).contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }



    @Test
    public void testGetGame() throws Exception {

        Game inputGame1 = new Game();
        inputGame1.setGameId(9);
        inputGame1.setTitle("megaman");
        inputGame1.setEsrbRating("E");
        inputGame1.setDescription("A game that has megaman");
        inputGame1.setPrice(new BigDecimal(50.00));
        inputGame1.setStudio("EA Sports");
        inputGame1.setQuantity(8);

        Optional<Game> returnVal = Optional.of(inputGame1);
        String outputJson = mapper.writeValueAsString(inputGame1);
        when(gameService.findGame(9)).thenReturn(returnVal.get());

        this.mockMvc.perform(get("/game/9"))
                .andDo(print())
                .andExpect(status().isOk())
                //use the objectmapper output with the json method
                .andExpect(content().json(outputJson));


    }

    @Test
    public void deleteGame() {

        //can't mock the call to delete. it returns void
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/game/9").with(csrf().asHeader()))
                    .andDo(print()).andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdateGame() throws Exception {

        Game inputGame1 = new Game();
        inputGame1.setGameId(2);
        inputGame1.setTitle("megaman");
        inputGame1.setEsrbRating("E");
        inputGame1.setDescription("A game that has megaman");
        inputGame1.setPrice(new BigDecimal(50.00));
        inputGame1.setStudio("EA Sports");
        inputGame1.setQuantity(8);


        //these will be the same
        String inputJson = mapper.writeValueAsString(inputGame1);
        String outputJson = mapper.writeValueAsString(inputGame1);

        this.mockMvc.perform(put("/game/" + inputGame1.getGameId()).with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getGamesByStudio() throws Exception {

        Game game = new Game();
        game.setGameId(9);
        game.setTitle("megaman");
        game.setEsrbRating("E");
        game.setDescription("its a game for the gameboy");
        game.setPrice(new BigDecimal(13.78));
        game.setStudio("gameboy");
        game.setQuantity(2);

        Game game2 = new Game();
        game2.setGameId(2);
        game2.setTitle("Donkey Kong");
        game2.setEsrbRating("M");
        game2.setDescription("its a game get it?");
        game2.setPrice(new BigDecimal(39.99));
        game2.setStudio("EA Sports");
        game2.setQuantity(15);

        Game game3 = new Game();
        game3.setGameId(8);
        game3.setTitle("Supper Mario cart");
        game3.setEsrbRating("M");
        game3.setDescription("One of the first games to come out for he gameboy");
        game3.setPrice(new BigDecimal(41.99));
        game3.setStudio("gameboy");
        game3.setQuantity(13);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);
        gameList.add(game3);

        when(gameService.findGamesByStudio("gameboy")).thenReturn(gameList);

        List<Game> gameList2 = new ArrayList<>();

        gameList2.addAll(gameList);

        String outputJson = mapper.writeValueAsString(gameList2);

        this.mockMvc.perform(get("/game/studio/gameboy"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(outputJson));


    }


    @Test
    public void getGamesByErsbRating() throws Exception {

        Game game = new Game();
        game.setGameId(9);
        game.setTitle("megaman");
        game.setEsrbRating("E");
        game.setDescription("its a game for the gameboy");
        game.setPrice(new BigDecimal(13.78));
        game.setStudio("gameboy");
        game.setQuantity(2);

        Game game2 = new Game();
        game2.setGameId(2);
        game2.setTitle("Donkey Kong");
        game2.setEsrbRating("M");
        game2.setDescription("its a game get it?");
        game2.setPrice(new BigDecimal(39.99));
        game2.setStudio("EA Sports");
        game2.setQuantity(15);

        Game game3 = new Game();
        game3.setGameId(8);
        game3.setTitle("Supper Mario cart");
        game3.setEsrbRating("M");
        game3.setDescription("One of the first games to come out for he gameboy");
        game3.setPrice(new BigDecimal(41.99));
        game3.setStudio("gameboy");
        game3.setQuantity(13);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);
        gameList.add(game3);

        when(gameService.findGamesByEsrbRating("M")).thenReturn(gameList);

        List<Game> gameList2 = new ArrayList<>();

        gameList2.addAll(gameList);

        String outputJson = mapper.writeValueAsString(gameList2);

        this.mockMvc.perform(get("/game/esrb/M/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getGamesByTitle() throws Exception {

        Game game = new Game();
        game.setGameId(9);
        game.setTitle("SuperMario");
        game.setEsrbRating("E");
        game.setDescription("its a game for the gameboy");
        game.setPrice(new BigDecimal(13.78));
        game.setStudio("gameboy");
        game.setQuantity(2);

        Game game2 = new Game();
        game2.setGameId(2);
        game2.setTitle("SuperMario");
        game2.setEsrbRating("M");
        game2.setDescription("its a game get it?");
        game2.setPrice(new BigDecimal(39.99));
        game2.setStudio("EA Sports");
        game2.setQuantity(15);

//        Game game3 = new Game();
//        game3.setGameId(8);
//        game3.setTitle("Supper Mario cart");
//        game3.setEsrbRating("M");
//        game3.setDescription("One of the first games to come out for he gameboy");
//        game3.setPrice(new BigDecimal(41.99));
//        game3.setStudio("gameboy");
//        game3.setQuantity(13);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);
//        gameList.add(game3);

        when(gameService.findGamesByTitle("SuperMario")).thenReturn(gameList);

        List<Game> gameList2 = new ArrayList<>();

        gameList2.addAll(gameList);

        String outputJson = mapper.writeValueAsString(gameList2);

        this.mockMvc.perform(get("/game/title/SuperMario"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));


    }
}
