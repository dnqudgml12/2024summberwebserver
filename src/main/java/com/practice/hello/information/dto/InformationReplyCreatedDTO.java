package com.practice.hello.information.dto;

import com.practice.hello.information.entity.InformationComment;
import com.practice.hello.information.entity.InformationReply;

public record InformationReplyCreatedDTO(String content, String author) {
    public InformationReply toEntity(InformationComment informationComment, long sequenceNumber) {
        return InformationReply.builder()
                .content(content)
                .author(author)
                .informationComment(informationComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
