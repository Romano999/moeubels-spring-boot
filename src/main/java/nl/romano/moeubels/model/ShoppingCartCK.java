package nl.romano.moeubels.model;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartCK implements Serializable {
    private Actor actor;
    private Product product;
}
