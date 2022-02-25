package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.FavouriteNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.*;
import nl.romano.moeubels.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FavouriteDao implements UserDetailsService {
    @Autowired
    private FavouriteRepository favouriteRepository;

    public Optional<List<Favourite>> getByActorId(UUID uuid) {
        List<Favourite> favourite = favouriteRepository.getByActorId(uuid);
        return Optional.of(favourite);
    }

    public void save(Favourite shoppingCart) {
        favouriteRepository.save(shoppingCart);
    }

    public void update(Favourite shoppingCart) {
        favouriteRepository.save(shoppingCart);
    }

    public void delete(FavouriteCK ck) throws ResourceNotFoundException {
        Favourite favourite = favouriteRepository.findById(ck)
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite with ck: " + ck.toString() + "not found"));
        favouriteRepository.delete(favourite);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
