package nl.romano.moeubels.dao;


import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public void delete(ShoppingCartCK ck) throws ResourceNotFoundException {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(ck)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart with ck: " + ck + "not found"));
        shoppingCartRepository.delete(shoppingCart);
    }
}
