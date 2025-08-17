package com.learn.resource_management_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.resource_management_service.requests.SaveResourceRequest;
import com.learn.resource_management_service.requests.SearchRequest;
import com.learn.resource_management_service.responses.Response;
import com.learn.resource_management_service.services.ResourceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.learn.resource_management_service.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.resource_management_service.constants.AppConstants.RoutingConstants.ACT_ID;
import static com.learn.resource_management_service.constants.AppConstants.RoutingConstants.SEARCH;

@RestController
@RequestMapping(ACT + ACT_ID)
public class ResourceController {

    private final ResourceService resourceService;
    private final ObjectMapper objectMapper;

    public ResourceController(ResourceService resourceService,
                              ObjectMapper objectMapper) {
        this.resourceService = resourceService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response createSearchData(
            @PathVariable UUID acct_id,
            @RequestPart("file") MultipartFile file,
            @RequestPart("request") String request) throws Exception {

        return resourceService.save(acct_id, objectMapper.readValue(request, SaveResourceRequest.class), file);
    }

    @PostMapping(SEARCH)
    public Response search(@PathVariable UUID acct_id,
                           @RequestBody SearchRequest request) {

        return resourceService.search(acct_id, request);
    }
}
