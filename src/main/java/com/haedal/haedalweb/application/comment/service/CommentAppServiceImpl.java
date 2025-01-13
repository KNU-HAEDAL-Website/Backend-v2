package com.haedal.haedalweb.application.comment.service;

import com.haedal.haedalweb.application.comment.dto.CommentRequestDto;
import com.haedal.haedalweb.application.comment.dto.CommentResponseDto;
import com.haedal.haedalweb.application.comment.mapper.CommentMapper;
import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.comment.service.CommentService;
import com.haedal.haedalweb.domain.post.model.Post;
import com.haedal.haedalweb.domain.post.service.PostService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentPage(Long postId, Pageable pageable) {
        Page<Comment> commentPage = commentService.getCommentPage(postId, pageable);

        return commentPage.map(CommentMapper::toDto);
    }

    @Override
    @Transactional
    public void registerReply(Long commentId, CommentRequestDto replyRequestDto) {
        User user = securityService.getLoggedInUser();
        Comment comment = commentService.getComment(commentId);

        if (comment.getParent() != null) { // 답글의 깊이는 1까지만 가능
            throw new BusinessException(ErrorCode.BAD_REQUEST_REPLY);
        }

        Comment reply = Comment.builder()
                .content(replyRequestDto.getCommentContent())
                .user(user)
                .post(comment.getPost())
                .parent(comment)
                .build();

        commentService.registerComment(reply);
    }

    @Override
    @Transactional
    public void removeComment(Long postId, Long commentId) {
        User user = securityService.getLoggedInUser();
        Comment comment = commentService.getCommentWithUserAndPost(postId, commentId);

        commentService.validateRemovePermission(user, comment.getPost().getUser(), comment.getUser());
        comment.setDeleted(true); // soft delete
    }
}
