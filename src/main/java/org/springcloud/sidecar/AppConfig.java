package org.springcloud.sidecar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SidecarHealthIndicator elasticSearchHealthCheck() {
        return new ElasticSearchHealthCheck();
    }
}