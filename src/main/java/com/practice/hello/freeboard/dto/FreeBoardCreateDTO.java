package com.practice.hello.freeboard.dto;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.image.entity.Image;
import com.practice.hello.member.entity.Member;

import java.util.ArrayList;
import java.util.List;


public record FreeBoardCreateDTO(String title, String content, List<FreeComment> freeComments) {

    public FreeBoard toEntity(Member member, Image image) {
        return FreeBoard.builder()
                .title(title)
                .author(member.getNickname())
                .content(content)
                .likes(0)
                .freeComment(new ArrayList<>())
                .member(member)
                .image(image)
                .build();
    }
}
