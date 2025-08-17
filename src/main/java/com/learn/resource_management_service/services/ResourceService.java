package com.learn.resource_management_service.services;

import com.learn.resource_management_service.constants.ResourceTypes;
import com.learn.resource_management_service.entities.ResourceEntity;
import com.learn.resource_management_service.models.Resource;
import com.learn.resource_management_service.models.SearchResult;
import com.learn.resource_management_service.repositories.ResourceRepository;
import com.learn.resource_management_service.requests.SaveResourceRequest;
import com.learn.resource_management_service.requests.SearchRequest;
import com.learn.resource_management_service.responses.ResourceResponse;
import com.learn.resource_management_service.responses.Response;
import com.learn.resource_management_service.responses.SearchResourceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.learn.resource_management_service.utils.FileUtils.uploadFile;

@Service
public class ResourceService {

    private final AiService aiService;
    private final String uploadDir;
    private final ResourceRepository repo;
    private final String host;

    public ResourceService(AiService aiService, ResourceRepository repo,
            @Value("${file.upload-dir}") String uploadDir, @Value("${server.port}") String port) {
        this.repo = repo;
        this.uploadDir = uploadDir;
        this.host = "http://localhost:" + port;
        this.aiService = aiService;
    }

    public Response save(UUID accountMappingId, SaveResourceRequest request, MultipartFile file) throws Exception {

        ResourceEntity resourceEntities = getResourceEntities(accountMappingId, request, file);
        ResourceEntity savedResource = repo.save(resourceEntities);

        new Thread(() -> aiService.updateMediaDetailsForSimilaritySearch(file, savedResource)).start();

        return ResourceResponse.builder()
                .success(true)
                .responseMsg("Resource created successfully")
                .responseCode(201)
                .id(savedResource.getId())
                .title(request.getTitle())
                .imgUrl(this.host + savedResource.getImgUrl())
                .resourceType(request.getResourceType())
                .build();
    }

    public Response search(UUID acctId, SearchRequest request) {
        List<SearchResult> searchResults = aiService.search(acctId, request);

        if (CollectionUtils.isEmpty(searchResults)) {
            return Response.builder().responseCode(404)
                    .responseMsg("No results found for the query: " + request.getQuery())
                    .success(true)
                    .build();
        }

        List<UUID> resourceIds = searchResults.stream().map(SearchResult::getResourceId).toList();
        List<ResourceEntity> allResources = repo.findAllByIdIn(resourceIds);

        return SearchResourceResponse.builder()
                .success(true)
                .responseCode(200)
                .responseMsg("Search results fetched successfully")
                .resources(allResources.stream()
                        .map(result -> Resource.builder()
                                .id(result.getId())
                                .title(result.getTitle())
                                .imgUrl(this.host + result.getImgUrl())
                                .resourceType(ResourceTypes.fromValue(result.getResourceType()))
                                .build()
                        ).toList())
                .build();
    }

    private ResourceEntity getResourceEntities(UUID accountMappingId, SaveResourceRequest request, MultipartFile file) throws Exception {
        String url = uploadFile(file, uploadDir);
        return ResourceEntity.builder()
                .resourceType(request.getResourceType().getValue())
                .title(request.getTitle())
                .accountMappingId(accountMappingId)
                .imgUrl(url)
                .build();
    }
}
