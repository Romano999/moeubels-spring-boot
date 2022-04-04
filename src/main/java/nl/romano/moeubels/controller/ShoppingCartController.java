package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
import nl.romano.moeubels.model.ShoppingCartRequest;
import nl.romano.moeubels.projection.ShoppingCartProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @GetMapping("/{actorId}")
    public ResponseEntity<List<ShoppingCartProjection>> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        List<ShoppingCart> shoppingCart = shoppingCartDao.getByActorId(actorId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Actor with id: " + actorId + "not found"));
        return Responses.ResponseEntityOk(ShoppingCartProjection.toShoppingCartProjectionList(shoppingCart));
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody ShoppingCartRequest shoppingCartRequest) {
        shoppingCartDao.save(shoppingCartRequest);
        return Responses.jsonOkResponseEntity();
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartDao.update(shoppingCart);
        return Responses.jsonOkResponseEntity();
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody ShoppingCartCK ck) throws ResourceNotFoundException {
        shoppingCartDao.delete(ck);
        return Responses.jsonOkResponseEntity();
    }
}
