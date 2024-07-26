package com.practice.hello.circle.dto;

import com.practice.hello.circle.entity.CircleBoard;
import com.practice.hello.circle.entity.CircleComment;

import java.util.ArrayList;

public record CircleCommentCreateDTO(String content, String author) {
    public CircleComment toEntity(CircleBoard circleBoard) {
        return CircleComment.builder()
                .content(content)
                .author(author)
                .circleBoard(circleBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
