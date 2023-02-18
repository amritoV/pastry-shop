package it.amrito.pastryshopbe.repository;

import it.amrito.pastryshopbe.model.Backoffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackofficeRepository extends JpaRepository<Backoffice, String> {
}
