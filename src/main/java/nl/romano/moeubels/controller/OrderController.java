package nl.romano.moeubels.controller;

import nl.romano.moeubels.dao.OrderDao;
import nl.romano.moeubels.exceptions.OrderNotFoundException;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import nl.romano.moeubels.model.Order;
import nl.romano.moeubels.model.OrderRequest;
import nl.romano.moeubels.projection.OrderProjection;
import nl.romano.moeubels.utils.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
        logger.info("Getting a order of an actor with actor id " + actorId);
        List<Order> orders = orderDao.getByActorId(actorId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exc = new OrderNotFoundException("Actor with actor id: " + actorId + " not found");
                    logger.error(exc.getMessage());
                    return exc;
                });
        return Responses.ResponseEntityOk(OrderProjection.toOrderProjectionList(orders));
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody OrderRequest orderRequest) {
        return null;
    }


    @PutMapping()
    public ResponseEntity<String> update(Order generic) {
        return null;
    }


    @DeleteMapping()
    public ResponseEntity<?> delete(UUID uuid) throws ResourceNotFoundException {
        return null;
    }
}
