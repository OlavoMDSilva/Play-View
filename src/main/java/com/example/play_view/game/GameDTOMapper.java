package com.example.play_view.game;

import java.util.function.Function;

public class GameDTOMapper implements Function<GameEntity, GameDTO> {
    @Override
    public GameDTO apply(GameEntity gameEntity) {
        return new GameDTO(
                gameEntity.getGameId(),
                gameEntity.getCodCompany(),
                gameEntity.getTitle(),
                gameEntity.getCoverUrl(),
                gameEntity.getReleaseDate(),
                gameEntity.getGameDescription(),
                gameEntity.getIndication()
        );
    }

    public GameEntity toEntity(GameDTO gameDTO) {
        GameEntity game = new GameEntity();

        if (game.getGameId() < 0) {
            game.setGameId(0);
        }else {
            game.setGameId(gameDTO.gameId());
        }

        game.setCodCompany(gameDTO.codCompany());
        game.setTitle(gameDTO.title());
        game.setCoverUrl(gameDTO.cover_url());
        game.setReleaseDate(gameDTO.releaseDate());
        game.setGameDescription(gameDTO.gameDescription());
        game.setIndication(gameDTO.indication());
        return game;
    }

}
