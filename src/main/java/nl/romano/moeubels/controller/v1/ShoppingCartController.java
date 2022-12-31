package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.response.ShoppingCartResponse;
import nl.romano.moeubels.dao.ActorDao;
import nl.romano.moeubels.dao.ProductDao;
import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.utils.JsonConverter;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private ProductDao productDao;

    private final ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping(ApiRoutes.ShoppingCart.GetByActorId)
    public ResponseEntity<List<ShoppingCartResponse>> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        logger.info("Received following actor id '" + actorId + "'");
        logger.info("Getting a shoppingCart of an actor with actor id " + actorId);
        List<ShoppingCart> shoppingCart = shoppingCartDao.getByActorId(actorId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ShoppingCartNotFoundException("Actor with actor id: " + actorId + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });

        logger.info("Returning shopping cart of size " + shoppingCart.size());
        List<ShoppingCartResponse> shoppingCartResponses = convertEntityListToDtoList(shoppingCart);
        return Responses.ResponseEntityOk(shoppingCartResponses);
    }

    @PostMapping(ApiRoutes.ShoppingCart.Create)
    public ResponseEntity<String> create(@RequestBody CreateShoppingCartRequest shoppingCartRequest) {
        logger.info("Received following create shopping cart request '" + JsonConverter.asJsonString(shoppingCartRequest) + "'");
        ShoppingCart shoppingCart = convertDtoToEntity(shoppingCartRequest);
        logger.info("Creating a shoppingCart");
        shoppingCartDao.save(shoppingCart);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.ShoppingCart.Update)
    public ResponseEntity<String> update(@RequestBody UpdateShoppingCartRequest shoppingCartRequest) {
        logger.info("Received following update shopping cart request '" + JsonConverter.asJsonString(shoppingCartRequest) + "'");
        ShoppingCart shoppingCart = convertDtoToEntity(shoppingCartRequest);
        logger.info("Updating a shoppingCart");
        shoppingCartDao.update(shoppingCart);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.ShoppingCart.Delete)
    public ResponseEntity<?> delete(@RequestBody ShoppingCartCK ck) throws ResourceNotFoundException {
        logger.info(
                "Deleting a shoppingCart with actor id '" + ck.getActor() + "' and product id '" + ck.getProduct() + "'"
        );
        shoppingCartDao.delete(ck);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.ShoppingCart.Payment)
    public ResponseEntity<String> paymentByActorId(@PathVariable UUID actorId) {
        logger.info("Paying a shoppingCart of an actor with actor id " + actorId);
        shoppingCartDao.paymentByActorId(actorId);
        return Responses.jsonOkResponseEntity();
    }

    private ShoppingCart convertDtoToEntity(UpdateShoppingCartRequest updateShoppingCartRequest) {
        logger.info("Mapping update shoppingCart request to shoppingCart model");
        return modelMapper.map(updateShoppingCartRequest, ShoppingCart.class);
    }

    private ShoppingCart convertDtoToEntity(CreateShoppingCartRequest createShoppingCartRequest) {
        logger.info("Mapping create shopping cart request to shopping cart model");
        ShoppingCart item = modelMapper.map(createShoppingCartRequest, ShoppingCart.class);
        item.setActor(actorDao.getById(createShoppingCartRequest.getActorId()).orElseThrow());
        item.setProduct(productDao.getById(createShoppingCartRequest.getProductId()).orElseThrow());
        item.setCreatedAt(ZonedDateTime.now());
        item.setModifiedAt(ZonedDateTime.now());
        return item;
    }

    private ShoppingCartResponse convertEntityToDto(ShoppingCart shoppingCart) {
        logger.info("Mapping shopping cart model to shopping cart response");
        logger.info("Mapping the following model: " + JsonConverter.asJsonString(shoppingCart));
        return modelMapper.map(shoppingCart, ShoppingCartResponse.class);
    }

    private List<ShoppingCartResponse> convertEntityListToDtoList(List<ShoppingCart> shoppingCartList) {
        logger.info("Mapping a shopping cart list to a shopping cart response list");
        ArrayList<ShoppingCartResponse> shoppingCartResponses = new ArrayList<>();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        shoppingCartList.forEach(item -> shoppingCartResponses.add(convertEntityToDto(item)));
        logger.info("Done with mapping a shopping cart list to a shopping cart response list");
        return shoppingCartResponses;
    }
}
