package com.practice.hello.entity.board;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;  //lombok -> annotation만드는거
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//Entity가 바뀔때 마다 감시해주겠다
// 수정된 내용만 보내주면 이 친구가 알아서 수정 날짜를 알아서 해주겠다.
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Board {



    @Id // primary key로 선언해주겠다. id로 해주었으므로 null이 안된다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //instance가 하나 생성될때 마다
    // 프론트에서 지정안해줘도 +1되서 값을 지정해주겠다.



    private Long id;

    // 명시 안해줘도 같은거를 찾는다
    //title이라는 컬럼을 찾아서 null이 아니도록 해준다.
    // db테이블이랑 mapping,
    // 이런 상황에서 title 안 보내면 db error
    @Column(name="title", nullable = false )
    private String title;

    @Column(name="content", nullable = false )
    private String content;



    private String author;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    private int likes;

    private boolean likeStatus; // Added boolean field for like status

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;




    @Builder //Setter역할 한다
    public Board(String title, String content, String author, int likes, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.comments = comments != null ? comments : new ArrayList<>();
        this.likeStatus = false; // Initialize likeStatus to false (not liked)
    }


    //수정 함수, DB안에 있는 객체를 수정 하니까
    // 객체 안에 수정은 이 안에서 이루어져야 한다.
    public void update(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;

    }


    public void setLikeStatus(boolean likeStatus) {
        this.likeStatus = likeStatus;
        if (likeStatus) {
            this.likes++;
        } else {
            this.likes--;
        }
    }


}
