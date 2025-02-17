package com.example.play_view.game;

import com.example.play_view.validation.EntityNotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/games")
public record GameController(GameServiceImpl gameService) {

    @Autowired
    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameDTO> findAllGames(@RequestParam(name = "orderBy", defaultValue = "gameId", required = false) String order,
                                      @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                      @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction orderDirection = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return gameService.findAll(order, orderDirection, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public List<GameDTO> findGameById(@PathVariable long id) {
        return gameService.findById(id);
    }

    @GetMapping("/filters")
    public List<GameDTO> findAllGamesByFilters(@RequestParam(name = "orderBy", defaultValue = "gameId", required = false) String order,
                                               @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                               @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                               @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                               @RequestParam(name = "company", defaultValue = "", required = false) List<String> companies,
                                               @RequestParam(name = "publishers", defaultValue = "", required = false) List<String> publishers,
                                               @RequestParam(name = "genres", defaultValue = "", required = false) List<String> genres,
                                               @RequestParam(name = "title", defaultValue = "", required = false) String title,
                                               @RequestParam(name = "startDate", defaultValue = "1000-01-01", required = false) String startDate,
                                               @RequestParam(name = "endDate", defaultValue = "9999-12-31", required = false) String endDate
    ) {
        Sort.Direction orderDirection = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return gameService.findByAttribute(order, orderDirection, pageNum, pageSize, companies, publishers, genres, title,
                LocalDate.parse(startDate), LocalDate.parse(endDate), "");
    }

    @PostMapping
    public GameDTO createGame(@Valid @RequestBody GameDTO gameDTO) {
        return gameService.saveGame(gameDTO);
    }

    @PutMapping("/{id}")
    public GameDTO updateGame(@Valid @RequestBody GameDTO gameDTO, @PathVariable long id) {
        if (id <= 0) throw new RuntimeException("Id must greater than 0");
        if (!gameService.existById(id)) throw new EntityNotFound("Game with ID: " + id + " not found");

        GameDTO updatedGame = new GameDTO(id, gameDTO.title(), gameDTO.company(), gameDTO.publishers(), gameDTO.genres(),
                gameDTO.coverUrl(), gameDTO.releaseDate(), gameDTO.description(), gameDTO.restriction());

        return gameService.saveGame(updatedGame);
    }

    @DeleteMapping("/{id}")
    public String deleteGameById(@PathVariable long id) {
        gameService.deleteGameById(id);
        return "Game with Id: " + id + " successfully deleted";
    }

}
