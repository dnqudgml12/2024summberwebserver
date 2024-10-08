package com.practice.hello.information.dto;


import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.member.entity.Member;

import java.util.ArrayList;
import java.util.List;


public record InformationBoardCreateDTO(String title, String author, String content, int likes, List<InformationComment> informationComments){

    public InformationBoard toEntity(Member member) {
        return InformationBoard.builder()
                .title(title)
                .author(member.getNickname())
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .informationComment(new ArrayList<>()) // Initialize with an empty list of comments
                .member(member)
                .build();
    }


}
