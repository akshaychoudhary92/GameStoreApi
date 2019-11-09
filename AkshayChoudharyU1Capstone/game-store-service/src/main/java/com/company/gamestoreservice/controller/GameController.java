package com.company.gamestoreservice.controller;

import com.company.gamestoreservice.dto.Game;
import com.company.gamestoreservice.exception.NotFoundException;
import com.company.gamestoreservice.service.GameStoreServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameStoreServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game addGame(@RequestBody @Valid Game game) {
        return service.saveGame(game);
    }

    @GetMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable("gameId") int gameId) {
        Game game = service.findGame(gameId);
        if (game == null)
            throw new NotFoundException("Game could not be retrieved for id " + gameId);
        return game;
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable("gameId") int gameId) {
        service.removeGame(gameId);
    }

    @PutMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game updateGame(@PathVariable("gameId") int gameId, @RequestBody @Valid Game game) {
        if (game.getGameId() == 0)
            game.setGameId(gameId);
        if (gameId != game.getGameId()) {
            throw new IllegalArgumentException("Game ID on path must match the ID in the Game object");
        }
        game= service.updateGame(game);
        return game;
    }

    @GetMapping("/esrb/{esrb}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGameWithEsrb(@PathVariable("esrb") String esrb) {
        List<Game> game = service.findGamesByEsrbRating(esrb);
        if (game.size() == 0)
            throw new NotFoundException("No games available with " + esrb + " rating.");
        return game;
    }

    @GetMapping("/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGameByStudio(@PathVariable("studio") String studio) {
        List<Game> game = service.findGamesByStudio(studio);
        if (game.size() == 0)
            throw new NotFoundException("No games found for: " + studio + ".");
        return game;
    }

    @GetMapping("/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByTitle(@PathVariable("title") String title) {
        List<Game> games = service.findGamesByTitle(title);
        if (games.size() == 0)
            throw new NotFoundException("No games found for: " + title + ".");
        return games;
    }
}
