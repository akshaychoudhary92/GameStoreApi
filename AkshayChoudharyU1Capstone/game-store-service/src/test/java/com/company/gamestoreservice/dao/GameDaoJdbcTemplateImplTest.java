package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Console;
import com.company.gamestoreservice.dto.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameDaoJdbcTemplateImplTest {


@Autowired
protected GameDao gameDao;

@Autowired
protected ConsoleDao consoleDao;


    @Before
    public void setUp() throws Exception {
        List<Game> gameList = gameDao.getAllGames();

        gameList.stream()
                .forEach(game -> gameDao.deleteGame(game.getGameId()));
        List<Console> consolesList = consoleDao.getAllConsoles();

        consolesList.stream()
                .forEach(console -> consoleDao.deleteConsole(console.getConsoleId()));
    }






    @Test
    public void addGetDeleteGame() {

        Game game = new Game();
        game.setTitle("pokemon");
        game.setEsrbRating("E");
        game.setDescription("gotta catch em all");
        game.setPrice(new BigDecimal("17.89"));
        game.setStudio("Nintendo softworks");
        game.setQuantity(2);

        game = gameDao.addGame(game);

        Game game1 = gameDao.getGame(game.getGameId());
        assertEquals(game1, game);

        gameDao.deleteGame(game.getGameId());
        game1 = gameDao.getGame(game.getGameId());
        assertNull(game1);

    }



    @Test
    public void getAllGames() {
        Game game = new Game();
        game.setTitle("pokemon");
        game.setEsrbRating("E");
        game.setDescription("gotta catch em all");
        game.setPrice(new BigDecimal("17.89"));
        game.setStudio("Nintendo softworks");
        game.setQuantity(2);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("digimon");
        game.setEsrbRating("E for everyone");
        game.setDescription("This is an game about digimon.");
        game.setPrice(new BigDecimal("20.00"));
        game.setStudio("dig evolution");
        game.setQuantity(5);

        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.getAllGames();
        assertEquals(2, gameList.size());
    }


    @Test
    public void updateGame() {
        Game game = new Game();
        game.setTitle("pokemon");
        game.setEsrbRating("E");
        game.setDescription("gotta catch em all");
        game.setPrice(new BigDecimal("17.89"));
        game.setStudio("Nintendo softworks");
        game.setQuantity(2);

        game = gameDao.addGame(game);

        game.setTitle("yugioh");
        game.setEsrbRating("M");
        game.setDescription("game about cards");
        game.setPrice(new BigDecimal("99.99"));
        game.setStudio("blah studios");
        game.setQuantity(22);

        gameDao.updateGame(game);

        Game game1 = gameDao.getGame(game.getGameId());
        assertEquals(game, game1);
    }



    @Test
    public void getGamesByStudio() {
        Game game = new Game();
        game.setTitle("pokemon");
        game.setEsrbRating("E");
        game.setDescription("gotta catch em all");
        game.setPrice(new BigDecimal("17.89"));
        game.setStudio("Nintendo softworks");
        game.setQuantity(2);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("Minecraft");
        game.setEsrbRating("EVERYONE 10+");
        game.setDescription("This is a puzzle-adventure game");
        game.setPrice(new BigDecimal("15.99"));
        game.setStudio("Microsoft");
        game.setQuantity(1);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("Gears of War 4");
        game.setEsrbRating("MATURE");
        game.setDescription("This is a third-person shooter game.");
        game.setPrice(new BigDecimal("23.99"));
        game.setStudio("Microsoft");
        game.setQuantity(3);

        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.getGamesByStudio("Microsoft");
        assertEquals(2, gameList.size());

        List<Game> gameList1 = gameDao.getGamesByStudio("Nintendo softworks");
        assertEquals(1, gameList1.size());
    }

    @Test
    public void getGamesByTitle() {
        Game game = new Game();
        game.setTitle("yugioh");
        game.setEsrbRating("M");
        game.setDescription("game about cards");
        game.setPrice(new BigDecimal("99.99"));
        game.setStudio("blah studios");
        game.setQuantity(22);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("Stupid game");
        game.setEsrbRating("EVERYONE 10+");
        game.setDescription("This is a stupid game ");
        game.setPrice(new BigDecimal("23.99"));
        game.setStudio("Microsoft");
        game.setQuantity(14);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("stupid game");
        game.setEsrbRating("e for everyone");
        game.setDescription("This is also a stupid game.");
        game.setPrice(new BigDecimal("34.09"));
        game.setStudio("Tell Tale Games");
        game.setQuantity(5);

        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.getGamesByTitle("stupid game");
        assertEquals(2, gameList.size());
    }

    @Test
    public void getGamesByEsrbRating() {
        Game game = new Game();
        game.setTitle("Elmo's World");
        game.setEsrbRating("EVERYONE");
        game.setDescription("its a game about elmo");
        game.setPrice(new BigDecimal("15.99"));
        game.setStudio("teststudios");
        game.setQuantity(9);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("Minecraft");
        game.setEsrbRating("e for everyone");
        game.setDescription("this is a build stuff game");
        game.setPrice(new BigDecimal("15.99"));
        game.setStudio("Microsoft");
        game.setQuantity(10);

        game = gameDao.addGame(game);

        game = new Game();
        game.setTitle("Minecraft: Story Mode - Season Two");
        game.setEsrbRating("e for everyone");
        game.setDescription("building stuff game");
        game.setPrice(new BigDecimal("23.99"));
        game.setStudio("Tell Tale Games");
        game.setQuantity(1);

        game = gameDao.addGame(game);

        List<Game> gameList = gameDao.getGamesByEsrbRating("e for everyone");
        assertEquals(2, gameList.size());
    }
}
