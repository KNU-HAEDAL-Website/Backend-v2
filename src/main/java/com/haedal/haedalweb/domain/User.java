package com.haedal.haedalweb.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {

    @Id
    @Column(name = "user_id", length = 31)
    private String id;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "student_number")
    @NonNull
    private Long studentNumber;

    @Column(name = "user_name", length = 15)
    @NonNull
    private String name;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    @NonNull
    private Profile profile;
}
