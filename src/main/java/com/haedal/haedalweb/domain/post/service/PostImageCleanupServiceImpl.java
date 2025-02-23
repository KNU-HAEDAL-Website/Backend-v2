package com.haedal.haedalweb.domain.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.domain.post.model.PostImage;
import com.haedal.haedalweb.domain.post.repository.PostImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostImageCleanupServiceImpl implements PostImageCleanupService {
	private final PostImageRepository postImageRepository;

	// 매일 자정에 실행
	@Scheduled(cron = "0 0 0 * * ?")
	public void cleanupOldNullPostImages() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime threshold = now.minusDays(3);

		log.info("Cleanup job started at {}, threshold: {}", now, threshold);

		List<PostImage> oldImages = postImageRepository.findOldImages(threshold);

		if (!oldImages.isEmpty()) {
			postImageRepository.deleteAll(oldImages);
			log.info("Deleted {} images (postId = null, regDate < 3일 전)", oldImages.size());
		} else {
			log.info("No images found to delete (postId = null, 3일 이상 지난 regDate)");
		}
	}
}
