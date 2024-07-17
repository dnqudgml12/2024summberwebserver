package com.practice.hello.freshman.dto;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;

import java.util.ArrayList;
import java.util.List;


public record FreshmanBoardCreateDTO(String title, String author, String content, int likes, List<FreshmanComment> freshmanComments){

    public Freshman toEntity() {
        return Freshman.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .freshmanComment(new ArrayList<>()) // Initialize with an empty list of comments
                .build();
    }


}
