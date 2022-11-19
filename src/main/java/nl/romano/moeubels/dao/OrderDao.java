package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.OrderNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.*;
import nl.romano.moeubels.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderDao {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private ProductDao productDao;

    public Optional<List<Order>> getByActorId(UUID id) {
        List<Order> orders = orderRepository.getByActorId(id);
        return Optional.of(orders);
    }

    public void save(OrderRequest orderRequest) {
        try {
            UUID actorId = orderRequest.getActorId();
            UUID productId = orderRequest.getProductId();

            Actor actor = actorDao.getById(actorId).orElseThrow();
            Product product = productDao.getById(productId).orElseThrow();
            int amount = orderRequest.getAmount();
            ZonedDateTime addedAt = orderRequest.getAddedAt();

            Order order = Order.builder()
                    .actor(actor)
                    .product(product)
                    .amount(amount)
                    .addedAt(addedAt)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Order order) {
        orderRepository.save(order);
    }

    public void delete(OrderCK ck) throws ResourceNotFoundException {
        Order order = orderRepository.findById(ck)
                .orElseThrow(() -> new OrderNotFoundException("Order with ck: " + ck + " not found."));
        orderRepository.delete(order);
    }
}
