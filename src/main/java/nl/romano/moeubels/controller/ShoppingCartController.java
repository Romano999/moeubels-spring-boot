package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.ShoppingCartDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.exceptions.ShoppingCartNotFoundException;
import nl.romano.moeubels.model.Category;
import nl.romano.moeubels.model.ShoppingCart;
import nl.romano.moeubels.model.ShoppingCartCK;
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

    @GetMapping("/{actorUuid}")
    public ResponseEntity<?> getByActorId(@PathVariable UUID actorUuid) throws ResourceNotFoundException {
        List<ShoppingCart> shoppingCart = shoppingCartDao.getByActorId(actorUuid)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Actor with id: " + actorUuid + "not found"));
        return Responses.ResponseEntityOk(shoppingCart);
    }

    @GetMapping()
    public ResponseEntity<?> getById(@RequestBody ShoppingCartCK ck) throws ResourceNotFoundException {
        ShoppingCart shoppingCart = shoppingCartDao.getById(ck)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart with ck: " + ck.toString() + "not found"));
        return Responses.ResponseEntityOk(shoppingCart);
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartDao.save(shoppingCart);
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
