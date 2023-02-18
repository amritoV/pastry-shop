package it.amrito.pastryshopbe.controller;

import it.amrito.pastryshopbe.dto.DashboardDto;
import it.amrito.pastryshopbe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;
    @GetMapping("")
    public ResponseEntity<List<DashboardDto>> getAllProducts(){
        return ResponseEntity.ok().body(dashboardService.findAllDashboards());
    }
}
