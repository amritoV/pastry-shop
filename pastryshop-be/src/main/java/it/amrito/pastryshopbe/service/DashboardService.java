package it.amrito.pastryshopbe.service;

import it.amrito.pastryshopbe.config.ApplicationConfig;
import it.amrito.pastryshopbe.dto.DashboardDto;
import it.amrito.pastryshopbe.dto.TypologicalSweetLiteDto;
import it.amrito.pastryshopbe.model.DashboardModel;
import it.amrito.pastryshopbe.model.TypologicalSweetModel;
import it.amrito.pastryshopbe.repository.DashboardRepository;
import it.amrito.pastryshopbe.utils.Mapper;
import it.amrito.pastryshopbe.utils.UtilMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private DashboardRepository dashboardRepository;


    public List<DashboardDto> findAllDashboards(){
        LocalDate expirationDate = LocalDate.now().minusDays(applicationConfig.getProductValidityDays());
        List<DashboardModel> dashboardModelList = dashboardRepository.trova(expirationDate);

        List<DashboardDto> dashboardDtoList = new ArrayList<>();
        for(DashboardModel dashboardModel: dashboardModelList){
            TypologicalSweetModel typologicalSweetModel = dashboardModel.getTypologicalSweetModel();
            TypologicalSweetLiteDto typologicalSweetLiteDto = mapper.mapLite(dashboardModel.getTypologicalSweetModel());
            Double effectivePrice = UtilMethods.calculatePrice(typologicalSweetModel.getPrice(), dashboardModel.getProductionDate(), applicationConfig);
            typologicalSweetLiteDto.setPrice(effectivePrice);
            DashboardDto dashboardDto = mapper.map(typologicalSweetLiteDto, dashboardModel);
            dashboardDtoList.add(dashboardDto);
        }

        return dashboardDtoList;
    }


}
