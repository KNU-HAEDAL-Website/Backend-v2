package com.haedal.haedalweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;

@Configuration
public class WebMmvConfig implements WebMvcConfigurer {
    private final String boardUploadPath;
    private final String boardUploadUrl;


    public WebMmvConfig(@Value("${file.path.upload-board-images}")String boardUploadPath,  @Value("${file.url.upload-board-images}") String boardUploadUrl) {
        this.boardUploadPath = boardUploadPath;
        this.boardUploadUrl = boardUploadUrl;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( boardUploadUrl + "/**")
                .addResourceLocations("file:///" + boardUploadPath + "/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
