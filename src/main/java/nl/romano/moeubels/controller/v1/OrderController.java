package nl.romano.moeubels.controller.v1;

import nl.romano.moeubels.contract.v1.ApiRoutes;
import nl.romano.moeubels.dao.OrderDao;
import nl.romano.moeubels.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping(ApiRoutes.Order.Get)
    public ResponseEntity<?> getByActorId(@PathVariable UUID actorId) throws ResourceNotFoundException {
//        logger.info("Getting an order of an actor with actor id " + actorId);
//        List<Order> orders = orderDao.getByActorId(actorId)
//                .orElseThrow(() -> {
//                    ResourceNotFoundException exc = new OrderNotFoundException("Actor with actor id: " + actorId + " not found");
//                    logger.error(exc.getMessage());
//                    return exc;
//                });
//        return Responses.ResponseEntityOk(OrderProjection.toOrderProjectionList(orders));
        throw new NotImplementedException();
    }

//    @PostMapping()
//    public ResponseEntity<String> create(@RequestBody OrderRequest orderRequest) {
//        return null;
//    }
//
//
//    @PutMapping()
//    public ResponseEntity<String> update(Order generic) {
//        return null;
//    }
//
//
//    @DeleteMapping()
//    public ResponseEntity<?> delete(UUID uuid) throws ResourceNotFoundException {
//        return null;
//    }
}
