package com.practice.hello.graduateboard.dto;

import com.practice.hello.graduateboard.entity.GraduateBoard;
import com.practice.hello.graduateboard.entity.GraduateComment;

import java.util.ArrayList;

public record GradutateCommentCreateDTO(String content, String author) {
    public GraduateComment toEntity(GraduateBoard graduateBoard) {
        return GraduateComment.builder()
                .content(content)
                .author(author)
                .graduateBoard(graduateBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
