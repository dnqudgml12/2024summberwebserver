package com.practice.hello.image.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.graduateboard.entity.GraduateBoard;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageUrl;

    @JsonBackReference(value = "freeBoard-image")
    @OneToOne(mappedBy = "image")
    private FreeBoard freeBoard;

    @JsonBackReference(value = "freshman-image")
    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL)
    private Freshman freshman;


    @JsonBackReference(value = "advertiseBoard-image")
    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL)
    private AdvertiseBoard advertiseBoard;

    @JsonBackReference(value = "graduateBoard-image")
    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL)
    private GraduateBoard graduateBoard;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
    @Builder
    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
