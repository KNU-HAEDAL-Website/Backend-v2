package com.haedal.haedalweb.domain.post.repository;

import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    @Query("SELECT pi FROM PostImage pi " +
            "WHERE pi.post IS NULL " +
            "AND pi.regDate < :threshold " +
            "ORDER BY pi.regDate ASC")
    List<PostImage> findOldImages(LocalDateTime threshold);

    List<PostImage> findByPost(Post post);

    List<PostImage> findAllBySaveFileIn(List<String> postImageNames);

    List<PostImage> findAllBySaveFileIn(Set<String> postImageNames);
}
