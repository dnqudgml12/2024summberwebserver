package com.practice.hello.freshman.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeReply;
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


public class FreshmanComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;

    private String content;
    private String author;

    @ManyToOne //하나의 board에 여러개의 comment가 들어 간다
    @JoinColumn(name="freshman_id")
    @JsonBackReference
    private Freshman freshman;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    // 하나의 comment에 여러개의 reply가 들어 간다
    @OneToMany(mappedBy = "freshmanComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<com.practice.hello.freshman.entity.FreshmanReply> replies;

    private Long sequenceNumber;

    @Builder
    public FreshmanComment(String content, String author, Freshman freshman, List<FreshmanReply> replies, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.freshman = freshman;
        this.replies = replies != null ? replies : new ArrayList<>();
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFreshmanBoard(Freshman freshman) {
        this.freshman = freshman;
    }




}
