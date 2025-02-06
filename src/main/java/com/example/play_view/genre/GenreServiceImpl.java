package com.example.play_view.genre;

import com.example.play_view.validation.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utility.SpecificationBuilder;
import utility.SpecificationHelper;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreDTOMapper genreDTOMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreDTOMapper genreDTOMapper) {
        this.genreRepository = genreRepository;
        this.genreDTOMapper = genreDTOMapper;
    }

    @Override
    public boolean existById(long id) {
        return genreRepository.existsById(id);
    }

    @Override
    public List<GenreDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return genreRepository.findAll(pageable).stream()
                .map(genreDTOMapper)
                .toList();
    }

    @Override
    public List<GenreDTO> findByAttribute(String order, Sort.Direction orderDir, int pageNum, int pageSize, String genre) {

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        SpecificationHelper<GenreEntity> specificationHelper = new SpecificationHelper<>();
        Specification<GenreEntity> spec = new SpecificationBuilder<GenreEntity>()
                .add((root, query, cb) ->
                        cb.like(root.get("genre"), "%" + genre + "%"), genre != null && !genre.isEmpty())
                .build();

        List<GenreDTO> genreDTOS = genreRepository.findAll(spec, pageable).stream()
                .map(genreDTOMapper)
                .toList();

        if (genreDTOS.isEmpty()) throw new EntityNotFound("Genre: " + genre + " not found");
        return genreDTOS;
    }

    @Override
    public List<GenreDTO> findById(long id) {
        if (!existById(id)) throw new EntityNotFound("Genre with ID: " + id + " not found");
        return genreRepository.findById(id).stream()
                .map(genreDTOMapper)
                .toList();
    }

    @Override
    public GenreDTO saveGenre(GenreDTO genreDTO) {
        GenreEntity genre = genreDTOMapper.toEntity(genreDTO);
        GenreEntity savedGenre = genreRepository.save(genre);
        return genreDTOMapper.apply(savedGenre);
    }

    @Override
    public void deleteGenreById(long id) {
        if (!existById(id)) throw new EntityNotFound("Genre with ID: " + id + " not found");
        genreRepository.deleteById(id);
    }
}
