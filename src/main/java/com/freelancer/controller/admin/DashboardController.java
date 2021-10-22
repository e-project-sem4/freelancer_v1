package com.freelancer.controller.admin;

import com.freelancer.model.ResponseObject;
import com.freelancer.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @GetMapping()
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public HashMap<Integer, Long> showData() {
        return dashboardService.getCountDashboard();
    }
}
