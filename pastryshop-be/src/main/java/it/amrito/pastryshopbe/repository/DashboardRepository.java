package it.amrito.pastryshopbe.repository;

import it.amrito.pastryshopbe.model.DashboardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DashboardRepository extends JpaRepository<DashboardModel, Long> {


    @Query(value = "SELECT d FROM DashboardModel d WHERE d.productionDate >= :expirationDate")
    Page<DashboardModel> trova(@Param("expirationDate") LocalDate expirationDate, Pageable pageable);

}
