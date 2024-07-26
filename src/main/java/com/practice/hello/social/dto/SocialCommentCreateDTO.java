package com.practice.hello.social.dto;

import com.practice.hello.social.entity.SocialBoard;
import com.practice.hello.social.entity.SocialComment;

import java.util.ArrayList;

public record SocialCommentCreateDTO(String content, String author) {
    public SocialComment toEntity(SocialBoard socialBoard) {
        return SocialComment.builder()
                .content(content)
                .author(author)
                .socialBoard(socialBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
