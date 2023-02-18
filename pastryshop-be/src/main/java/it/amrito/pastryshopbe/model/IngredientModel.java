package it.amrito.pastryshopbe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ingrediente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_generator")
    @SequenceGenerator(name = "ingredient_generator", sequenceName = "ingrediente_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(name = "nome")
    private String name;

    @Column(name = "unitamisura")
    private String unitMeasure;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private Set<SweetIngredientModel> sweetIngredientModelSet = new HashSet<>();

    public void addSweetIngredientModel(SweetIngredientModel sweetIngredientModel){
        this.sweetIngredientModelSet.add(sweetIngredientModel);
        sweetIngredientModel.setIngredient(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientModel that = (IngredientModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
