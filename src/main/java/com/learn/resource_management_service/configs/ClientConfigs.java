package com.learn.resource_management_service.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ClientConfigs {

    @Bean(name = "media-details-extractor-rest-template")
    public RestTemplate mediaExtractorRestTemplate(@Value("${media.details.extractor.url}") String url) {
        return getRestTemplate(url);
    }

    @Bean(name = "global-search-engine-rest-template")
    public RestTemplate globalSearchEngineRestTemplate(@Value("${global.search.engine.url}") String url) {
        return getRestTemplate(url);
    }

    private RestTemplate getRestTemplate(String baseUrl) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
        return restTemplate;
    }
}
