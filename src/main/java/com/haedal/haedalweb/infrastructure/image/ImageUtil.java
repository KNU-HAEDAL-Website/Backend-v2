package com.haedal.haedalweb.infrastructure.image;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ImageUtil {
    private ImageUtil() {
    }

    public static void uploadImage(MultipartFile multipartFile, String uploadPath, String saveFile) {
        try {
            File folder = new File(uploadPath);
            if (!folder.exists()) folder.mkdirs();

            Path path = Paths.get(uploadPath + File.separator + saveFile);
            log.warn(path.toString() + " path");
            multipartFile.transferTo(path);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.NOT_SAVE_FILE);
        }
    }

    public static void removeImage(String uploadPath, String removeFile) {
        String path = uploadPath + File.separator + removeFile;
        File file = new File(path);

        if (file.exists() && file.isFile()) {
            if (!file.delete()) {
                log.warn("파일 삭제 실패: {}", path);
            }
        }
    }

    public static String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new BusinessException(ErrorCode.BAD_REQUEST_FILE);
        }

        int lastDotIndex = fileName.lastIndexOf('.');

        // 확장자가 없다면 예외 발생
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            throw new BusinessException(ErrorCode.BAD_REQUEST_FILE);
        }

        return fileName.substring(lastDotIndex + 1);
    }

    public static String generateImageUrl(String uploadUrl, String saveFile) {
        return uploadUrl + "/" + saveFile;
    }
}
