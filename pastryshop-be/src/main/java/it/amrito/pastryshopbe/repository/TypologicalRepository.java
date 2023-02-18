package it.amrito.pastryshopbe.repository;

import it.amrito.pastryshopbe.model.TypologicalSweetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypologicalRepository extends JpaRepository<TypologicalSweetModel, Long> {


    Optional<TypologicalSweetModel> findByNameIgnoreCase(String name);
}
