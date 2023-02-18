package it.amrito.pastryshopbe.repository;

import it.amrito.pastryshopbe.model.IngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface IngredientRepository extends JpaRepository<IngredientModel, Long> {

    Set<IngredientModel> findByIdIn(Set<Long> idSet);
}
