package nl.romano.moeubels.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteCK implements Serializable {
    private Actor actor;
    private Product product;
}
