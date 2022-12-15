package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateFavouriteRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateFavouriteRequest;
import nl.romano.moeubels.controller.v1.response.FavouriteResponse;
import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.FavouriteCK;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class FavouriteController {
    @Autowired
    private FavouriteDao favouriteDao;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @GetMapping(ApiRoutes.Favourite.Get)
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

    @PostMapping(ApiRoutes.Favourite.Create)
    public ResponseEntity<String> create(@RequestBody CreateFavouriteRequest favouriteRequest) {
        Favourite favourite = convertDtoToEntity(favouriteRequest);
        logger.info("Creating a favourite");
        favouriteDao.save(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Favourite.Update)
    public ResponseEntity<String> update(@RequestBody UpdateFavouriteRequest favouriteRequest) {
        Favourite favourite = convertDtoToEntity(favouriteRequest);
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

    private Favourite convertDtoToEntity(UpdateFavouriteRequest updateFavouriteRequest) {
        logger.info("Mapping update favourite request to favourite model");
        return modelMapper.map(updateFavouriteRequest, Favourite.class);
    }

    private Favourite convertDtoToEntity(CreateFavouriteRequest createFavouriteRequest) {
        logger.info("Mapping create favourite request to favourite model");
        return modelMapper.map(createFavouriteRequest, Favourite.class);
    }

    private FavouriteResponse convertEntityToDto(Favourite favourite) {
        logger.info("Mapping favourite model to favourite response");
        return modelMapper.map(favourite, FavouriteResponse.class);
    }
}
