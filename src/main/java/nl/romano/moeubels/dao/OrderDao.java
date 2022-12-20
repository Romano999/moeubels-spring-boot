package nl.romano.moeubels.dao;

import nl.romano.moeubels.model.Order;
import nl.romano.moeubels.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public void update(Order order) {
        orderRepository.save(order);
    }

}
