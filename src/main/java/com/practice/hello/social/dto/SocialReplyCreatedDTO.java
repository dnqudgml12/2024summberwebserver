package com.practice.hello.social.dto;

import com.practice.hello.social.entity.SocialComment;
import com.practice.hello.social.entity.SocialReply;

public record SocialReplyCreatedDTO(String content, String author) {
    public SocialReply toEntity(SocialComment socialComment, long sequenceNumber) {
        return SocialReply.builder()
                .content(content)
                .author(author)
                .socialComment(socialComment)
                .sequenceNumber(sequenceNumber)
                .build();
    }
}
