package com.haedal.haedalweb.config;

import com.haedal.haedalweb.web.interceptor.ImageUploadInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final String boardUploadPath;
    private final String boardUploadUrl;
    private final String profileUploadPath;
    private final String profileUploadUrl;
    private final String postUploadPath;
    private final String postUploadUrl;
    private final String defaultUploadUrl;


    public WebMvcConfig(@Value("${file.path.upload-board-images}")String boardUploadPath, @Value("${file.url.upload-board-images}") String boardUploadUrl, @Value("${file.path.upload-profile-images}") String profileUploadPath, @Value("${file.url.upload-profile-images}") String profileUploadUrl, @Value("${file.path.upload-post-images}") String postUploadPath, @Value("${file.url.upload-post-images}") String postUploadUrl, @Value("${file.url.upload-default-images}") String defaultUploadUrl) {
        this.boardUploadPath = boardUploadPath;
        this.boardUploadUrl = boardUploadUrl;
        this.profileUploadPath = profileUploadPath;
        this.profileUploadUrl = profileUploadUrl;
        this.postUploadPath = postUploadPath;
        this.postUploadUrl = postUploadUrl;
        this.defaultUploadUrl = defaultUploadUrl;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( boardUploadUrl + "/**")
                .addResourceLocations("file:///" + boardUploadPath + "/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler( profileUploadUrl + "/**")
                .addResourceLocations("file:///" + profileUploadPath + "/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler( postUploadUrl + "/**")
                .addResourceLocations("file:///" + postUploadPath + "/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler(defaultUploadUrl+"/**")
                .addResourceLocations("classpath:/static/images/") // 기본 이미지 경로
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 특정 패턴에 대해 인터셉터를 적용
        // "/activities/*/boards/*/image" 패턴에 해당하는 모든 요청에 대해 인터셉터 수행
        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/activities/*/boards/*/image");

        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/activities/*/boards");

        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/users/*/profile/image");

        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/users/*/profile/image");

        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/post-images");
    }
}
