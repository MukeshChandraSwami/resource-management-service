package com.learn.resource_management_service.controllers;

import com.learn.resource_management_service.responses.HealthResponse;
import com.learn.resource_management_service.responses.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.learn.resource_management_service.constants.AppConstants.RoutingConstants.STATUS;

@RestController
@RequestMapping(STATUS)
public class HealthController {

    @GetMapping
    public Response checkHealth() {
        return HealthResponse.builder()
                .success(true)
                .responseCode(200)
                .status("UP")
                .build();
    }
}
