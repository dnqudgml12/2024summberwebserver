package com.practice.hello.entity.board;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)


public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;

    private String content;
    private String author;

    @ManyToOne //하나의 board에 여러개의 comment가 들어 간다
    @JoinColumn(name="board_id")
    @JsonBackReference
    private Board board;


    // 하나의 comment에 여러개의 reply가 들어 간다
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reply> replies;

    private Long sequenceNumber;

    @Builder
    public Comment(String content, String author, Board board, List<Reply> replies, long sequenceNumber) {
        this.content = content;
        this.author = author;
        this.board = board;
        this.replies = replies != null ? replies : new ArrayList<>();
        this.sequenceNumber = sequenceNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBoard(Board board) {
        this.board = board;
    }




}
