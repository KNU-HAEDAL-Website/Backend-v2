package com.haedal.haedalweb.util;

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

    public static String generateImageUrl(String uploadUrl, String saveFile) {
        return uploadUrl + "/" + saveFile;
    }
}
