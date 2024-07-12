package com.practice.hello.dto.boardreq;


import com.practice.hello.entity.board.Board;
import com.practice.hello.entity.board.Comment;

import java.util.ArrayList;
import java.util.List;


public record BoardCreateDTO(String title, String author, String content, int likes, List<Comment> comments){

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .author(author)
                .content(content)
                .likes(0) // Default to 0 likes when creating a new board
                .comments(new ArrayList<>()) // Initialize with an empty list of comments
                .build();
    }


}
