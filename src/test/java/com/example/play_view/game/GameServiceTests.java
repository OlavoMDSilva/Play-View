package com.example.play_view.game;

import com.example.play_view.validation.EntityNotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameDTOMapper gameDTOMapper;

    @Test
    public void gameService_findByAttribute_returnsGames() {
        List<String> companies = List.of("Software");
        List<String> publishers = List.of("Bandai Namco");
        List<String> genres = List.of("Souls Like");
        String title = "Souls 3";
        LocalDate startDate = LocalDate.parse("2016-01-01");
        LocalDate endDate = LocalDate.parse("2016-12-30");
        String restriction = "14+";
        Sort.Direction orderDir = Sort.Direction.ASC;
        String order = "title";
        int pageNum = 0, pageSize = 10;

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        GameEntity mockGameEntity = GameEntity.builder()
                .title("Dark Souls 3")
                .releaseDate(LocalDate.parse("2016-03-24"))
                .build();

        GameDTO mockGameDTO = GameDTO.builder()
                .title("Dark Souls 3")
                .releaseDate(LocalDate.parse("2016-03-24"))
                .build();

        Page<GameEntity> mockGameEntities = new PageImpl<>(List.of(mockGameEntity));

        when(gameRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(mockGameEntities);
        when(gameDTOMapper.apply(mockGameEntity)).thenReturn(mockGameDTO);

        List<GameDTO> result = gameService.findByAttribute(order, orderDir, pageNum, pageSize,
                companies, publishers, genres, title, startDate, endDate, restriction);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Dark Souls 3", result.get(0).title());

        verify(gameRepository, times(1)).findAll(Mockito.any(Specification.class), Mockito.eq(pageable));
    }

    @Test
    public void gameService_findByAttribute_returnsEntityNotFound() {
        List<String> companies = List.of("NonExistentCompany");
        List<String> publishers = List.of("NonExistentPublisher");
        List<String> genres = List.of("NonExistentGenre");
        String title = "Unknown Game";
        LocalDate startDate = null;
        LocalDate endDate = null;
        String restriction = null;
        Sort.Direction orderDir = Sort.Direction.ASC;
        String order = "title";
        int pageNum = 0, pageSize = 10;

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        Page<GameEntity> emptyPage = Page.empty();

        when(gameRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(emptyPage);

        EntityNotFound exception = Assertions.assertThrows(EntityNotFound.class, () ->
                gameService.findByAttribute(order, orderDir, pageNum, pageSize,
                        companies, publishers, genres, title, startDate, endDate, restriction)
        );

        Assertions.assertEquals("Game not found", exception.getMessage());

        verify(gameRepository, times(1)).findAll(Mockito.any(Specification.class), Mockito.eq(pageable));

    }

}
