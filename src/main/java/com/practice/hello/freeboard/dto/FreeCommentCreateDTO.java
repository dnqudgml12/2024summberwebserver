package com.practice.hello.freeboard.dto;

import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;

import java.util.ArrayList;

public record FreeCommentCreateDTO(String content, String author) {
    public FreeComment toEntity(FreeBoard freeBoard) {
        return FreeComment.builder()
                .content(content)
                .author(author)
                .freeBoard(freeBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
