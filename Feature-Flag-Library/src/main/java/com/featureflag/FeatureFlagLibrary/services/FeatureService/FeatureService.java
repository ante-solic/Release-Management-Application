package com.featureflag.FeatureFlagLibrary.services.FeatureService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class FeatureService {
    private final String featureApiUrl;
    private final String featurePathSegment;
    private final RestTemplate restTemplate;

    public FeatureService(RestTemplate restTemplate,
                          @Value("${feature.api.url:http://localhost:8080}") String featureApiUrl,
                          @Value("/feature") String featurePathSegment) {
        this.restTemplate = restTemplate;
        this.featureApiUrl = featureApiUrl;
        this.featurePathSegment = featurePathSegment;
    }

    public Optional<Boolean> isFeatureEnabled(String featureName, String accountId) {
        String url = featureApiUrl + featurePathSegment + "/" + featureName + "/" + accountId;
        return Optional.ofNullable(restTemplate.getForObject(url, Boolean.class));
    }
}
