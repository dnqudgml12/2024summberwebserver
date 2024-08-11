package com.practice.hello.image.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.social.entity.SocialBoard;
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

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private FreeBoard freeBoard;

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private AdvertiseBoard advertiseBoard;


    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private Freshman freshmanBoard;

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private GraduateBoard graduateBoard;

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private SecretBoard secretBoard;

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private SocialBoard socialBoard;

    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private InformationBoard informationBoard;

/*
    @JsonBackReference
    @OneToOne(mappedBy = "image")
    private CircleBoard circleBoard;
*/

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
