package it.amrito.pastryshopbe.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SweetIngredientId implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long ingredientId;
    private Long typologicalSweetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SweetIngredientId that = (SweetIngredientId) o;
        return Objects.equals(ingredientId, that.ingredientId) && Objects.equals(typologicalSweetId, that.typologicalSweetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, typologicalSweetId);
    }
}
