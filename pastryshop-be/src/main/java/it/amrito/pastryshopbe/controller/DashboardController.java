package it.amrito.pastryshopbe.controller;

import it.amrito.pastryshopbe.dto.DashboardDto;
import it.amrito.pastryshopbe.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    DashboardService dashboardService;
    @GetMapping(("/get"))
    public ResponseEntity<List<DashboardDto>> getAllProducts(
            @PageableDefault @SortDefault(sort = "publicationDate", direction = Sort.Direction.DESC) Pageable pageable){
        logger.info("prrrodooooodpppppppppp");
        logger.warn("prrrodooooodpppppppppp");
        logger.error("prrrodooooodpppppppppp");
        logger.debug("prrrodooooodpppppppppp");
        logger.trace("prrrodooooodpppppppppp");
        return ResponseEntity.ok().body(dashboardService.findAllDashboards(pageable).getContent());
    }
}
