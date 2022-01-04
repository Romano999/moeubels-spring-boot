package nl.romano.moeubels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.romano.moeubels.dao.Dao;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.dao.ReviewDao;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Product;
import nl.romano.moeubels.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private ReviewDao reviewDao;
    private MockMvc mvc;
    private Review review;


    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.review = Review.builder()
                .reviewId(UUID.randomUUID())
                .addedAt(ZonedDateTime.now())
                .rating(5)
                .build();
    }

    @Test
    void getById() throws Exception {
        Review testReview = this.review;
        UUID reviewId = testReview.getReviewId();
        //String actorJsonString = new JSONObject(testActor).toString();
        String requestPath = String.format("/reviews/%s", reviewId.toString());

        given(reviewDao.getById(reviewId)).willReturn(Optional.of(testReview));

        this.mvc.perform(MockMvcRequestBuilders
                        .get(requestPath).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(testReview)));
    }

    @Test
    void create() throws Exception {
        Review testReview = this.review;

        this.mvc.perform(MockMvcRequestBuilders.put("/reviews")
                        .secure(true).content(asJsonString(testReview)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update() throws Exception {
        Review testReview = this.review;

        this.mvc.perform(MockMvcRequestBuilders.post("/reviews")
                        .secure(true).content(asJsonString(testReview)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Review testReview = this.review;
        UUID reviewId = testReview.getReviewId();
        String requestPath = String.format("/reviews/%s", reviewId.toString());

        this.mvc.perform(MockMvcRequestBuilders.delete(requestPath, testReview).secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk());
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