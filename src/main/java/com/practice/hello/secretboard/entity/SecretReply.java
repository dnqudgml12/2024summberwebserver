package com.practice.hello.secretboard.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecretReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String author;

    @ManyToOne
    @JoinColumn(name = "secretcomment_id") // 하나의 comment에 여러개의 reply이니까 종속 관계에서 reply가 comment에 대해 외래키를 가지니까, 그 외래키 이름을 comment_id로 설정
    @JsonBackReference
    private SecretComment secretComment;

    private long sequenceNumber;
    @Builder
    public SecretReply(String content, String author, SecretComment secretComment, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.secretComment = secretComment;
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSecretComment(SecretComment secretComment) {
        this.secretComment = secretComment;
    }


}
