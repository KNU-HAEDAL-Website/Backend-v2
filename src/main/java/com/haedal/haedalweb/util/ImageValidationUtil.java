package com.haedal.haedalweb.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;

public class ImageValidationUtil {
	private static final List<String> ALLOWED_EXTENSIONS = List.of("jpeg", "jpg", "png");
	private static final List<String> ALLOWED_MIME_TYPES = List.of("image/jpeg", "image/png");

	public static void validateImageExtension(MultipartFile imageFile) {
		String ImageFileName = imageFile.getOriginalFilename();
		String extension = ImageUtil.getExtension(ImageFileName);

		if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
			throw new BusinessException(ErrorCode.BAD_REQUEST_FILE);
		}

		String contentType = imageFile.getContentType();
		if (!ALLOWED_MIME_TYPES.contains(contentType)) {
			throw new BusinessException(ErrorCode.BAD_REQUEST_FILE);
		}

		validateRealImage(imageFile);
	}

	private static void validateRealImage(MultipartFile file) {
		try {
			BufferedImage image = ImageIO.read(file.getInputStream());
			if (image == null) {
				throw new IllegalArgumentException("Invalid image content.");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Error reading the file.", e);
		}
	}
}
