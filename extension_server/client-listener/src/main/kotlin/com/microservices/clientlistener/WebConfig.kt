package com.microservices.clientlistener

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: org.springframework.web.servlet.config.annotation.CorsRegistry) {
        registry.addMapping("/**") // Apply to all endpoints
            .allowedOrigins("*") // Allow all origins
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specified methods
            .allowedHeaders("*") // Allow all headers
            .exposedHeaders("*") // Expose headers to the client
            .allowCredentials(false)
            .maxAge(3600) // Cache preflight response for 1 hour
    }
}