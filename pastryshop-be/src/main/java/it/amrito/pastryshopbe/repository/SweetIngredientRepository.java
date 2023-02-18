package it.amrito.pastryshopbe.repository;

import it.amrito.pastryshopbe.model.SweetIngredientId;
import it.amrito.pastryshopbe.model.SweetIngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SweetIngredientRepository extends JpaRepository<SweetIngredientModel, SweetIngredientId> {
    Set<SweetIngredientModel> deleteByIdTypologicalSweetId(Long typologicalSweetId);

    Set<SweetIngredientModel> deleteByIdIn(Set<SweetIngredientId> idSet);
    Set<SweetIngredientModel> findByIdTypologicalSweetId(Long typologicalSweetId);
}
