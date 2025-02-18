package com.example.play_view.review;

import com.example.play_view.game.GameDTO;
import com.example.play_view.user.UserDTO;
import com.example.play_view.validation.EntityNotFound;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDTO> findAllReviews(@RequestParam(name = "orderBy", defaultValue = "reviewId", required = false) String order,
                                      @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                      @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return reviewService.findAll(order, direction, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public List<ReviewDTO> findReviewById(@PathVariable long id) {
        return reviewService.findById(id);
    }

    @GetMapping("/filters")
    public List<ReviewDTO> findReviewsByFilters(@RequestParam(name = "orderBy", defaultValue = "reviewId", required = false) String order,
                                                @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                                @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                                @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                @RequestParam(name = "games", defaultValue = "", required = false) List<String> games,
                                                @RequestParam(name = "users", defaultValue = "", required = false) List<String> users) {
        Sort.Direction orderDirection = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return reviewService.findByAttribute(order, orderDirection, pageNum, pageSize, games, users);
    }

    @PostMapping
    public ReviewDTO createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return reviewService.saveReview(reviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateGame(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable long id, Authentication authentication) {
        if (id <= 0) throw new RuntimeException("Id must greater than 0");
        if (!reviewService.existById(id)) throw new EntityNotFound("Game with ID: " + id + " not found");

        String email = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));

        if (!email.equalsIgnoreCase(reviewDTO.user().email()) && !isAdmin) throw new RuntimeException("You don't have permission for that");

        ReviewDTO updatedGame = new ReviewDTO(id, reviewDTO.game(), reviewDTO.user(), reviewDTO.review());
        return reviewService.saveReview(updatedGame);
    }

    @DeleteMapping("/{id}")
    public String deleteGameById(@PathVariable long id, Authentication authentication) {
        ReviewDTO reviewDTO = reviewService.findById(id).getFirst();

        String email = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));

        if (!email.equalsIgnoreCase(reviewDTO.user().email()) && !isAdmin) throw new RuntimeException("You don't have permission for that");

        reviewService.deleteReviewById(id);
        return "Review with Id: " + id + " successfully deleted";
    }

}
