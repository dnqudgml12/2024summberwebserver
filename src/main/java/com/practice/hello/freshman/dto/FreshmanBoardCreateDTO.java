package com.practice.hello.freshman.dto;


import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.image.entity.Image;
import com.practice.hello.member.entity.Member;

import java.util.ArrayList;
import java.util.List;


public record FreshmanBoardCreateDTO(String title, String author, String content, int likes, List<FreshmanComment> freshmanComments){

    public Freshman toEntity(Member member, Image image) {
        return Freshman.builder()
                .title(title)
                .author(member.getNickname())
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .freshmanComment(new ArrayList<>()) // Initialize with an empty list of comments
                .member(member)
                .image(image)
                .build();
    }


}
