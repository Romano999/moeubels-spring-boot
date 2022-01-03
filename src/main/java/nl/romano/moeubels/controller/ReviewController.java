package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ReviewDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController implements CrudOperations<Review> {
    @Autowired
    private ReviewDao reviewDao;

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getById(@PathVariable UUID uuid) throws ResourceNotFoundException {
        Review review = reviewDao.getById(uuid)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id: " + uuid + "not found"));
        return Responses.ResponseEntityOk(review);
    }

    @Override
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Review review) {
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Review review) {
        return Responses.jsonOkResponseEntity();
    }

    @Override
    @DeleteMapping("/{}")
    public ResponseEntity<?> delete(UUID uuid) throws ResourceNotFoundException {
        return Responses.jsonOkResponseEntity();
    }
}
