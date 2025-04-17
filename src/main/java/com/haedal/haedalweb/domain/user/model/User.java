package com.haedal.haedalweb.domain.user.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class User {

	@Id
	@Column(name = "user_id", length = 31)
	private String id;

	@Column(name = "password")
	@NonNull
	private String password;

	@Column(name = "student_number", unique = true)
	private Integer studentNumber;

	@Column(name = "email", unique = true, length = 63)
	private String email;

	@Column(name = "user_name", length = 15)
	@NonNull
	private String name;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	@NonNull
	private Role role;

	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	@NonNull
	private UserStatus userStatus;

	@Column(name = "join_semester")
	@Enumerated(EnumType.STRING)
	private JoinSemester joinSemester;

	@CreatedDate
	@Column(name = "reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}
