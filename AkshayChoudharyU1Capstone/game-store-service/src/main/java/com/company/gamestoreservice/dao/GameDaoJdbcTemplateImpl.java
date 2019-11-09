package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class GameDaoJdbcTemplateImpl implements GameDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_GAME =
            "insert into game (title, esrb_rating, description, price, studio, quantity) values (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_GAME =
            "select * from game where game_id = ?";

    private static final String SELECT_ALL_GAMES =
            "select * from game";

    private static final String UPDATE_GAME =
            "update game set title = ?, esrb_rating = ?, description = ?, price = ?, studio = ?, quantity = ? where game_id = ?";

    private static final String DELETE_GAME =
            "delete from game where game_id = ?";

    private static final String SELECT_GAME_BY_STUDIO =
            "select * from game where studio = ?";

    private static final String SELECT_GAME_BY_TITLE =
            "select * from game where title like ?";

    private static final String SELECT_GAME_BY_ESRB =
            "select * from game where esrb_rating = ?";

    @Autowired
    public GameDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    @Transactional
    public Game addGame(Game game) {

        jdbcTemplate.update(INSERT_GAME,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        game.setGameId(id);

        return game;
    }

    @Override
    public Game getGame(int Id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_GAME, this::mapRowToGame, Id);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Game> getAllGames() {
        return jdbcTemplate.query(SELECT_ALL_GAMES, this::mapRowToGame);
    }

    @Override
    public Game updateGame(Game game) {
        jdbcTemplate.update(
                UPDATE_GAME,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity(),
                game.getGameId()
        );

        return game;
    }

    @Override
    public void deleteGame(int id) {
        jdbcTemplate.update(DELETE_GAME, id);
    }

    @Override
    public List<Game> getGamesByStudio(String studio) {
        try {
            return jdbcTemplate.query(SELECT_GAME_BY_STUDIO, this::mapRowToGame, studio);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Game> getGamesByTitle(String title) {
        return jdbcTemplate.query(SELECT_GAME_BY_TITLE, this::mapRowToGame,"%"+title+"%");
    }

    @Override
    public List<Game> getGamesByEsrbRating(String esrb) {
        try {
            return jdbcTemplate.query(SELECT_GAME_BY_ESRB, this::mapRowToGame, esrb);
        }catch (EmptyResultDataAccessException e){
            return null;
        }



    }
    private Game mapRowToGame(ResultSet rs, int rowNum) throws SQLException {

        Game game = new Game();
        game.setGameId(rs.getInt("game_id"));
        game.setTitle(rs.getString("title"));
        game.setEsrbRating(rs.getString("esrb_rating"));
        game.setDescription(rs.getString("description"));
        game.setPrice(rs.getBigDecimal("price"));
        game.setStudio(rs.getString("studio"));
        game.setQuantity(rs.getInt("quantity"));

        return game;


}
}
