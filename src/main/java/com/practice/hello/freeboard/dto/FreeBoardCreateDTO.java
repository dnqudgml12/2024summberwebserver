package com.practice.hello.freeboard.dto;


import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;

import java.util.ArrayList;
import java.util.List;


public record FreeBoardCreateDTO(String title, String author, String content, int likes, List<FreeComment> freeComments){

    public FreeBoard toEntity() {
        return FreeBoard.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .freeComment(new ArrayList<>()) // Initialize with an empty list of comments
                .build();
    }


}
