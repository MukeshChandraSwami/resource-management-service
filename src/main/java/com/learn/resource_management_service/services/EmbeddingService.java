package com.learn.resource_management_service.services;

import com.learn.resource_management_service.constants.ResourceTypes;
import com.learn.resource_management_service.entities.ResourceEntity;
import com.learn.resource_management_service.requests.SearchRequest;
import com.learn.resource_management_service.responses.internal.EmbeddingResponse;
import com.learn.resource_management_service.responses.internal.SearchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static com.learn.resource_management_service.constants.AppConstants.RoutingConstants.ACT;
import static com.learn.resource_management_service.constants.AppConstants.RoutingConstants.SEARCH;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate;

    public EmbeddingService(@Qualifier("global-search-engine-rest-template") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmbeddingResponse createEmbedding(ResourceEntity entity, String text) {
        EmbeddingRequest request = new EmbeddingRequest(
                entity.getId(),
                ResourceTypes.fromValue(entity.getResourceType()),
                text
        );

        try {
            return restTemplate.postForObject(ACT + "/" + entity.getAccountMappingId(),
                    request, EmbeddingResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating embedding for resource: " + entity.getId(), e);
        }
    }

    public SearchResponse search( UUID accountMappingId, SearchRequest request) {
        try {
            return restTemplate.postForObject(ACT + "/" + accountMappingId + SEARCH, request, SearchResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while searching for: " + request.getQuery(), e);
        }
    }

    private record EmbeddingRequest(UUID resourceId, ResourceTypes resourceType, String content) { }
}
