package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateReviewRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateReviewRequest;
import nl.romano.moeubels.controller.v1.response.ReviewResponse;
import nl.romano.moeubels.dao.ReviewDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ReviewNotFoundException;
import nl.romano.moeubels.model.Review;
import nl.romano.moeubels.utils.JsonConverter;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ReviewController {
    @Autowired
    private ReviewDao reviewDao;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping(ApiRoutes.Review.Get)
    public ResponseEntity<ReviewResponse> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Received following favourite id '" + id + "'");
        logger.info("Getting a review by review id '" + id + "'");
        Review review = reviewDao.getById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ReviewNotFoundException("Review with review id '" + id + "' not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Review with id '" + review.getReviewId() + "' found");
        ReviewResponse reviewResponse = convertEntityToDto(review);
        logger.info("Returning following data: " + JsonConverter.asJsonString(reviewResponse));
        return Responses.ResponseEntityOk(reviewResponse);
    }

    @GetMapping(ApiRoutes.Review.GetByProductId)
    public ResponseEntity<List<ReviewResponse>> getProductId(@PathVariable UUID productId) throws ResourceNotFoundException {
        logger.info("Received following product id '" + productId + "'");
        logger.info("Getting a review by product id " + productId);
        List<Review> reviews = reviewDao.getByProductId(productId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ReviewNotFoundException("Review with product id " + productId + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Returning " + reviews.size() + " reviews");
        List<ReviewResponse> reviewResponses = convertEntityListToDtoList(reviews);
        return Responses.ResponseEntityOk(reviewResponses);
    }

    @PostMapping(ApiRoutes.Review.Create)
    public ResponseEntity<String> create(@RequestBody CreateReviewRequest reviewRequest) {
        logger.info("Received following create review request '" + JsonConverter.asJsonString(reviewRequest) + "'");
        Review review = convertDtoToEntity(reviewRequest);
        logger.info("Creating a review");
        reviewDao.save(review);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Review.Update)
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody UpdateReviewRequest reviewRequest) {
        logger.info("Received following update review request '" + JsonConverter.asJsonString(reviewRequest) + "'");
        Review review = convertDtoToEntity(reviewRequest);
        review.setReviewId(id);
        logger.info("Updating a review");
        reviewDao.update(review);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Review.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a review with review id '" + id + "'");
        reviewDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    private Review convertDtoToEntity(UpdateReviewRequest updateReviewRequest) {
        logger.info("Mapping update review request to review model");
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(updateReviewRequest, Review.class);
    }

    private Review convertDtoToEntity(CreateReviewRequest createReviewRequest) {
        logger.info("Mapping create review request to review model");
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Review review = modelMapper.map(createReviewRequest, Review.class);
        review.setCreatedAt(ZonedDateTime.now());
        review.setModifiedAt(ZonedDateTime.now());
        return review;
    }

    private ReviewResponse convertEntityToDto(Review review) {
        logger.info("Mapping review model to review response");
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(review, ReviewResponse.class);
    }

    private List<ReviewResponse> convertEntityListToDtoList(List<Review> reviews) {
        logger.info("Mapping a review list to a review response list");
        ArrayList<ReviewResponse> reviewResponses = new ArrayList<>();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        reviews.forEach(review -> reviewResponses.add(convertEntityToDto(review)));
        logger.info("Done with mapping a review list to a review response list");
        return reviewResponses;
    }
}
