package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ReviewDao;
import nl.romano.moeubels.exceptions.ProductNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.model.Review;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController implements CrudOperations<Review> {
    @Autowired
    private ReviewDao reviewDao;

    Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Getting a Review with id " + id);
        Review review = reviewDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ReviewNotFoundException("Review with id: " + id + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(review);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Review review) {
        logger.info("Creating a Review");
        reviewDao.save(review);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Review review) {
        logger.info("Updating a Review");
        reviewDao.update(review);
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a Review with id " + id);
        return Responses.jsonOkResponseEntity();
    }
}
