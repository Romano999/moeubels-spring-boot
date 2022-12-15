package nl.romano.moeubels.v1.unit;

import nl.romano.moeubels.controller.v1.request.create.CreateFavouriteRequest;
import nl.romano.moeubels.controller.v1.request.update.UpdateFavouriteRequest;
import nl.romano.moeubels.controller.v1.response.FavouriteResponse;
import nl.romano.moeubels.model.Favourite;
import nl.romano.moeubels.v1.utils.FavouriteObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class FavouriteDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenFavouriteModelToFavouriteResponseDto_thenCorrect() {
        // Arrange
        Favourite favourite = FavouriteObjectMother.genericFavourite();

        // Act
        FavouriteResponse favouriteResponse = modelMapper.map(favourite, FavouriteResponse.class);

        // Assert
        Assertions.assertEquals(favourite.getActor().getActorId(), favouriteResponse.getActorId());
        Assertions.assertEquals(favourite.getProduct().getProductId(), favouriteResponse.getProductId());
    }

    @Test
    public void whenUpdateFavouriteRequestToFavouriteModel_thenCorrect() {
        // Arrange
        UpdateFavouriteRequest updateFavoriteRequest = FavouriteObjectMother.genericUpdateFavouriteRequest();

        // Act
        Favourite favourite = modelMapper.map(updateFavoriteRequest, Favourite.class);

        // Assert
        Assertions.assertEquals(updateFavoriteRequest.getActorId(), favourite.getActor().getActorId());
        Assertions.assertEquals(updateFavoriteRequest.getProductId(), favourite.getProduct().getProductId());
    }

    @Test
    public void whenCreateFavouriteRequestToFavouriteModel_thenCorrect() {
        // Arrange
        CreateFavouriteRequest createFavoriteRequest = FavouriteObjectMother.genericCreateFavouriteRequest();

        // Act
        Favourite favourite = modelMapper.map(createFavoriteRequest, Favourite.class);

        // Assert
        Assertions.assertEquals(createFavoriteRequest.getActorId(), favourite.getActor().getActorId());
        Assertions.assertEquals(createFavoriteRequest.getProductId(), favourite.getProduct().getProductId());
    }

}
