package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateFavouriteRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateFavouriteRequest;
import nl.romano.moeubels.controller.v1.response.FavouriteResponse;
import nl.romano.moeubels.dao.FavouriteDao;
import nl.romano.moeubels.exceptions.ActorNotFoundException;
import nl.romano.moeubels.exceptions.FavouriteNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.utils.JsonConverter;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class FavouriteController {
    @Autowired
    private FavouriteDao favouriteDao;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @GetMapping(ApiRoutes.Favourite.Get)
    public ResponseEntity<FavouriteResponse> getById(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Received following favourite id '" + id + "'");
        logger.info("Getting a favourite by id '" + id + "'");
        Favourite favourite = favouriteDao.getById(id)
            .orElseThrow(() -> {
                ResourceNotFoundException exc = new FavouriteNotFoundException("Favourite with favourite id " + id + " not found");
                logger.error(exc.getMessage());
                return exc;
            });

        logger.info("Favourite with id '" + favourite.getFavouriteId() + "' found");
        FavouriteResponse favouriteResponse = convertEntityToDto(favourite);
        logger.info("Returning following data: " + JsonConverter.asJsonString(favouriteResponse));
        return Responses.ResponseEntityOk(favouriteResponse);
    }

    @GetMapping(ApiRoutes.Favourite.GetByActorId)
    public ResponseEntity<List<FavouriteResponse>> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        logger.info("Received following actor id '" + actorId + "'");
        logger.info("Getting all favourite(s) of an actor with actor id " + actorId);
        List<Favourite> favourites = favouriteDao.getByActorId(actorId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ActorNotFoundException("Actor with actor id " + actorId + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Returning favourite list of size " + favourites.size());
        List<FavouriteResponse> favouriteResponses = convertEntityListToDtoList(favourites);
        return Responses.ResponseEntityOk(favouriteResponses);
    }

    @PostMapping(ApiRoutes.Favourite.Create)
    public ResponseEntity<String> create(@RequestBody CreateFavouriteRequest favouriteRequest) {
        logger.info("Received following create favourite request '" + JsonConverter.asJsonString(favouriteRequest) + "'");
        Favourite favourite = convertDtoToEntity(favouriteRequest);
        logger.info("Creating a favourite");
        favouriteDao.save(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.Favourite.Update)
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody UpdateFavouriteRequest favouriteRequest) {
        logger.info("Received following update favourite request '" + JsonConverter.asJsonString(favouriteRequest) + "'");
        Favourite favourite = convertDtoToEntity(favouriteRequest);
        favourite.setFavouriteId(id);
        logger.info("Updating a favourite");
        favouriteDao.update(favourite);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.Favourite.Delete)
    public ResponseEntity<?> delete(@PathVariable UUID id) throws ResourceNotFoundException {
        logger.info("Deleting a favourite with id '" + id + "'");
        favouriteDao.delete(id);
        return Responses.jsonOkResponseEntity();
    }

    private Favourite convertDtoToEntity(UpdateFavouriteRequest updateFavouriteRequest) {
        logger.info("Mapping update favourite request to favourite model");
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(updateFavouriteRequest, Favourite.class);
    }

    private Favourite convertDtoToEntity(CreateFavouriteRequest createFavouriteRequest) {
        logger.info("Mapping create favourite request to favourite model");
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(createFavouriteRequest, Favourite.class);
    }

    private FavouriteResponse convertEntityToDto(Favourite favourite) {
        logger.info("Mapping favourite model to favourite response");
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(favourite, FavouriteResponse.class);
    }

    private List<FavouriteResponse> convertEntityListToDtoList(List<Favourite> favourites) {
        logger.info("Mapping a favourite list to a favourite response list");
        ArrayList<FavouriteResponse> favouriteResponses = new ArrayList<>();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        favourites.forEach(favourite -> favouriteResponses.add(convertEntityToDto(favourite)));
        logger.info("Done with mapping a favourite list to a favourite response list");
        return favouriteResponses;
    }
}
