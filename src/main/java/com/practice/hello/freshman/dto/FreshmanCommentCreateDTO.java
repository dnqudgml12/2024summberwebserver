package com.practice.hello.freshman.dto;

import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freshman.entity.Freshman;
import com.practice.hello.freshman.entity.FreshmanComment;

import java.util.ArrayList;

public record FreshmanCommentCreateDTO(String content, String author) {
    public FreshmanComment toEntity(Freshman freshman) {
        return FreshmanComment.builder()
                .content(content)
                .author(author)
                .freshman(freshman)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
