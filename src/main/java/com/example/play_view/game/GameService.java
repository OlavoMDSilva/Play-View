package com.example.play_view.game;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface GameService {
    boolean existById(long id);
    List<GameDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize);
    List<GameDTO> findByAttribute(String order, Sort.Direction orderDir,
                                  int pageNum, int pageSize,
                                  List<String> companies, List<String> publishers, List<String> genres,
                                  String title, LocalDate startDate, LocalDate endDate, String indication);
    List<GameDTO> findById(long id);
    @Transactional
    GameDTO saveGame(GameDTO gameDTO);
    @Transactional
    void deleteGameById(long id);
}
