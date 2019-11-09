package com.company.gamestoreservice.dao;

import com.company.gamestoreservice.dto.Game;

import java.util.List;

public interface GameDao {
    Game addGame(Game game);

    Game getGame(int Id);

    List<Game> getAllGames();

    Game updateGame(Game game);

    void deleteGame(int id);

    List<Game> getGamesByStudio(String studio);

    List<Game> getGamesByTitle(String title);

    List<Game> getGamesByEsrbRating(String esrb);

}
