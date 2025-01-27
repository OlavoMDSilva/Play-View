package com.example.play_view.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService{

    private GameRepository gameRepository;
    private GameDTOMapper gameDTOMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameDTOMapper gameDTOMapper) {
        this.gameRepository = gameRepository;
        this.gameDTOMapper = gameDTOMapper;
    }

    @Override
    public List<GameDTO> findAll() {
        return gameRepository.findAll().stream()
                .map(gameDTOMapper)
                .toList();
    }

}
