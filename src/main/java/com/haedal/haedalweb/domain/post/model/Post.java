package com.haedal.haedalweb.domain.post.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
@Entity
public class Post {
	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "post_title")
	@NonNull
	private String title;

	@Lob
	@Column(name = "post_content", columnDefinition = "mediumtext")
	@NonNull
	private String content;

	@Column(name = "post_type")
	@Enumerated(EnumType.STRING)
	@NonNull
	private PostType postType;

	@Column(name = "post_activity_start_date")
	private LocalDate activityStartDate;

	@Column(name = "post_activity_end_date")
	private LocalDate activityEndDate;

	@Column(name = "post_views")
	@Builder.Default
	private Long postViews = 0L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;

	@CreatedDate
	@Column(name = "reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}
