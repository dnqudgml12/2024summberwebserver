package com.practice.hello.social.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)


public class SocialComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;

    private String content;
    private String author;

    @ManyToOne //하나의 socialboard_id")
    @JsonBackReference
    private SocialBoard socialBoard;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    // 하나의 comment에 여러개의 reply가 들어 간다
    @OneToMany(mappedBy = "socialComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SocialReply> replies;

    private Long sequenceNumber;

    @Builder
    public SocialComment(String content, String author, SocialBoard socialBoard, List<SocialReply> replies, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.socialBoard = socialBoard;
        this.replies = replies != null ? replies : new ArrayList<>();
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSocialBoard(SocialBoard socialBoard) {
        this.socialBoard = socialBoard;
    }




}
