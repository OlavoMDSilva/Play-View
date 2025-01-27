package com.example.play_view.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameServiceImpl gameService;

    @Autowired
    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameDTO> findAllGames() {
        return gameService.findAll();
    }

}
