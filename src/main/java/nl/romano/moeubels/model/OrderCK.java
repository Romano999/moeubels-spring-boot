package nl.romano.moeubels.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCK implements Serializable {
    private Actor actor;
    private Product product;
}
