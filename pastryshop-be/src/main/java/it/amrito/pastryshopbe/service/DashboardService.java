package it.amrito.pastryshopbe.service;

import it.amrito.pastryshopbe.config.ApplicationConfig;
import it.amrito.pastryshopbe.dto.DashboardDto;
import it.amrito.pastryshopbe.dto.DashboardLiteDto;
import it.amrito.pastryshopbe.model.DashboardModel;
import it.amrito.pastryshopbe.model.TypologicalSweetModel;
import it.amrito.pastryshopbe.repository.DashboardRepository;
import it.amrito.pastryshopbe.repository.TypologicalRepository;
import it.amrito.pastryshopbe.utils.Mapper;
import it.amrito.pastryshopbe.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DashboardService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private TypologicalRepository typologicalRepository;


    public Page<DashboardDto> findAllDashboards(Pageable pageable){
        LocalDate expirationDate = LocalDate.now().minusDays(applicationConfig.getProductValidityDays());
        return dashboardRepository.trova(expirationDate, pageable)
                .map(x -> mapper.map(x, UtilMethods.calculatePrice(x.getTypologicalSweetModel().getPrice(), x.getProductionDate(), applicationConfig)));
    }

    public Long save(DashboardLiteDto dashboardLiteDto, String nickname){

        Optional<TypologicalSweetModel> typologicalSweetModel = typologicalRepository.findById(dashboardLiteDto.getTypologicalSweetId());
        if(typologicalSweetModel.isEmpty()) return -1l; //TODO gestire errore
        DashboardModel dashboardModelToSave = mapper.map(dashboardLiteDto, typologicalSweetModel.get(), nickname);
        DashboardModel dashboardModelSaved = dashboardRepository.save(dashboardModelToSave);
        return dashboardModelSaved.getId();
    }
}
