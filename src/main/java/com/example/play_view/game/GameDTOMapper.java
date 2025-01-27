package com.example.play_view.game;

import com.example.play_view.company.CompanyDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GameDTOMapper implements Function<GameEntity, GameDTO> {
    CompanyDTOMapper companyDTOMapper = new CompanyDTOMapper();

    @Override
    public GameDTO apply(GameEntity gameEntity) {
        return new GameDTO(
                gameEntity.getGameId(),
                companyDTOMapper.apply(gameEntity.getCompany()),
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

        game.setCompany(companyDTOMapper.toEntity(gameDTO.company()));
        game.setTitle(gameDTO.title());
        game.setCoverUrl(gameDTO.cover_url());
        game.setReleaseDate(gameDTO.releaseDate());
        game.setGameDescription(gameDTO.gameDescription());
        game.setIndication(gameDTO.indication());
        return game;
    }

}
