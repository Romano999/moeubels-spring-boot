package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.controller.v1.request.create.CreateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateShoppingCartRequest;
import nl.romano.moeubels.controller.v1.response.ShoppingCartResponse;
import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.utils.Responses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

//    @GetMapping("/{actorId}")
//    public ResponseEntity<List<ShoppingCartProjection>> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
//        logger.info("Getting a shoppingCart of an actor with actor id " + actorId);
//        List<ShoppingCart> shoppingCart = shoppingCartDao.getByActorId(actorId)
//                .orElseThrow(() -> {
//                    ResourceNotFoundException exc = new ShoppingCartNotFoundException("Actor with actor id: " + actorId + " not found");
//                    logger.error(exc.getMessage());
//                    return exc;
//                });
//        return Responses.ResponseEntityOk(ShoppingCartProjection.toShoppingCartProjectionList(shoppingCart));
//    }

    @PostMapping(ApiRoutes.ShoppingCart.Create)
    public ResponseEntity<String> create(@RequestBody CreateShoppingCartRequest shoppingCartRequest) {
        ShoppingCart shoppingCart = convertDtoToEntity(shoppingCartRequest);
        logger.info("Creating a shoppingCart");
        shoppingCartDao.save(shoppingCart);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping(ApiRoutes.ShoppingCart.Update)
    public ResponseEntity<String> update(@RequestBody UpdateShoppingCartRequest shoppingCartRequest) {
        ShoppingCart shoppingCart = convertDtoToEntity(shoppingCartRequest);
        logger.info("Updating a shoppingCart");
        shoppingCartDao.update(shoppingCart);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping(ApiRoutes.ShoppingCart.Delete)
    public ResponseEntity<?> delete(@RequestBody ShoppingCartCK ck) throws ResourceNotFoundException {
        logger.info(
                "Deleting a shoppingCart with actor id " + ck.getActor() + " and product id " + ck.getProduct()
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
        return modelMapper.map(createShoppingCartRequest, ShoppingCart.class);
    }

    private ShoppingCartResponse convertEntityToDto(ShoppingCart shoppingCart) {
        logger.info("Mapping shopping cart model to shopping cart response");
        return modelMapper.map(shoppingCart, ShoppingCartResponse.class);
    }
}
