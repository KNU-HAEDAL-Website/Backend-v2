package com.haedal.haedalweb.infrastructure.image;

import lombok.Getter;

@Getter
public class ImageRemoveEvent {
    private final String uploadPath;
    private final String removeFile;

    public ImageRemoveEvent(String uploadPath, String removeFile) {
        this.uploadPath = uploadPath;
        this.removeFile = removeFile;
    }
}
