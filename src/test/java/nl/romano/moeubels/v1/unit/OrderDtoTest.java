package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.controller.v1.request.create.CreateOrderRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateOrderRequest;
import nl.romano.moeubels.controller.v1.response.OrderResponse;
import nl.romano.moeubels.model.Order;
import nl.romano.moeubels.v1.utils.OrderObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class OrderDtoTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Test
    public void whenOrderModelToOrderResponseDto_thenCorrect() {
        // Arrange
        Order order = OrderObjectMother.genericOrder();

        // Act
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);

        // Assert
        Assertions.assertEquals(order.getActor().getActorId(), orderResponse.getActorId());
        Assertions.assertEquals(order.getProduct().getProductId(), orderResponse.getProductId());
        Assertions.assertEquals(order.getAmount(), orderResponse.getAmount());
    }

    @Test
    public void whenUpdateOrderRequestToOrderModel_thenCorrect() {
        // Arrange
        UpdateOrderRequest updateOrderRequest = OrderObjectMother.genericUpdateOrderRequest();

        // Act
        Order order = modelMapper.map(updateOrderRequest, Order.class);

        // Assert
        Assertions.assertEquals(updateOrderRequest.getActorId(), order.getActor().getActorId());
        Assertions.assertEquals(updateOrderRequest.getProductId(), order.getProduct().getProductId());
        Assertions.assertEquals(updateOrderRequest.getAmount(), order.getAmount());
    }

    @Test
    public void whenCreateOrderRequestToOrderModel_thenCorrect() {
        // Arrange
        CreateOrderRequest createOrderRequest = OrderObjectMother.genericCreateOrderRequest();

        // Act
        Order order = modelMapper.map(createOrderRequest, Order.class);

        // Assert
        Assertions.assertEquals(createOrderRequest.getActorId(), order.getActor().getActorId());
        Assertions.assertEquals(createOrderRequest.getProductId(), order.getProduct().getProductId());
        Assertions.assertEquals(createOrderRequest.getAmount(), order.getAmount());
    }
}
