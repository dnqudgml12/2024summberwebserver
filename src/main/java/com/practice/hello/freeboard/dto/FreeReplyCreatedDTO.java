package com.practice.hello.freeboard.dto;

import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;

public record FreeReplyCreatedDTO(String content, String author) {
    public FreeReply toEntity(FreeComment freeComment, long sequenceNumber) {
        return FreeReply.builder()
                .content(content)
                .author(author)
                .freeComment(freeComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
