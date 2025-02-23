package com.haedal.haedalweb.infrastructure.image;

import lombok.Getter;

@Getter
public class ImageSaveRollbackEvent {
	private final String uploadPath;
	private final String saveFile;

	public ImageSaveRollbackEvent(String uploadPath, String saveFile) {
		this.uploadPath = uploadPath;
		this.saveFile = saveFile;
	}
}
