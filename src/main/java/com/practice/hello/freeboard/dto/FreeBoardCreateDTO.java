package com.practice.hello.freeboard.dto;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.member.entity.Member;

import java.util.ArrayList;
import java.util.List;


public record FreeBoardCreateDTO(String title,String content, List<FreeComment> freeComments){

    public FreeBoard toEntity(Member member) {
        return FreeBoard.builder()
                .title(title)
                .author(member.getNickname())
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .freeComment(new ArrayList<>()) // Initialize with an empty list of comments
                .member(member)
                .build();
    }


}
