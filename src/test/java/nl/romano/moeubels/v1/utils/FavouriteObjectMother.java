package nl.romano.moeubels.v1.utils;

import nl.romano.moeubels.controller.v1.request.create.CreateFavouriteRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateFavouriteRequest;
import nl.romano.moeubels.controller.v1.response.FavouriteResponse;
import nl.romano.moeubels.model.Actor;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.model.Product;
import org.modelmapper.ModelMapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FavouriteObjectMother {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Favourite genericFavourite() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return Favourite.builder()
            .favouriteId(UUID.randomUUID())
            .actor(actor)
            .product(product)
            .addedAt(ZonedDateTime.now())
            .build();
    }

    public static UpdateFavouriteRequest genericUpdateFavouriteRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return UpdateFavouriteRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .build();
    }

    public static CreateFavouriteRequest genericCreateFavouriteRequest() {
        Actor actor = ActorObjectMother.genericActor();
        Product product = ProductObjectMother.genericProduct();

        return CreateFavouriteRequest.builder()
            .actorId(actor.getActorId())
            .productId(product.getProductId())
            .build();
    }

    public static Favourite genericFavouriteWithActorId(UUID actorId) {
        Actor actor = ActorObjectMother.genericActorWithActorId(actorId);
        Product product = ProductObjectMother.genericProduct();

        return Favourite.builder()
            .favouriteId(UUID.randomUUID())
            .actor(actor)
            .product(product)
            .addedAt(ZonedDateTime.now())
            .build();
    }

    public static List<Favourite> genericFavouriteListWithActorId(UUID actorId) {
        List<Favourite> favourites = new ArrayList<>();
        int length = 5;

        for (int i = 0; i < length; i++) {
            Favourite favourite = FavouriteObjectMother.genericFavouriteWithActorId(actorId);
            favourites.add(favourite);
        }

        return favourites;
    }

    public static List<FavouriteResponse> genericFavouriteResponseListFromFavouriteList(
        List<Favourite> favourites
    ) {
        ArrayList<FavouriteResponse> favouriteResponses = new ArrayList<>();
        favourites.forEach(favourite -> favouriteResponses.add(convertEntityToDto(favourite)));
        return favouriteResponses;
    }

    private static FavouriteResponse convertEntityToDto(Favourite favourite) {
        return modelMapper.map(favourite, FavouriteResponse.class);
    }
}
