package com.example.play_view.genre;

import com.example.play_view.publisher.PublisherDTO;
import com.example.play_view.publisher.PublisherEntity;

import java.util.function.Function;

public class GenreDTOMapper implements Function<GenreEntity, GenreDTO> {
    @Override
    public GenreDTO apply(GenreEntity genreEntity) {
        return new GenreDTO(
                genreEntity.getGenreId(),
                genreEntity.getGenre()
        );
    }

    public GenreEntity toEntity(GenreDTO genreDTO) {
        GenreEntity genre = new GenreEntity();

        if (genre.getGenreId() < 0) {
            genre.setGenreId(0);
        }else {
            genre.setGenreId(genreDTO.genreId());
        }

        genre.setGenre(genreDTO.genre());
        return genre;
    }
}
