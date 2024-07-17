package com.practice.hello.freshman.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practice.hello.freeboard.entity.FreeComment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreshmanReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String author;

    @ManyToOne
    @JoinColumn(name = "freshmancomment_id") // 하나의 comment에 여러개의 reply이니까 종속 관계에서 reply가 comment에 대해 외래키를 가지니까, 그 외래키 이름을 comment_id로 설정
    @JsonBackReference
    private FreshmanComment freshmanComment;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
    private long sequenceNumber;
    @Builder
    public FreshmanReply(String content, String author, FreshmanComment freshmanComment, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.freshmanComment = freshmanComment;
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFreeComment(FreshmanComment freshmanComment) {
        this.freshmanComment = freshmanComment;
    }


}
