package it.amrito.pastryshopbe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Tipologicadolce")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypologicalSweetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "typologicalsweet_generator")
    @SequenceGenerator(name = "typologicalsweet_generator", sequenceName = "tipologicadolce_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "prezzo")
    private Double price;

    @Column(name = "descrizione")
    private String description;

    @OneToMany(mappedBy = "typologicalSweet",  cascade = CascadeType.ALL)
    private Set<SweetIngredientModel> sweetIngredientModelSet = new HashSet<>();

    @OneToMany(mappedBy = "typologicalSweetModel")
    private Set<DashboardModel> dashboardModelSet = new HashSet<>();

    public void addSweetIngredientModel(SweetIngredientModel sweetIngredientModel){
        this.sweetIngredientModelSet.add(sweetIngredientModel);
        sweetIngredientModel.setTypologicalSweet(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypologicalSweetModel that = (TypologicalSweetModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
