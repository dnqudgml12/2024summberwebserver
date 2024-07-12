package com.practice.hello.secretboard.dto;

import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.secretboard.entity.SecretBoard;
import com.practice.hello.secretboard.entity.SecretComment;

import java.util.ArrayList;

public record SecretCommentCreateDTO(String content, String author) {
    public SecretComment toEntity(SecretBoard secretBoard) {
        return SecretComment.builder()
                .content(content)
                .author(author)
                .secretBoard(secretBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
