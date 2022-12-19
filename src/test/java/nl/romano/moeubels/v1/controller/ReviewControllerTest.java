package nl.romano.moeubels.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.ReviewController;
import nl.romano.moeubels.controller.v1.request.create.CreateReviewRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateReviewRequest;
import nl.romano.moeubels.controller.v1.response.ReviewResponse;
import nl.romano.moeubels.dao.ReviewDao;
import nl.romano.moeubels.model.Review;
import nl.romano.moeubels.v1.utils.ReviewObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private ReviewDao reviewDao;
    @Autowired
    private MockMvc mvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Test
    void getById() throws Exception {
        // Arrange
        Review review = ReviewObjectMother.genericReview();
        ReviewResponse reviewResponse = modelMapper.map(review, ReviewResponse.class);
        UUID reviewId = review.getReviewId();
        String requestPath = ApiRoutes.Review.Get.replace("{id}", reviewId.toString());

        given(reviewDao.getById(reviewId)).willReturn(Optional.of(review));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(reviewResponse)));
    }

    @Test
    void getByProductId() throws Exception {
        // Arrange
        UUID productId = UUID.randomUUID();
        List<Review> reviews = ReviewObjectMother.genericReviewListWithProductId(productId);
        List<ReviewResponse> reviewResponses =
            ReviewObjectMother.genericReviewResponseListFromReviewList(reviews);
        String requestPath = ApiRoutes.Review.GetByProductId.replace("{productId}", productId.toString());

        given(reviewDao.getByProductId(productId)).willReturn(Optional.of(reviews));

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(requestPath)
            .secure(true)
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(asJsonString(reviewResponses)));
    }

    @Test
    void create() throws Exception {
        // Arrange
        CreateReviewRequest reviewRequest = ReviewObjectMother.genericCreateReviewRequest();
        String requestPath = ApiRoutes.Review.Create;

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(requestPath)
            .secure(true)
            .content(asJsonString(reviewRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        // Arrange
        UUID reviewId = UUID.randomUUID();
        UpdateReviewRequest reviewRequest = ReviewObjectMother.genericUpdateReviewRequest();
        String requestPath = ApiRoutes.Review.Update.replace("{id}", reviewId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(requestPath)
            .secure(true)
            .content(asJsonString(reviewRequest))
            .contentType("application/json"));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        // Arrange
        Review review = ReviewObjectMother.genericReview();
        UUID reviewId = review.getReviewId();
        String requestPath = ApiRoutes.Review.Delete.replace("{id}", reviewId.toString());

        // Act
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, review).secure(true));

        // Assert
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}