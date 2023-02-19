package it.amrito.pastryshopbe.repository;

import it.amrito.pastryshopbe.model.Backoffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackofficeRepository extends JpaRepository<Backoffice, String> {

    Optional<Backoffice> findByNickname(String nickname);

}
