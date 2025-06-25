package com.example.todo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Web MVC configuration for handling static resource requests.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.directory}")
    private String uploadDir;

    /**
     * Configures resource handlers for serving static files from the file system.
     * Maps /uploads/** URLs to the configured upload directory.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            String uploadAbsolutePath = uploadPath.toAbsolutePath().normalize().toString();

            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadAbsolutePath + "/");
        } catch (Exception e) {
            throw new RuntimeException("Could not configure resource handler for upload directory: " + uploadDir, e);
        }
    }
}