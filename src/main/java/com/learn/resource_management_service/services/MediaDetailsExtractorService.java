package com.learn.resource_management_service.services;

import com.learn.resource_management_service.models.ImageAnalysis;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaDetailsExtractorService {

    public static final String MEDIA = "/media";

    private final RestTemplate restTemplate;

    public MediaDetailsExtractorService(@Qualifier("media-details-extractor-rest-template") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ImageAnalysis extractMediaDetails(MultipartFile file) {

        try {
            ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileAsResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            return restTemplate.postForObject(MEDIA, requestEntity, ImageAnalysis.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while media details extraction.", e);
        }
    }
}
