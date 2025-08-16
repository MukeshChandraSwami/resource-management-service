package com.learn.resource_management_service.services;

import com.learn.resource_management_service.entities.ResourceEntity;
import com.learn.resource_management_service.models.ImageAnalysis;
import com.learn.resource_management_service.repositories.ResourceRepository;
import com.learn.resource_management_service.requests.SaveResourceRequest;
import com.learn.resource_management_service.responses.ResourceResponse;
import com.learn.resource_management_service.responses.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.learn.resource_management_service.utils.EmbeddingUtils.toEmbeddingInput;

@Service
public class ResourceService {

    private final MediaDetailsExtractorService mediaDetailsExtractorService;
    private final String uploadDir;
    private final ResourceRepository repo;
    private final String host;

    public ResourceService(
            MediaDetailsExtractorService mediaDetailsExtractorService, ResourceRepository repo,
            @Value("${file.upload-dir}") String uploadDir, @Value("${server.port}") String port) {
        this.repo = repo;
        this.uploadDir = uploadDir;
        this.host = "http://localhost:" + port;
        this.mediaDetailsExtractorService = mediaDetailsExtractorService;
    }

    public Response save(UUID accountMappingId, SaveResourceRequest request, MultipartFile file) throws Exception {

        ResourceEntity resourceEntities = getResourceEntities(accountMappingId, request, file);
        ResourceEntity savedResource = repo.save(resourceEntities);

        new Thread(() -> {
            try {
                updateMediaDetailsForSimilaritySearch(file, savedResource);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();


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

    private ResourceEntity getResourceEntities(UUID accountMappingId, SaveResourceRequest request, MultipartFile file) throws Exception {
        String url = uploadFile(file);
        return ResourceEntity.builder()
                .resourceType(request.getResourceType().getValue())
                .title(request.getTitle())
                .accountMappingId(accountMappingId)
                .imgUrl(url)
                .build();
    }

    private String uploadFile(MultipartFile file) throws IOException {
        // Ensure upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save file locally
        Files.write(filePath, file.getBytes());

        // Build file URL (served later by ResourceHandler)
        return  "/files/" + fileName;
    }

    private void updateMediaDetailsForSimilaritySearch(MultipartFile file, ResourceEntity resourceEntity) throws Exception {
        ImageAnalysis imageAnalysis = mediaDetailsExtractorService.extractMediaDetails(file);
        imageAnalysis.setMetadata(ImageAnalysis.Metadata.builder()
                        .resourceId(resourceEntity.getId().toString())
                        .resourceType(resourceEntity.getResourceType())
                        .accountMappingId(resourceEntity.getAccountMappingId().toString())
                .build());
        String s = toEmbeddingInput(imageAnalysis);
        System.out.println(s);
    }
}
