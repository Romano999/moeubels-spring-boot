package nl.romano.moeubels.dao;


import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.*;
import nl.romano.moeubels.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShoppingCartDao {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private ProductDao productDao;

    public Optional<List<ShoppingCart>> getByActorId(UUID id) {
        List<ShoppingCart> cart = shoppingCartRepository.getByActorId(id);
        return Optional.of(cart);
    }

    public void paymentByActorId(UUID id) {
        List<ShoppingCart> cart = shoppingCartRepository.getByActorId(id);
        shoppingCartRepository.deleteAll(cart);
    }

    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public void save(ShoppingCartRequest shoppingCartRequest) {
        try {
            UUID actorId = shoppingCartRequest.getActorId();
            UUID productId = shoppingCartRequest.getProductId();

            Actor actor = actorDao.getById(actorId).orElseThrow();
            Product product = productDao.getById(productId).orElseThrow();
            int amount = shoppingCartRequest.getAmount();
            ZonedDateTime addedAt = shoppingCartRequest.getAddedAt();

            ShoppingCart shoppingCart = ShoppingCart.builder()
                    .actor(actor)
                    .product(product)
                    .amount(amount)
                    .addedAt(addedAt)
                    .build();

            shoppingCartRepository.save(shoppingCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public void delete(ShoppingCartCK ck) throws ResourceNotFoundException {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(ck)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart with ck: " + ck + "not found"));
        shoppingCartRepository.delete(shoppingCart);
    }
}
