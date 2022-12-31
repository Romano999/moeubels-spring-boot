package nl.romano.moeubels.exceptions;

import nl.romano.moeubels.utils.Responses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
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

    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<String> handleShoppingCartNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Shopping cart not found.");
    }

    @ExceptionHandler(FavouriteNotFoundException.class)
    public ResponseEntity<String> handleFavouriteNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Favourite not found.");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException() {
        return Responses.jsonNotFoundResponseEntityWithMessage("Order not found.");
    }
}
