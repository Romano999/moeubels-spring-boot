package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.exceptions.FavouriteNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.FavouriteCK;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("favourites")
public class FavouriteController {
    @Autowired
    private FavouriteDao favouriteDao;

    @GetMapping("/{actorId}")
    public ResponseEntity<?> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        List<Favourite> favourites = favouriteDao.getByActorId(actorId)
                .orElseThrow(() -> new FavouriteNotFoundException("Favourite with id: " + actorId + "not found"));
        return Responses.ResponseEntityOk(favourites);
    }


    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Favourite favourite) {
        favouriteDao.save(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Favourite favourite) {
        favouriteDao.update(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody FavouriteCK ck) throws ResourceNotFoundException {
        favouriteDao.delete(ck);
        return Responses.jsonOkResponseEntity();
    }
}
