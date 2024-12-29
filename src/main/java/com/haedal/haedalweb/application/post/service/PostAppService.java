package com.haedal.haedalweb.application.post.service;

import org.springframework.web.multipart.MultipartFile;

public interface PostAppService {
    String registerPostImage(MultipartFile postImageFile);
}
