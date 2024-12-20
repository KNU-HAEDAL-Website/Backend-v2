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


    public WebMvcConfig(@Value("${file.path.upload-board-images}")String boardUploadPath, @Value("${file.url.upload-board-images}") String boardUploadUrl) {
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 특정 패턴에 대해 인터셉터를 적용
        // "/activities/*/boards/*/image" 패턴에 해당하는 모든 요청에 대해 인터셉터 수행
        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/activities/*/boards/*/image");

        registry.addInterceptor(new ImageUploadInterceptor())
                .addPathPatterns("/activities/*/boards");
    }
}
