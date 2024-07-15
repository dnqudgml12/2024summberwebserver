package com.practice.hello.freeboard.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class FreeReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String author;

    @ManyToOne
    @JoinColumn(name = "freecomment_id") // 하나의 comment에 여러개의 reply이니까 종속 관계에서 reply가 comment에 대해 외래키를 가지니까, 그 외래키 이름을 comment_id로 설정
    @JsonBackReference
    private FreeComment freeComment;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
    private long sequenceNumber;
    @Builder
    public FreeReply(String content, String author, FreeComment freeComment, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.freeComment = freeComment;
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFreeComment(FreeComment freeComment) {
        this.freeComment = freeComment;
    }


}
