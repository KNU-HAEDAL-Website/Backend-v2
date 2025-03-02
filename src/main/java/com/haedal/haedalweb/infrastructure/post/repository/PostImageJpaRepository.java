package com.haedal.haedalweb.infrastructure.post.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;

public interface PostImageJpaRepository extends JpaRepository<PostImage, Long> {
	@Query(
		"SELECT pi FROM PostImage pi "
			+ "WHERE pi.post IS NULL "
			+ "AND pi.regDate < :threshold "
			+ "ORDER BY pi.regDate ASC"
	)
	List<PostImage> findOldImages(@Param("threshold") LocalDateTime threshold);

	List<PostImage> findByPost(Post post);

	List<PostImage> findAllBySaveFileIn(List<String> postImageNames);

	List<PostImage> findAllBySaveFileIn(Set<String> postImageNames);
}
