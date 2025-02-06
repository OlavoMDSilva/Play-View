package com.example.play_view.genre;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public record GenreController(GenreServiceImpl genreService) {

    @Autowired
    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDTO> findAllCompanies(@RequestParam(name = "orderBy", defaultValue = "genreId", required = false) String order,
                                           @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                           @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                           @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return genreService.findAll(order, direction, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public List<GenreDTO> findGenreById(@PathVariable long id) {
        return genreService.findById(id);
    }

    @GetMapping("/filters")
    public List<GenreDTO> findCompaniesByAttribute(@RequestParam(name = "orderBy", defaultValue = "genreId", required = false) String order,
                                                   @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                                   @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                                   @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                   @RequestParam(name = "genre", defaultValue = "", required = false) String genre) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return genreService.findByAttribute(order, direction, pageNum, pageSize, genre);
    }

    @PostMapping
    public GenreDTO createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        return genreService.saveGenre(genreDTO);
    }

    @PutMapping("/{id}")
    public GenreDTO updateGenre(@RequestBody GenreDTO genreDTO, @PathVariable long id) {
        GenreDTO updatedGenre = new GenreDTO(id, genreDTO.genre());
        return genreService.saveGenre(updatedGenre);
    }

    @DeleteMapping("/{id}")
    public String deleteGenreById(@PathVariable long id) {
        genreService.deleteGenreById(id);
        return "Genre with Id: " + id + " successfully deleted";
    }

}
