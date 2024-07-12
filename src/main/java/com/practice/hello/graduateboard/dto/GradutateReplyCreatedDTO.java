package com.practice.hello.graduateboard.dto;

import com.practice.hello.graduateboard.entity.GraduateComment;
import com.practice.hello.graduateboard.entity.GraduateReply;
import com.practice.hello.secretboard.entity.SecretComment;
import com.practice.hello.secretboard.entity.SecretReply;

public record GradutateReplyCreatedDTO(String content, String author) {
    public GraduateReply toEntity(GraduateComment graduateComment, long sequenceNumber) {
        return GraduateReply.builder()
                .content(content)
                .author(author)
                .graduateComment(graduateComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
