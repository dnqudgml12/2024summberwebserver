package com.practice.hello.graduateboard.entity;
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
public class GraduateReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String author;

    @ManyToOne
    @JoinColumn(name = "graduatecomment_id") // 하나의 comment에 여러개의 reply이니까 종속 관계에서 reply가 comment에 대해 외래키를 가지니까, 그 외래키 이름을 comment_id로 설정
    @JsonBackReference
    private GraduateComment graduateComment;

    private long sequenceNumber;
    @Builder
    public GraduateReply(String content, String author, GraduateComment graduateComment, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.graduateComment = graduateComment;
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGraduateComment(GraduateComment graduateComment) {
        this.graduateComment = graduateComment;
    }


}
