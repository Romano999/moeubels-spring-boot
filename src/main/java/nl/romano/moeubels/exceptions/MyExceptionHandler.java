package nl.romano.moeubels.exceptions;

import nl.romano.moeubels.controller.Responses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException() {
        return Responses.jsonNotFoundResponseEntity();
    }

    @ExceptionHandler(ActorNotFoundException.class)
    public ResponseEntity<String> handleActorNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Actor not found.");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Category not found.");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Product not found.");
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<String> handleReviewNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Review not found.");
    }
}
