package com.example.play_view.game;

import com.example.play_view.company.CompanyEntity;
import com.example.play_view.publisher.PublisherEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameRepositoryTests {

    @Mock
    private GameRepository gameRepository;

    @Test
    public void gameRepository_findAllGames_returnGames() {

        CompanyEntity company = Mockito.mock(CompanyEntity.class);
        when(company.getCompanyId()).thenReturn(1L);
        when(company.getCompanyName()).thenReturn("FromSoftware");

        PublisherEntity publisher = Mockito.mock(PublisherEntity.class);
        when(publisher.getPublisherId()).thenReturn(1L);
        when(publisher.getPublisherName()).thenReturn("FromSoftware, Inc");

        Set<PublisherEntity> publishers = new HashSet<>();
        publishers.add(publisher);

        GameEntity game1 = new GameEntity();
        game1.setGameId(0);
        game1.setCompany(company);
        game1.setPublishers(publishers);
        game1.setTitle("Dark Souls");
        game1.setCoverUrl("http://");
        game1.setReleaseDate(LocalDate.parse("2011-9-11"));
        game1.setDescription("HARD");
        game1.setRestriction("14+");

        GameEntity game2 = new GameEntity();
        game2.setGameId(0);
        game2.setCompany(company);
        game2.setPublishers(publishers);
        game2.setTitle("Dark Souls 2");
        game2.setCoverUrl("http://");
        game2.setReleaseDate(LocalDate.parse("2014-3-22"));
        game2.setDescription("HARD 2");
        game2.setRestriction("14+");

        when(gameRepository.findAll()).thenReturn(List.of(game1, game2));

        List<GameEntity> games = gameRepository.findAll();

        Assertions.assertNotNull(games);
        Assertions.assertEquals(2, games.size());

    }

}
