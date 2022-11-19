package nl.romano.moeubels.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.romano.moeubels.model.ShoppingCart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartProjection implements Serializable {
    @JsonProperty("actor")
    private ActorPublicProjection actorPublicProjection;
    @JsonProperty("product")
    private ProductPublicProjection productPublicProjection;

    public ShoppingCartProjection(ActorPublicProjection actorPublicProjection, ProductPublicProjection productPublicProjection) {
        this.actorPublicProjection = actorPublicProjection;
        this.productPublicProjection = productPublicProjection;
    }

    public static ShoppingCartProjection toShoppingCartProjection(
            ShoppingCart shoppingCart
    ) {
        return new ShoppingCartProjection(
                ActorPublicProjection.toActorPublicProjection(shoppingCart.getActor()),
                ProductPublicProjection.toProductPublicProjection(shoppingCart.getProduct()));
    }

    public static List<ShoppingCartProjection> toShoppingCartProjectionList(
            List<ShoppingCart> shoppingCartList
    ) {
        ArrayList<ShoppingCartProjection> shoppingCartProjectionList = new ArrayList<>();
        shoppingCartList.forEach(
                shoppingCart -> shoppingCartProjectionList.add(ShoppingCartProjection.toShoppingCartProjection(shoppingCart))
        );
        return shoppingCartProjectionList;
    }
}

