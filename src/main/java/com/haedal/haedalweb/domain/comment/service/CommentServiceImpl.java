package com.haedal.haedalweb.domain.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.comment.model.Comment;
import com.haedal.haedalweb.domain.comment.repository.CommentRepository;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;

	@Override
	public void registerComment(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public void removeComments(Long postId) {
		commentRepository.deleteCommentsByPostId(postId);
	}

	@Override
	public Page<Comment> getCommentPage(Long postId, Pageable pageable) {
		return commentRepository.findCommentPage(postId, pageable);
	}

	@Override
	public Comment getCommentWithUser(Long commentId) {
		return commentRepository.findCommentWithUser(commentId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT_ID));
	}

	@Override
	public Comment getCommentWithUserAndPost(Long postId, Long commentId) {
		return commentRepository.findCommentWithUserAndPost(postId, commentId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT_ID));
	}

	@Override
	public void validateRemovePermission(User loggedInUser, User postCreator, User commentCreator) {
		String loggedInUserId = loggedInUser.getId();
		String postCreatorId = postCreator.getId();
		String commentCreatorId = commentCreator.getId();

		if (loggedInUser.getRole() == Role.ROLE_WEB_MASTER || loggedInUser.getRole() == Role.ROLE_ADMIN) {
			return; // 관리자면 삭제 가능
		}
		if (loggedInUserId.equals(postCreatorId)) {
			return; // 자신이 작성한 글의 댓글 삭제 가능
		}
		if (loggedInUserId.equals(commentCreatorId)) {
			return; // 자신이 작성한 댓글 삭제 가능
		}

		throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE); // 위의 경우 제외 예외 발생
	}

	@Override
	public void validateUpdatePermission(User loggedInUser, User commentCreator) {
		String loggedInUserId = loggedInUser.getId();
		String commentCreatorId = commentCreator.getId();

		if (loggedInUserId.equals(commentCreatorId)) {
			return; // 자신이 작성한 댓글 수정 가능
		}

		throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE); // 위의 경우 제외 예외 발생
	}
}
