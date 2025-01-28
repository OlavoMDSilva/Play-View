package com.example.play_view.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
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
    public List<GameDTO> findAllGames(@RequestParam(name = "orderBy", defaultValue = "gameId", required = false) String order,
                                      @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                      @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction orderDirection = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return gameService.findAll(order, orderDirection, pageNum, pageSize);
    }

    @GetMapping("/filters")
    public List<GameDTO> findAllGamesByFilters(@RequestParam(name = "orderBy", defaultValue = "gameId", required = false) String order,
                                               @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                               @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                               @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                               @RequestParam(name = "companies", defaultValue = "", required = false) List<String> companies,
                                               @RequestParam(name = "title", defaultValue = "", required = false) String title
    ) {
        Sort.Direction orderDirection = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return gameService.findByAttribute(order, orderDirection, pageNum, pageSize, companies, List.of(), List.of(), title,
                LocalDate.parse("2011-09-22"), LocalDate.parse("2016-10-27"), "");
    }

}
