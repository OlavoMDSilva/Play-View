package com.example.play_view.review;

import com.example.play_view.game.GameDTOMapper;
import com.example.play_view.user.UserDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReviewDTOMapper implements Function<ReviewEntity, ReviewDTO> {

    private final GameDTOMapper gameDTOMapper = new GameDTOMapper();
    private final UserDTOMapper userDTOMapper = new UserDTOMapper();

    @Override
    public ReviewDTO apply(ReviewEntity reviewEntity) {
        return new ReviewDTO(
                reviewEntity.getReviewId(),
                gameDTOMapper.apply(reviewEntity.getGame()),
                userDTOMapper.apply(reviewEntity.getUser()),
                reviewEntity.getReview()
        );
    }
}
