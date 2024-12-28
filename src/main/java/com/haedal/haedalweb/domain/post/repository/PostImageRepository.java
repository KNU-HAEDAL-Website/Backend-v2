package com.haedal.haedalweb.domain.post.repository;

import com.haedal.haedalweb.domain.post.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
