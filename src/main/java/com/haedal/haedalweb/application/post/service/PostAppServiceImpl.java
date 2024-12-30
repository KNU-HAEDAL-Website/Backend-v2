package com.haedal.haedalweb.application.post.service;

import com.haedal.haedalweb.application.post.dto.PostImageResponseDto;
import com.haedal.haedalweb.application.post.mapper.PostImageMapper;
import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.service.PostImageService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;
import com.haedal.haedalweb.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
public class PostAppServiceImpl implements PostAppService {
    private final PostImageService postImageService;
    private final SecurityService securityService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String uploadPath;
    private final String uploadUrl;

    public PostAppServiceImpl(PostImageService postImageService, SecurityService securityService, ApplicationEventPublisher applicationEventPublisher, @Value("${file.path.upload-post-images}") String uploadPath, @Value("${file.url.upload-post-images}") String uploadUrl) {
        this.postImageService = postImageService;
        this.securityService = securityService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.uploadPath = uploadPath;
        this.uploadUrl = uploadUrl;
    }

    @Override
    @Transactional
    public PostImageResponseDto registerPostImage(MultipartFile postImageFile) {
        User loggedInUser = securityService.getLoggedInUser();

        String originalFile = postImageFile.getOriginalFilename();
        String saveFile = UUID.randomUUID() + "." + ImageUtil.getExtension(originalFile);
        ImageUtil.uploadImage(postImageFile, uploadPath, saveFile);

        applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

        PostImage postImage = PostImage.builder()
                .originalFile(originalFile)
                .saveFile(saveFile)
                .user(loggedInUser)
                .build();

        PostImage savedPostImage = postImageService.registerPostImage(postImage);

        return PostImageMapper.toDto(ImageUtil.generateImageUrl(uploadUrl, saveFile), savedPostImage);
    }
}
