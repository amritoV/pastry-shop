package it.amrito.pastryshopbe.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "ingrediente_tipologicadolce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SweetIngredientModel {

    @EmbeddedId
    private SweetIngredientId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingrediente")
    private IngredientModel ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("typologicalSweetId")
    @JoinColumn(name = "tipologicadolce")
    private TypologicalSweetModel typologicalSweet;

    @Column(name = "quantita")
    private Double quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SweetIngredientModel that = (SweetIngredientModel) o;
        return Objects.equals(ingredient, that.ingredient) && Objects.equals(typologicalSweet, that.typologicalSweet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, typologicalSweet);
    }
}
