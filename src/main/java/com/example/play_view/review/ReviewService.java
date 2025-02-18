package com.example.play_view.review;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ReviewService {
    boolean existById(long id);

    List<ReviewDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize);

    List<ReviewDTO> findByAttribute(String order, Sort.Direction orderDir, int pageNum,
                                    int pageSize, List<String> games, List<String> users);

    List<ReviewDTO> findById(long id);

    @Transactional
    ReviewDTO saveReview(ReviewDTO reviewDTO);

    @Transactional
    void deleteReviewById(long id);

}
