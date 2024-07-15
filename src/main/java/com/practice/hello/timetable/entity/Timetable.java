package com.practice.hello.timetable.entity;

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
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name="professor", nullable = false )
    private String professor;

    @Column(name="location", nullable = false )
    private String location;

    @Column(name="day", nullable = false )
    private String day;

    @Column(name="startTime", nullable = false )
    private String startTime;

    @Column(name="endTime", nullable = false )
    private String endTime;



    @Column(name="color", nullable = false )
    private String color;



    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //camelCase하면 db에서 언더 바로 읽는다(_);

    @LastModifiedDate // 수정시간
    @Column(name = "updated_at")
    private LocalDateTime updateAt;


    @Builder
    public Timetable(String subject, String professor, String day, String startTime, String endTime, String location, String color){
        this.subject=subject;
        this.professor=professor;

        this.day=day;
        this.startTime=startTime;
        this.endTime=endTime;
        this.location=location;
        this.color=color;
    }


    public void update(String subject, String professor, String day, String startTime, String endTime, String location) {
        this.subject=subject;
        this.professor=professor;

        this.day=day;
        this.startTime=startTime;
        this.endTime=endTime;
        this.location=location;

    }


}
