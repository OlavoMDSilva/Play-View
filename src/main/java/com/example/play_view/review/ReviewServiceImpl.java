package com.example.play_view.review;

import com.example.play_view.game.GameEntity;
import com.example.play_view.game.GameRepository;
import com.example.play_view.user.UserEntity;
import com.example.play_view.user.UserRepository;
import com.example.play_view.validation.EntityNotFound;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utility.SpecificationBuilder;
import utility.SpecificationHelper;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ReviewDTOMapper reviewDTOMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewDTOMapper reviewDTOMapper,
                             GameRepository gameRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewDTOMapper = reviewDTOMapper;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean existById(long id) {
        return reviewRepository.existsById(id);
    }

    @Override
    public List<ReviewDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return reviewRepository.findAll(pageable).stream()
                .map(reviewDTOMapper)
                .toList();
    }

    @Override
    public List<ReviewDTO> findByAttribute(String order, Sort.Direction orderDir, int pageNum,
                                           int pageSize, List<String> games, List<String> users) {
        SpecificationHelper<ReviewEntity> specificationHelper = new SpecificationHelper<>();
        Specification<ReviewEntity> spec = new SpecificationBuilder<ReviewEntity>()
                .add((root, query, cb) ->
                                specificationHelper.createLikeSpecification(games, "game", "title", root, cb),
                        games != null && !games.isEmpty())
                .add((root, query, cb) ->
                                specificationHelper.createLikeSpecification(users, "user", "userName", root, cb),
                        users != null && !users.isEmpty())
                .build();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        List<ReviewDTO> reviewDTOS = reviewRepository.findAll(spec, pageable).stream().map(reviewDTOMapper).toList();

        if (reviewDTOS.isEmpty()) throw new EntityNotFound("Review not found");
        return reviewDTOS;
    }

    @Override
    public List<ReviewDTO> findById(long id) {
        if (!existById(id)) throw new EntityNotFound("Review with ID: " + id + " not found");
        return reviewRepository.findById(id).stream()
                .map(reviewDTOMapper)
                .toList();
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        GameEntity game = gameRepository.findByTitle(reviewDTO.game().title());
        UserEntity user = userRepository.findByEmail(reviewDTO.user().email());

        if (game == null) throw new EntityNotFound("Game " + reviewDTO.game().title() + " not found");
        if (user == null) throw new EntityNotFound("User " + reviewDTO.user().email() + " not found");

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .reviewId(reviewDTO.reviewId())
                .review(reviewDTO.review())
                .game(game)
                .user(user)
                .build();

        ReviewEntity savedReview = reviewRepository.save(reviewEntity);

        return reviewDTOMapper.apply(savedReview);
    }

    @Override
    public void deleteReviewById(long id) {
        if (!existById(id)) throw new EntityNotFound("Review with ID: " + id + " not found");
        reviewRepository.deleteById(id);
    }
}
