package com.example.play_view.game;

import com.example.play_view.company.CompanyDTOMapper;
import com.example.play_view.genre.GenreDTOMapper;
import com.example.play_view.publisher.PublisherDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameDTOMapper implements Function<GameEntity, GameDTO> {
    CompanyDTOMapper companyDTOMapper = new CompanyDTOMapper();
    PublisherDTOMapper publisherDTOMapper = new PublisherDTOMapper();
    GenreDTOMapper genreDTOMapper = new GenreDTOMapper();

    @Override
    public GameDTO apply(GameEntity gameEntity) {
        return new GameDTO(
                gameEntity.getGameId(),
                gameEntity.getTitle(),
                companyDTOMapper.apply(gameEntity.getCompany()),
                gameEntity.getPublishers().stream()
                        .map(publisherDTOMapper)
                        .collect(Collectors.toSet()),
                gameEntity.getGenres().stream()
                        .map(genreDTOMapper)
                        .collect(Collectors.toSet()),
                gameEntity.getCoverUrl(),
                gameEntity.getReleaseDate(),
                gameEntity.getDescription(),
                gameEntity.getRestriction()
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
        game.setPublishers(gameDTO.publishers().stream()
                .map(publisherDTOMapper::toEntity)
                .collect(Collectors.toSet()));
        game.setGenres(gameDTO.genres().stream()
                .map(genreDTOMapper::toEntity)
                .collect(Collectors.toSet()));
        game.setTitle(gameDTO.title());
        game.setCoverUrl(gameDTO.coverUrl());
        game.setReleaseDate(gameDTO.releaseDate());
        game.setDescription(gameDTO.description());
        game.setRestriction(gameDTO.restriction());
        return game;
    }

}
