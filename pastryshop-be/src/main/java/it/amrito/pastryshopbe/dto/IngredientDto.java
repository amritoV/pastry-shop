package it.amrito.pastryshopbe.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    @NotNull
    private Long id;
    private String name;

    @NotNull
    private Double quantity;
    private String unitMeasure;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDto that = (IngredientDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(quantity, that.quantity) && Objects.equals(unitMeasure, that.unitMeasure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, unitMeasure);
    }
}
