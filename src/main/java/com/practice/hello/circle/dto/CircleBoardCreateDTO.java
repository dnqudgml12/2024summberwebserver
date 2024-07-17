package com.practice.hello.circle.dto;


import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;

import java.util.ArrayList;
import java.util.List;


public record CircleBoardCreateDTO(String title, String author, String content, int likes, List<CircleComment> circleComments){

    public CircleBoard toEntity() {
        return CircleBoard.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .circleComment(new ArrayList<>()) // Initialize with an empty list of comments
                .build();
    }


}
