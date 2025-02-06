package com.example.play_view.game;

import com.example.play_view.company.CompanyEntity;
import com.example.play_view.company.CompanyRepository;
import com.example.play_view.genre.GenreDTO;
import com.example.play_view.genre.GenreEntity;
import com.example.play_view.genre.GenreRepository;
import com.example.play_view.publisher.PublisherDTO;
import com.example.play_view.publisher.PublisherEntity;
import com.example.play_view.publisher.PublisherRepository;
import com.example.play_view.validation.EntityNotFound;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utility.SpecificationBuilder;
import utility.SpecificationHelper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final CompanyRepository companyRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genreRepository;
    private final GameDTOMapper gameDTOMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, CompanyRepository companyRepository, PublisherRepository publisherRepository, GenreRepository genreRepository, GameDTOMapper gameDTOMapper) {
        this.gameRepository = gameRepository;
        this.companyRepository = companyRepository;
        this.publisherRepository = publisherRepository;
        this.genreRepository = genreRepository;
        this.gameDTOMapper = gameDTOMapper;
    }

    @Override
    public boolean existById(long id) {
        return gameRepository.existsById(id);
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

        List<GameDTO> gameDTOS = gameRepository.findAll(spec, pageable).stream().map(gameDTOMapper).toList();

        if (gameDTOS.isEmpty()) throw new EntityNotFound("Game not found");
        return gameDTOS;
    }

    @Override
    public List<GameDTO> findById(long id) {
        if (!existById(id)) throw new EntityNotFound("Game with ID: " + id + " not found");
        return gameRepository.findById(id).stream()
                .map(gameDTOMapper)
                .toList();
    }

    @Override
    public GameDTO saveGame(GameDTO gameDTO) {
        CompanyEntity company = companyRepository.findByCompanyName(gameDTO.company().companyName());

        if (company == null) throw new EntityNotFound("Company: " + gameDTO.company().companyName() + " not found");

        Set<PublisherEntity> publishers = setPublishers(gameDTO);

        Set<GenreEntity> genres = setGenres(gameDTO);

        GameEntity game = gameDTOMapper.toEntity(gameDTO);

        game.setCompany(company);
        game.setPublishers(publishers);
        game.setGenres(genres);

        GameEntity savedGame = gameRepository.save(game);

        return gameDTOMapper.apply(savedGame);
    }

    @Override
    public void deleteGameById(long id) {
        if (!existById(id)) throw new EntityNotFound("Game with ID: " + id + " not found");
        gameRepository.deleteById(id);
    }

    private Set<PublisherEntity> setPublishers(GameDTO gameDTO) {
        Set<String> publisherNames = gameDTO.publishers().stream()
                .map(PublisherDTO::publisherName)
                .collect(Collectors.toSet());

        Set<PublisherEntity> publishers = new HashSet<>(publisherRepository.findAllByPublisherNameIn(publisherNames));

        Set<String> foundPublishersNames = publishers.stream()
                        .map(PublisherEntity::getPublisherName)
                        .collect(Collectors.toSet());

        publisherNames.removeAll(foundPublishersNames);

        if (!publisherNames.isEmpty()) throw new EntityNotFound("Publishers not found: " + String.join(", ", publisherNames));
        return publishers;
    }

    private Set<GenreEntity> setGenres(GameDTO gameDTO) {
        Set<String> genreNames = gameDTO.genres().stream()
                .map(GenreDTO::genre)
                .collect(Collectors.toSet());

        Set<GenreEntity> genres = new HashSet<>(genreRepository.findAllByGenreIn(genreNames));

        Set<String> foundGenreNames = genres.stream()
                .map(GenreEntity::getGenre)
                .collect(Collectors.toSet());

        genreNames.removeAll(foundGenreNames);

        if (!genreNames.isEmpty()) throw new EntityNotFound("Genres not found: " + String.join(", ", genreNames));
        return genres;
    }

}
