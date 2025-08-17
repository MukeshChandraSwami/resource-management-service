package com.learn.resource_management_service.services;

import com.learn.resource_management_service.entities.ResourceEntity;
import com.learn.resource_management_service.models.ImageAnalysis;
import com.learn.resource_management_service.models.SearchResult;
import com.learn.resource_management_service.requests.SearchRequest;
import com.learn.resource_management_service.responses.internal.EmbeddingResponse;
import com.learn.resource_management_service.responses.internal.SearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.learn.resource_management_service.utils.EmbeddingUtils.toEmbeddingInput;

@Service
public class AiService {

    private final MediaDetailsExtractorService mediaDetailsExtractorService;
    private final EmbeddingService embeddingService;

    public AiService(MediaDetailsExtractorService mediaDetailsExtractorService,
                     EmbeddingService embeddingService) {
        this.mediaDetailsExtractorService = mediaDetailsExtractorService;
        this.embeddingService = embeddingService;

    }

    public void updateMediaDetailsForSimilaritySearch(MultipartFile file, ResourceEntity resourceEntity) {
        ImageAnalysis imageAnalysis = mediaDetailsExtractorService.extractMediaDetails(file);
        imageAnalysis.setMetadata(ImageAnalysis.Metadata.builder()
                .resourceId(resourceEntity.getId().toString())
                .resourceType(resourceEntity.getResourceType())
                .accountMappingId(resourceEntity.getAccountMappingId().toString())
                .build());
        String s = toEmbeddingInput(imageAnalysis);
        EmbeddingResponse embedding = embeddingService.createEmbedding(resourceEntity, s);
        if (embedding.isSuccess()) {
            System.out.println("Embedding created successfully for resource: " + resourceEntity.getId());
        } else {
            System.err.println("Failed to create embedding: " + embedding.getResponseMsg());
        }
    }

    public List<SearchResult> search(UUID acctId, SearchRequest request) {
        SearchResponse search = embeddingService.search(acctId, request);
        if (search.isSuccess()) {
            return search.getSearchResponses();
        }
        return List.of();
    }
}