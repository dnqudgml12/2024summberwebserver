package com.practice.hello.information.dto;

import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.information.entity.InformationBoard;
import com.practice.hello.information.entity.InformationComment;

import java.util.ArrayList;

public record InformationCommentCreateDTO(String content, String author) {
    public InformationComment toEntity(InformationBoard informationBoard) {
        return InformationComment.builder()
                .content(content)
                .author(author)
                .informationBoard(informationBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
