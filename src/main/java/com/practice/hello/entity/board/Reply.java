package com.practice.hello.entity.board;
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
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String author;

    @ManyToOne
    @JoinColumn(name = "comment") // 하나의 comment에 여러개의 reply이니까 종속 관계에서 reply가 comment에 대해 외래키를 가지니까, 그 외래키 이름을 comment_id로 설정
    @JsonBackReference
    private Comment comment;

    private long sequenceNumber;
    @Builder
    public Reply(String content, String author, Comment comment, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.comment = comment;
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }


}
