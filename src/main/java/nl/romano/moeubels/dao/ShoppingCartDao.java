package nl.romano.moeubels.dao;


import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ShoppingCartDao implements UserDetailsService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Optional<List<ShoppingCart>> getByActorId(UUID uuid) {
        List<ShoppingCart> cart = shoppingCartRepository.getByActorId(uuid);
        return Optional.of(cart);
    }

    public void save(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public void update(ShoppingCart shoppingCart) {
        shoppingCartRepository.save(shoppingCart);
    }

    public void delete(ShoppingCartCK ck) throws ResourceNotFoundException {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(ck)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart with ck: " + ck.toString() + "not found"));
        shoppingCartRepository.delete(shoppingCart);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
