package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.FavouriteNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.FavouriteCK;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @GetMapping("/{actorId}")
    public ResponseEntity<?> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        logger.info("Getting all favourite(s) of an actor with actor id " + actorId);
        List<Favourite> favourites = favouriteDao.getByActorId(actorId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ActorNotFoundException("Actor with actor id " + actorId + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(favourites);
    }


    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Favourite favourite) {
        logger.info("Creating a favourite");
        favouriteDao.save(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Favourite favourite) {
        logger.info("Updating a favourite");
        favouriteDao.update(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody FavouriteCK ck) throws ResourceNotFoundException {
        logger.info(
                "Deleting a favourite with actor id " + ck.getActor().getActorId() +
                " and product id " + ck.getProduct().getProductId()
        );
        favouriteDao.delete(ck);
        return Responses.jsonOkResponseEntity();
    }
}
