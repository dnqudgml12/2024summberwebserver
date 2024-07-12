package com.practice.hello.secretboard.dto;

import com.practice.hello.freeboard.entity.FreeComment;
import com.practice.hello.freeboard.entity.FreeReply;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.entity.SecretReply;

public record SecretReplyCreatedDTO(String content, String author) {
    public SecretReply toEntity(SecretComment secretComment, long sequenceNumber) {
        return SecretReply.builder()
                .content(content)
                .author(author)
                .secretComment(secretComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
