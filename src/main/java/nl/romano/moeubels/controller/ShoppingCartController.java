package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.model.ShoppingCartRequest;
import nl.romano.moeubels.projection.ShoppingCartProjection;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartDao shoppingCartDao;

    Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping("/{actorId}")
    public ResponseEntity<List<ShoppingCartProjection>> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        logger.info("Getting a shoppingCart of an actor with actor id " + actorId);
        List<ShoppingCart> shoppingCart = shoppingCartDao.getByActorId(actorId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new ShoppingCartNotFoundException("Actor with actor id: " + actorId + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(ShoppingCartProjection.toShoppingCartProjectionList(shoppingCart));
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody ShoppingCartRequest shoppingCartRequest) {
        logger.info("Creating a shoppingCart");
        shoppingCartDao.save(shoppingCartRequest);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody ShoppingCart shoppingCart) {
        logger.info("Updating a shoppingCart");
        shoppingCartDao.update(shoppingCart);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody ShoppingCartCK ck) throws ResourceNotFoundException {
        logger.info(
                "Deleting a shoppingCart with actor id " + ck.getActor() + " and product id " + ck.getProduct()
        );
        shoppingCartDao.delete(ck);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping("/payment/{actorId}")
    public ResponseEntity<String> paymentByActorId(@PathVariable UUID actorId) {
        logger.info("Paying a shoppingCart of an actor with actor id " + actorId);
        shoppingCartDao.paymentByActorId(actorId);
        return Responses.jsonOkResponseEntity();
    }
}
