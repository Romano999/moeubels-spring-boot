package nl.romano.moeubels.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.romano.moeubels.model.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderProjection implements Serializable {
    @JsonProperty("actor")
    private ActorPublicProjection actorPublicProjection;
    @JsonProperty("product")
    private ProductPublicProjection productPublicProjection;

    public OrderProjection(ActorPublicProjection actorPublicProjection, ProductPublicProjection productPublicProjection) {
        this.actorPublicProjection = actorPublicProjection;
        this.productPublicProjection = productPublicProjection;
    }

    public static OrderProjection toOrderProjection(
            Order order
    ) {
        return new OrderProjection(
                ActorPublicProjection.toActorPublicProjection(order.getActor()),
                ProductPublicProjection.toProductPublicProjection(order.getProduct()));
    }

    public static List<OrderProjection> toOrderProjectionList(
            List<Order> orders
    ) {
        ArrayList<OrderProjection> orderProjectionList = new ArrayList<>();
        orders.forEach(
                order -> orderProjectionList.add(OrderProjection.toOrderProjection(order))
        );
        return orderProjectionList;
    }
}
