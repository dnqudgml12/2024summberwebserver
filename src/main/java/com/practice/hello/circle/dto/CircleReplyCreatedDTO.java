package com.practice.hello.circle.dto;

import com.practice.hello.circle.entity.CircleComment;
import com.practice.hello.circle.entity.CircleReply;

public record CircleReplyCreatedDTO(String content, String author) {
    public CircleReply toEntity(CircleComment circleComment, long sequenceNumber) {
        return CircleReply.builder()
                .content(content)
                .author(author)
                .circleComment(circleComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
