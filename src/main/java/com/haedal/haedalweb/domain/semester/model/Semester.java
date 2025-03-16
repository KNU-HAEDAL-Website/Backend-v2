package com.haedal.haedalweb.domain.semester.model;

import com.haedal.haedalweb.domain.association.model.UserSemester;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
public class Semester {
	@Id
	@Column(name = "semester_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "semester_name", length = 7, unique = true)
	@NonNull
	private String name;

	@CreatedDate
	@Column(name = "reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Builder.Default
	@OneToMany(mappedBy = "semester", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserSemester> userSemesters = new ArrayList<>();
}
