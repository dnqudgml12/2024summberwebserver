package com.practice.hello.freshman.dto;

import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.freshman.entity.FreshmanComment;
import com.practice.hello.freshman.entity.FreshmanReply;

public record FreshmanReplyCreatedDTO(String content, String author) {
    public FreshmanReply toEntity(FreshmanComment freshmanComment, long sequenceNumber) {
        return FreshmanReply.builder()
                .content(content)
                .author(author)
                .freshmanComment(freshmanComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
