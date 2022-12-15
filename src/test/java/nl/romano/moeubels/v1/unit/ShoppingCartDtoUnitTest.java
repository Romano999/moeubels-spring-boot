package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.controller.v1.request.create.CreateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.response.ShoppingCartResponse;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.v1.utils.ShoppingCartObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ShoppingCartDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenShoppingCartModelToShoppingCartResponseDto_thenCorrect() {
        // Arrange
        ShoppingCart shoppingCart = ShoppingCartObjectMother.genericShoppingCart();

        // Act
        ShoppingCartResponse shoppingCartResponse = modelMapper.map(shoppingCart, ShoppingCartResponse.class);

        // Assert
        Assertions.assertEquals(shoppingCart.getActor().getActorId(), shoppingCartResponse.getActorId());
        Assertions.assertEquals(shoppingCart.getProduct().getProductId(), shoppingCartResponse.getProductId());
        Assertions.assertEquals(shoppingCart.getAmount(), shoppingCartResponse.getAmount());
    }

    @Test
    public void whenUpdateShoppingCartRequestToShoppingCartModel_thenCorrect() {
        // Arrange
        UpdateShoppingCartRequest updateShoppingCartRequest = ShoppingCartObjectMother.genericUpdateShoppingCartRequest();

        // Act
        ShoppingCart shoppingCart = modelMapper.map(updateShoppingCartRequest, ShoppingCart.class);

        // Assert
        Assertions.assertEquals(updateShoppingCartRequest.getActorId(), shoppingCart.getActor().getActorId());
        Assertions.assertEquals(updateShoppingCartRequest.getProductId(), shoppingCart.getProduct().getProductId());
        Assertions.assertEquals(updateShoppingCartRequest.getAmount(), shoppingCart.getAmount());
    }

    @Test
    public void whenCreateShoppingCartRequestToShoppingCartModel_thenCorrect() {
        // Arrange
        CreateShoppingCartRequest createShoppingCartRequest = ShoppingCartObjectMother.genericCreateShoppingCartRequest();

        // Act
        ShoppingCart shoppingCart = modelMapper.map(createShoppingCartRequest, ShoppingCart.class);

        // Assert
        Assertions.assertEquals(createShoppingCartRequest.getActorId(), shoppingCart.getActor().getActorId());
        Assertions.assertEquals(createShoppingCartRequest.getProductId(), shoppingCart.getProduct().getProductId());
        Assertions.assertEquals(createShoppingCartRequest.getAmount(), shoppingCart.getAmount());
    }
}
