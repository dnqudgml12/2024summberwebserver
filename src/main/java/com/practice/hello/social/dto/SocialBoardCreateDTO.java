package com.practice.hello.social.dto;


import com.practice.hello.member.entity.Member;
import com.practice.hello.social.entity.SocialBoard;
import com.practice.hello.social.entity.SocialComment;

import java.util.ArrayList;
import java.util.List;


public record SocialBoardCreateDTO(String title, String author, String content, int likes, List<SocialComment> socialComments){

    public SocialBoard toEntity(Member member) {
        return SocialBoard.builder()
                .title(title)
                .author(member.getNickname())
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .socialComment(new ArrayList<>()) // Initialize with an empty list of comments
                .member(member)
                .build();
    }


}
