package com.example.play_view.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    @Override
    @Query(value = """
            select games.*, GROUP_CONCAT(pub.publisher_id order by publisher_id) AS publisher_ids,
            GROUP_CONCAT(pub.publisher_name order by publisher_id) AS publisher_names from games
            join games_publishers on games_publishers.cod_game = game_id
            join publishers pub on games_publishers.cod_publisher = publisher_id
            group by game_id;""", nativeQuery = true)
    List<GameEntity> findAll();

}
