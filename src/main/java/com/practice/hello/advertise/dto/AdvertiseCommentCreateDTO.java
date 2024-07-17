package com.practice.hello.advertise.dto;

import com.practice.hello.advertise.entity.AdvertiseBoard;
import com.practice.hello.advertise.entity.AdvertiseComment;
import com.practice.hello.freeboard.entity.FreeBoard;
import com.practice.hello.freeboard.entity.FreeComment;

import java.util.ArrayList;

public record AdvertiseCommentCreateDTO(String content, String author) {
    public AdvertiseComment toEntity(AdvertiseBoard advertiseBoard) {
        return AdvertiseComment.builder()
                .content(content)
                .author(author)
                .advertiseBoard(advertiseBoard)
                .replies(new ArrayList<>())  // Initialize an empty list for replies
                .build();
    }
}
