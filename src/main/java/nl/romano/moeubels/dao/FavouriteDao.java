package nl.romano.moeubels.dao;

import nl.romano.moeubels.exceptions.FavouriteNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.FavouriteCK;
import nl.romano.moeubels.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FavouriteDao {
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
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite with ck: " + ck + "not found"));
        favouriteRepository.delete(favourite);
    }
}
