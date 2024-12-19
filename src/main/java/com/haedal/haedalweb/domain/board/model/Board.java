package com.haedal.haedalweb.domain.board.model;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
@Entity
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    @NonNull
    private String name;

    @Column(name = "board_intro")
    private String intro;

    @OneToOne(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true) // FetchType.Lazy를 설정해도, 연관관계의 주인이 BoardImage 이므로 적용 x
    private BoardImage boardImage;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public void addParticipant(Participant participant) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        participants.add(participant);
    }
}
