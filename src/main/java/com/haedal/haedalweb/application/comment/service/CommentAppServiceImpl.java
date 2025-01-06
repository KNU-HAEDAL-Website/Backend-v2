package com.haedal.haedalweb.application.comment.service;

import com.haedal.haedalweb.application.comment.dto.CommentRequestDto;
import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.comment.service.CommentService;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.service.PostService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentAppServiceImpl implements CommentAppService {
    public final CommentService commentService;
    public final PostService postService;
    public final SecurityService securityService;

    @Override
    @Transactional
    public void registerComment(Long postId, CommentRequestDto commentRequestDto) {
        User user = securityService.getLoggedInUser();
        Post post = postService.getPost(postId);

        Comment comment = Comment.builder()
                .content(commentRequestDto.getCommentContent())
                .user(user)
                .post(post)
                .build();

        commentService.registerComment(comment);
    }
}
