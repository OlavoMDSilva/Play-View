package com.example.play_view.genre;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GenreService {
    boolean existById(long id);
    List<GenreDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize);
    List<GenreDTO> findByAttribute(String order, Sort.Direction orderDir,
                                  int pageNum, int pageSize,
                                  String genre);
    List<GenreDTO> findById(long id);
    @Transactional
    GenreDTO saveGenre(GenreDTO genreDTO);
    @Transactional
    void deleteGenreById(long id);
}
