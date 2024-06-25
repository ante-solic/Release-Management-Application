package com.featureflag.FeatureFlagLibrary;

import com.featureflag.FeatureFlagLibrary.services.FeatureService.FeatureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FeatureFlagConfig {
    @Value("${feature.api.url:http://localhost:8080}")
    private String featureApiUrl;

    @Value("/feature/enabled/")
    private String featurePathSegment;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FeatureService featureService(RestTemplate restTemplate) {
        return new FeatureService(restTemplate, featureApiUrl, featurePathSegment);
    }
}
