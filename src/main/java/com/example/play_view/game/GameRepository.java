package com.example.play_view.game;

import com.example.play_view.company.CompanyEntity;
import com.example.play_view.publisher.PublisherEntity;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utility.SpecificationBuilder;

import java.sql.Date;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long>, JpaSpecificationExecutor<GameEntity> {
}
