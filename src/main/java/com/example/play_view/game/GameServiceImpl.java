package com.example.play_view.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utility.SpecificationBuilder;
import utility.SpecificationHelper;

import java.time.LocalDate;
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
    public boolean existById(long id) {
        return false;
    }

    @Override
    public List<GameDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize) {

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return gameRepository.findAll(pageable).stream()
                .map(gameDTOMapper)
                .toList();
    }

    @Override
    public List<GameDTO> findByAttribute(String order, Sort.Direction orderDir,
                                         int pageNum, int pageSize,
                                         List<String> companies, List<String> publishers, List<String> genres,
                                         String title, LocalDate startDate, LocalDate endDate, String restriction) {

        SpecificationHelper<GameEntity> specificationHelper = new SpecificationHelper<>();
        Specification<GameEntity> spec = new SpecificationBuilder<GameEntity>()
                .add((root, query, cb) ->
                        specificationHelper.createLikeSpecification(companies, "company", "companyName", root, cb),
                        companies != null && !companies.isEmpty())
                .add((root, query, cb) ->
                        specificationHelper.createLikeSpecification(publishers, "publishers", "publisherName", root, cb),
                        publishers != null && !publishers.isEmpty())
                .add((root, query, cb) ->
                        specificationHelper.createLikeSpecification(genres, "genres", "genre", root, cb),
                        genres != null && !genres.isEmpty())
                .add((root, query, cb) ->
                        cb.like(root.get("title"), "%" + title + "%"), title != null && !title.isEmpty())
                .add((root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get("releaseDate"), startDate), startDate != null)
                .add((root, query, cb) ->
                        cb.lessThanOrEqualTo(root.get("releaseDate"), endDate), endDate != null)
                .add((root, query, cb) ->
                        cb.like(root.get("restriction"), "%" + restriction + "%"), restriction != null && !restriction.isEmpty())
                .build();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return gameRepository.findAll(spec, pageable).stream().map(gameDTOMapper).toList();
    }

    @Override
    public List<GameDTO> findById(long id) {
        return gameRepository.findById(id).stream()
                .map(gameDTOMapper)
                .toList();
    }

    @Override
    public GameDTO saveGame(GameDTO gameDTO) {
        GameEntity game = gameDTOMapper.toEntity(gameDTO);
        GameEntity savedGame = gameRepository.save(game);
        return gameDTOMapper.apply(savedGame);
    }

    @Override
    public void deleteGameById(long id) {
        gameRepository.deleteById(id);
    }
}
