package com.haedal.haedalweb.domain.profile.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
@Entity
public class ProfileImage {
	@Id
	@Column(name = "profile_image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "profile_image_original_file")
	private String originalFile;

	@Column(name = "profile_image_save_file")
	private String saveFile;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false, unique = true)
	private Profile profile;

	@CreatedDate
	@Column(name = "reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}
